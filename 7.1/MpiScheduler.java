import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;
import com.fourinone.StartResult;
import com.fourinone.FileAdapter;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

public class MpiScheduler extends Contractor
{
	private static boolean runFlag = false;
	private static ParkLocal pl = BeanContext.getPark();//配置和队列管理
	
	public WareHouse giveTask(WareHouse inhouse)
	{
		//初始化mpi机器列表配置
		pl.create("mpi", 0+"", new Integer(50));
		pl.create("mpi", 1+"", new Integer(100));
		pl.create("mpi", 2+"", new Integer(50));
		
		ArrayList<StartResult<Integer>> rsarr = new ArrayList<StartResult<Integer>>();//调度结果数组
		while(true){
			//根据队列是否存在job和是否有足够机器判断调度
			for(int i=0;i<3;i++){
				ObjectBean job = receive(i+"");//不阻塞接收队列，获取到job对象
				if(job!=null){
					WareHouse jobobj = (WareHouse)job.toObject();
					int num = jobobj.getStringInt("computnum");//获取申请机器数
					Integer iob = (Integer)(pl.get("mpi",i+"").toObject());//获取配置列表里剩下的机器数量
					if(iob>=num){
						System.out.print(i+"type spent "+num+" computer to doTask");
						pl.delete(job.getDomain(), job.getNode());//删除队列
						pl.update("mpi", i+"", new Integer(iob-num));//更新机器列表
						System.out.println(", and remain "+(iob-num));
						//FileAdapter.createTempFile
						//调度job对象里的sh脚本
						StartResult<Integer> res = BeanContext.tryStart(new FileAdapter(jobobj.getString("shdir")),"sh", jobobj.getString("sh"), jobobj.getString("param"), jobobj.getString("computnum"), ">>log/"+i+".log", "2>>&1");
						res.setObj("job",jobobj);
						rsarr.add(res);//将调度结果添加到结果数组
					}//else System.out.println(i+"type remain "+iob+" is not enough for "+num);
				}
			}
		
			//检查结果数组是否完成调度
			ArrayList<StartResult<Integer>> rmvrs = new ArrayList<StartResult<Integer>>();//记录完成的结果
			for(StartResult<Integer> rs:rsarr){
				WareHouse jobwh = (WareHouse)rs.getObj("job");
				int timeout = jobwh.getStringInt("timeout");
				if(rs!=null&&rs.getStatus(StartResult.s(timeout))!=StartResult.NOTREADY){//检查结果是否完成或超时
					System.out.print("Result:"+rs.getResult());
					Integer iob = (Integer)(pl.get("mpi",jobwh.getString("mpiType")).toObject());
					Integer newiob = iob+jobwh.getStringInt("computnum");
					pl.update("mpi", jobwh.getString("mpiType"), newiob);
					System.out.println(", and remain "+newiob+" "+jobwh);
					rmvrs.add(rs);
				}
			}
			rsarr.removeAll(rmvrs);//完成从结果数组删除
		}
	}
	
	//发送到队列
	public static void send(String queue, Object obj){
		pl.create(queue, (Serializable)obj);
	}
	
	//从队列接收
	public static ObjectBean receive(String queue){
		ObjectBean ob=null;
		List<ObjectBean> oblist = pl.get(queue);
		if(oblist!=null)
			ob = oblist.get(0);
		return ob;
	}
	
	public static void main(String[] args)
	{
		WareHouse jobwh = new WareHouse();
		jobwh.setString("mpiType",args[0]);//mpi计算类型
		jobwh.setString("shdir",args[1]);//sh脚本运行目录
		jobwh.setString("sh",args[2]);//sh脚本名称
		jobwh.setString("param",args[3]);//sh脚本参数
		jobwh.setString("computnum",args[4]);//申请计算机数量
		jobwh.setString("timeout",args[5]);//超时时间，单位为秒
		send(args[0],jobwh);//提交到队列
		System.out.println("submit job to queue:"+jobwh);
		
		//判断锁是否作为调度服务，不作为调度服务提交job后直接退出
		ObjectBean oblock = pl.get("mpi","lock");
		if(oblock==null){
			pl.create("mpi", "lock", true, true);
			MpiScheduler a = new MpiScheduler();
			a.giveTask(null);
		}
	}
}