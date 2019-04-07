import java.util.Date;
public class CombTest
{
	int m=0,n=0,total=0;
	CombTest(int n, int m){
		this.m=m;
		this.n=n;
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
		CombTest ct = new CombTest(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
		long begin = (new Date()).getTime();
		ct.comb("");
		System.out.println("total:"+ct.total);
		long end = (new Date()).getTime();
		System.out.println("time:"+(end-begin)/1000+"s");
	}
}