import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;

public class JobCtor extends Contractor
{
	public WareHouse giveTask(WareHouse inhouse)
	{
		WorkerLocal[] wks = getWaitingWorkers("simpleworker");
		System.out.println("wks.length:"+wks.length);
		wks[0].setWorker(new JobWorker());//com.job.
		
		WareHouse wh = new WareHouse("word", "hello");
		WareHouse result = wks[0].doTask(wh);
		System.out.println("result:"+result);

		while(true){
			if(result.isReady()){
				System.out.println("result:"+result);
				break;
			}
		}
		return null;
	}
	
	public static void main(String[] args)
	{
		JobCtor a = new JobCtor();
		a.giveTask(null);
		a.exit();
	}
}