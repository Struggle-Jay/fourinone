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
	private static ParkLocal pl = BeanContext.getPark();//���úͶ��й���
	
	public WareHouse giveTask(WareHouse inhouse)
	{
		//��ʼ��mpi�����б�����
		pl.create("mpi", 0+"", new Integer(50));
		pl.create("mpi", 1+"", new Integer(100));
		pl.create("mpi", 2+"", new Integer(50));
		
		ArrayList<StartResult<Integer>> rsarr = new ArrayList<StartResult<Integer>>();//���Ƚ������
		while(true){
			//���ݶ����Ƿ����job���Ƿ����㹻�����жϵ���
			for(int i=0;i<3;i++){
				ObjectBean job = receive(i+"");//���������ն��У���ȡ��job����
				if(job!=null){
					WareHouse jobobj = (WareHouse)job.toObject();
					int num = jobobj.getStringInt("computnum");//��ȡ���������
					Integer iob = (Integer)(pl.get("mpi",i+"").toObject());//��ȡ�����б���ʣ�µĻ�������
					if(iob>=num){
						System.out.print(i+"type spent "+num+" computer to doTask");
						pl.delete(job.getDomain(), job.getNode());//ɾ������
						pl.update("mpi", i+"", new Integer(iob-num));//���»����б�
						System.out.println(", and remain "+(iob-num));
						//FileAdapter.createTempFile
						//����job�������sh�ű�
						StartResult<Integer> res = BeanContext.tryStart(new FileAdapter(jobobj.getString("shdir")),"sh", jobobj.getString("sh"), jobobj.getString("param"), jobobj.getString("computnum"), ">>log/"+i+".log", "2>>&1");
						res.setObj("job",jobobj);
						rsarr.add(res);//�����Ƚ����ӵ��������
					}//else System.out.println(i+"type remain "+iob+" is not enough for "+num);
				}
			}
		
			//����������Ƿ���ɵ���
			ArrayList<StartResult<Integer>> rmvrs = new ArrayList<StartResult<Integer>>();//��¼��ɵĽ��
			for(StartResult<Integer> rs:rsarr){
				WareHouse jobwh = (WareHouse)rs.getObj("job");
				int timeout = jobwh.getStringInt("timeout");
				if(rs!=null&&rs.getStatus(StartResult.s(timeout))!=StartResult.NOTREADY){//������Ƿ���ɻ�ʱ
					System.out.print("Result:"+rs.getResult());
					Integer iob = (Integer)(pl.get("mpi",jobwh.getString("mpiType")).toObject());
					Integer newiob = iob+jobwh.getStringInt("computnum");
					pl.update("mpi", jobwh.getString("mpiType"), newiob);
					System.out.println(", and remain "+newiob+" "+jobwh);
					rmvrs.add(rs);
				}
			}
			rsarr.removeAll(rmvrs);//��ɴӽ������ɾ��
		}
	}
	
	//���͵�����
	public static void send(String queue, Object obj){
		pl.create(queue, (Serializable)obj);
	}
	
	//�Ӷ��н���
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
		jobwh.setString("mpiType",args[0]);//mpi��������
		jobwh.setString("shdir",args[1]);//sh�ű�����Ŀ¼
		jobwh.setString("sh",args[2]);//sh�ű�����
		jobwh.setString("param",args[3]);//sh�ű�����
		jobwh.setString("computnum",args[4]);//������������
		jobwh.setString("timeout",args[5]);//��ʱʱ�䣬��λΪ��
		send(args[0],jobwh);//�ύ������
		System.out.println("submit job to queue:"+jobwh);
		
		//�ж����Ƿ���Ϊ���ȷ��񣬲���Ϊ���ȷ����ύjob��ֱ���˳�
		ObjectBean oblock = pl.get("mpi","lock");
		if(oblock==null){
			pl.create("mpi", "lock", true, true);
			MpiScheduler a = new MpiScheduler();
			a.giveTask(null);
		}
	}
}