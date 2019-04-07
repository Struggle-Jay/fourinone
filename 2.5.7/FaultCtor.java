import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;
import java.util.ArrayList;

public class FaultCtor extends Contractor
{
	public WareHouse giveTask(WareHouse inhouse)
	{
		WorkerLocal[] wks = getWaitingWorkers("faultworker");
		System.out.println("wks.length:"+wks.length);
		
		WareHouse wh = new WareHouse("word", "hello");
		WareHouse result = wks[0].doTask(wh);
		System.out.println("result:"+result);
		
		while(true){
			if(result.getStatus()==WareHouse.READY){
				System.out.println("result:"+result);
				break;
			}
			else if(result.getStatus()==WareHouse.EXCEPTION){
				System.out.println("something wrong about wks[0] result");
				//doTask(wh) again or put wh into log
				break;
			}
		}
		
		return null;
	}
	
	public static void main(String[] args)
	{
		FaultCtor a = new FaultCtor();
		a.giveTask(null);
		a.exit();
	}
}