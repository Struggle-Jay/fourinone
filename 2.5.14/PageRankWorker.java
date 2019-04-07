import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;
import com.fourinone.Workman;

public class PageRankWorker extends MigrantWorker
{
	public String page = null;
	public String[] links = null;
	
	public PageRankWorker(String page, String[] links){
		this.page = page;
		this.links = links;
	}

	public WareHouse doTask(WareHouse inhouse)
	{
		Double pr = (Double)inhouse.getObj(page);
		System.out.println(pr);
		
		WareHouse outhouse = new WareHouse();
		for(String p:links)
			outhouse.setObj(p, pr/links.length);//对包括的链接PR投票

		return outhouse;
	}
	
	public static void main(String[] args)
	{
		String[] links = null;
		if(args[2].equals("A"))
			links = new String[]{"B","C"};//A页面包括的链接
		else if(args[2].equals("B"))
			links = new String[]{"C"};
		else if(args[2].equals("C"))
			links = new String[]{"A"};
		
		PageRankWorker mw = new PageRankWorker(args[2],links);
		mw.waitWorking(args[0],Integer.parseInt(args[1]),"pagerankworker");
	}
}
