import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;
import com.fourinone.FileAdapter;
public class JobWorker extends MigrantWorker
{	
	public WareHouse doTask(WareHouse inhouse)
	{
		JobHelp.print();
		String word = inhouse.getString("word");
		System.out.println(word+" from Contractor.");
		try{Thread.sleep(2000L);}catch(Exception ex){}
		System.out.println("done.");
		return new WareHouse("word", word+" worldddddddddddd! ");
	}
}