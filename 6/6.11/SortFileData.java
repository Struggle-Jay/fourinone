import com.fourinone.FileAdapter;
import com.fourinone.FileAdapter.IntReadAdapter;
import com.fourinone.FileAdapter.IntWriteAdapter;
import java.util.Random;
import java.util.List;

public class SortFileData
{
	public static void creatData(int total, int max, String path)
	{
		System.out.println("create "+total+" number(max:"+max+") to "+path+"...");
		int every = 500000;
		FileAdapter fa = new FileAdapter(path);
		fa.delete();
		Random rad = new Random();
		while(total>0){
			IntWriteAdapter wa = fa.getIntWriter();
	        int[] nums = new int[total-every<0?total:every];
	        for(int i=0;i<nums.length;i++){
	        	nums[i]=rad.nextInt(max);
	        }
	        wa.writeInt(nums);
	        total-=nums.length;
	        
		}
		System.out.println("create done.");
		fa.close();
	}
	
	public static void checkData(String path)
	{
		FileAdapter fa = new FileAdapter(path);
        List<Integer> rls = fa.getIntReader(0,100).readListIntAll();
        System.out.println(rls+"...");
	}
	
	public static void main(String[] args)
	{
		creatData(25000000,10000000,"data\\2008\\data");
		checkData("data\\2008\\data");
		creatData(25000000,10000000,"data\\2009\\data");
		checkData("data\\2009\\data");
		creatData(25000000,10000000,"data\\2010\\data");
		checkData("data\\2010\\data");
		creatData(25000000,10000000,"data\\2011\\data");
		checkData("data\\2011\\data");
	}
}