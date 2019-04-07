import com.fourinone.Contractor;
import com.fourinone.WareHouse;
import com.fourinone.WorkerLocal;
import java.util.Iterator;

public class PageRankCtor extends Contractor
{
	public WareHouse giveTask(WareHouse inhouse)
	{
		WorkerLocal[] wks = getWaitingWorkers("pagerankworker");
		System.out.println("wks.length:"+wks.length);
		
		for(int i=0;i<500;i++){//500��
			WareHouse[] hmarr = doTaskBatch(wks, inhouse);
			WareHouse prwh = new WareHouse();
			for(WareHouse result:hmarr){
				for(Iterator iter=result.keySet().iterator();iter.hasNext();){
					String page = (String)iter.next();
					Double pagepr = (Double)result.getObj(page);
					if(prwh.containsKey(page))
						pagepr = pagepr+(Double)prwh.getObj(page);
					prwh.setObj(page,pagepr);
				}
			}
			inhouse = prwh;//����
			System.out.println("No."+i+":"+inhouse);
		}
		return inhouse;
	}
	
	public static void main(String[] args)
	{
		PageRankCtor a = new PageRankCtor();
		WareHouse inhouse = new WareHouse();
		inhouse.setObj("A",1.00d);//A��pr��ʼֵ
		inhouse.setObj("B",1.00d);//B��pr��ʼֵ
		inhouse.setObj("C",1.00d);//C��pr��ʼֵ
		a.giveTask(inhouse);
		a.exit();
	}
}