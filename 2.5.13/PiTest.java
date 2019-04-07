import java.math.BigDecimal;
import java.util.Date;

public class PiTest
{
	public static void main(String[] args)
	{
		/*System.out.println(Math.PI);
		BigDecimal a = new BigDecimal("1.0");
		BigDecimal b = new BigDecimal("3.0");
		System.out.println(a.divide(b,1000,BigDecimal.ROUND_HALF_EVEN));*/
		//3.1415926535 8979323846 2643383279 
		//¦Ð=4*¡Æ(-1)^n+1/(2n-1)
		long begin = (new Date()).getTime();
		double pi=0.0;//(3.1415926525880504/4)3.141592653488346
		for(double i=1.0;i<1000000000d;i++){//1000000d-1000000001d
			pi += Math.pow(-1,i+1)/(2*i-1);
		}
		System.out.println(4*pi);
		long end = (new Date()).getTime();
		System.out.println("time:"+(end-begin)/1000+"s");
		
		/*BigDecimal pi = new BigDecimal("1.0");
		BigDecimal a,b=null;
		for(int i=2;i<=10000000;i++){
			a = new BigDecimal(Math.pow(-1,i+1));
			b = new BigDecimal(2*i-1);
			pi=pi.add(a.divide(b,1000,BigDecimal.ROUND_HALF_EVEN));
		}
		System.out.println(pi.multiply(new BigDecimal(4)));*/
		//System.out.println(Math.pow(-1,3));
	}
}
