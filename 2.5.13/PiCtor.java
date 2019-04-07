import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;
import java.util.Date;

public class PiCtor extends Contractor
{
	public WareHouse giveTask(WareHouse inhouse)
	{
		WorkerLocal[] wks = getWaitingWorkers("PiWorker");
		System.out.println("wks.length:"+wks.length);
		
		WareHouse[] hmarr = doTaskBatch(wks, inhouse);
		
		double pi=0.0;
		for(WareHouse result:hmarr){
			pi = pi + (Double)result.getObj("pi");
		}
		
		System.out.println("pi:"+pi);
		return inhouse;
	}
	
	public static void main(String[] args)
	{
		PiCtor a = new PiCtor();
		long begin = (new Date()).getTime();
		a.giveTask(new WareHouse());
		long end = (new Date()).getTime();
		System.out.println("time:"+(end-begin)/1000+"s");
		a.exit();
	}
}