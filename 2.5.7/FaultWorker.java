import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;
import com.fourinone.Workman;

public class FaultWorker extends MigrantWorker
{
	public WareHouse doTask(WareHouse inhouse)
	{	
		System.out.println(inhouse.getString("word"));
		try{Thread.sleep(8000L);}catch(Exception ex){}
		for(int i=0;i<100000000;i++)System.out.println(i);
		String[] strs = null;
		System.out.println(strs.length);
		WareHouse wh = new WareHouse("word", "hello ");
		return wh;
	}
	
	public static void main(String[] args)
	{
		FaultWorker mw = new FaultWorker();
		mw.waitWorking("localhost",Integer.parseInt(args[0]),"faultworker");
	}
}