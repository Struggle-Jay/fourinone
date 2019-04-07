import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;

public class CombWorker extends MigrantWorker
{
	private int m=0,n=0,total=0,index=-1;

	public WareHouse doTask(WareHouse wh)
	{
		total=0;
		n = (Integer)wh.getObj("wknum");
		m = (Integer)wh.getObj("comb");
		index = getSelfIndex()+1;
		System.out.println("index:"+index);
		comb(index+"");
		System.out.println("total:"+total);
		return new WareHouse("total",total);
	}
	
	public void comb(String str)
	{
		for(int i=1;i<n+1;i++){
			if(str.length()==m-1){
				//System.out.println(str+i);//打印出组合序列
				total++;
			}
			else
				comb(str+i);
		}
	}
	
	public static void main(String[] args)
	{
		CombWorker mw = new CombWorker();
		mw.waitWorking(args[0],Integer.parseInt(args[1]),"CombWorker");
	}
}