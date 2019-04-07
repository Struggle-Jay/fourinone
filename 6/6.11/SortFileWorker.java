import com.fourinone.MigrantWorker;
import com.fourinone.WareHouse;
import com.fourinone.Workman;
import com.fourinone.FileAdapter;
import com.fourinone.FileAdapter.ReadAdapter;
import com.fourinone.FileAdapter.WriteAdapter;
import com.fourinone.FileAdapter.IntReadAdapter;
import com.fourinone.FileAdapter.IntWriteAdapter;
import com.fourinone.ArrayAdapter;
import com.fourinone.ArrayAdapter.ListInt;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SortFileWorker extends MigrantWorker
{
	private int n=-1,max=-1,every=-1,m=-1,index=-1;
	private String path;
	private HashMap<Integer,List<Integer>> wharr = new HashMap<Integer,List<Integer>>();
	private Random rad = new Random();
	private Workman[] wms = null;
	private FileAdapter[] faws = null;

	public SortFileWorker(int n, int max, int every, String path){
		this.n = n;
		this.max = max;
		this.every = every;
		this.path = path;
		faws = new FileAdapter[n];
	}
		
	public WareHouse doTask(WareHouse wh){
		try{	
			int step = (Integer)wh.getObj("step");
			if(wms==null){
				wms = getWorkerAll();
				m = wms.length;
				for(int l=0;l<n;l++){
					faws[l] = new FileAdapter(new File(path).getParent()+"\\output\\"+l*m/n+l%(n/m));//"output/"+l*m/n+l%(n/m)
					faws[l].delete();
				}
			}
			index = getSelfIndex();
			System.out.println("wknum:"+m+";step:"+step);
			WareHouse resultWh = new WareHouse("ok",1);
			
			if(step==1){
				FileAdapter fa = new FileAdapter(path);
				IntReadAdapter ira = null;
				int begin = 0;
				int[] rls = null;
				while(true){
					ira = fa.getIntReader(begin,every);
					rls = ira.readIntAll();
					if(rls!=null){
						for(int i:rls){
							Integer numi = (int)(new Long(i)*new Long(n)/new Long(max));
							List<Integer> arr = wharr.get(numi);
							if(arr==null)
								arr = new ArrayList<Integer>();
							arr.add(i);
							wharr.put(numi, arr);
						}
						for(int j=0;j<n;j++){
							if(wharr.containsKey(j))
								faws[j].getIntWriter().writeListInt(wharr.remove(j));
						}
						begin+=rls.length;
					}else break;
				}
				fa.close();
			}else if(step==2){
				for(int j=0;j<n/m;j++){
					for(int i=0;i<m;i++){
						if(i!=index){
							if(faws[i*n/m+j].exists()){
								int[] itsn = faws[i*n/m+j].getIntReader().readIntAll();
								Workman wm = wms[i];
								WareHouse whij = new WareHouse();
								whij.put("i",i);
								whij.put("j",j);
								whij.put("v",itsn);
								System.out.println(i+"-receive:"+wm.receive(whij));
								faws[i*n/m+j].close();
								
							}
						}
					}
				}
			}else if(step==3){
				int[] arrl = null;
				int total = 0;
				for(int j=0;j<n/m;j++){
					if(faws[index*n/m+j].exists()){
						arrl = faws[index*n/m+j].getIntReader().readIntAll();
						ListInt is = ArrayAdapter.getListInt();
						is.sort(arrl);
						total+=arrl.length;
						faws[index*n/m+j].getIntWriter(0,arrl.length).writeInt(arrl);
						faws[index*n/m+j].close();
					}
					for(int i=0;i<m;i++)
						if(i!=index&&faws[i*n/m+j].exists())
							faws[i*n/m+j].delete();
				}
				resultWh.setObj("total",total);
				System.out.println("over.");
			}
			return resultWh;
		}catch(Exception ex){
			System.out.println(ex);
			return null;
		}
	}
	
	protected boolean receive(WareHouse inhouse)
	{
		Integer i = (Integer)inhouse.get("i");
		Integer j = (Integer)inhouse.get("j");
		int[] v = (int[])inhouse.get("v");
		faws[i*n/m+j].getIntWriter().writeInt(v);
		return true;
	}
	
	public static void main(String[] args)
	{
		SortFileWorker mw = new SortFileWorker(Integer.parseInt(args[2]),Integer.parseInt(args[3]),Integer.parseInt(args[4]),args[5]);
		mw.waitWorking(args[0],Integer.parseInt(args[1]),"SortWorker");
	}
}