import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;
import java.util.Date;

public class CombCtor extends Contractor
{
	public WareHouse giveTask(WareHouse wh)
	{
		WorkerLocal[] wks = getWaitingWorkers("CombWorker");
		System.out.println("wks.length:"+wks.length+";"+wh);
		wh.setObj("wknum",wks.length);
		WareHouse[] hmarr = doTaskBatch(wks, wh);//批量执行任务,所有工人完成才返回
		int total=0;
		for(WareHouse hm:hmarr)
			total+=(Integer)hm.getObj("total");
		System.out.println("total:"+total);
		return wh;
	}
	
	public static void main(String[] args)
	{
		CombCtor a = new CombCtor();
		WareHouse wh = new WareHouse("comb", Integer.parseInt(args[0]));
		long begin = (new Date()).getTime();
		a.doProject(wh);
		long end = (new Date()).getTime();
		System.out.println("time:"+(end-begin)/1000+"s");
		a.exit();
	}
}