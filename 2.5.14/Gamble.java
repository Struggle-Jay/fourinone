public class Gamble
{
	public static double x1=1.0,x2=1.0,x3=1.0;
	
	public static void playgame(){
		/*double x2_income=x1/2.0;
		double x3_income=x1/2.0+x2;
		double x1_income=x3;*/
		
		double x2_income=x1/2.0+x3/2.0;//income
		double x3_income=x1/2.0+x2;
		double x1_income=x3/2.0;
		
		/*double x2_income=x1/2.0d+x3;
		double x3_income=x1/2.0d+x2;
		double x1_income=0;*/
		
		x1=x1_income;
		x2=x2_income;
		x3=x3_income;
		System.out.println("x1:"+x1+", x2:"+x2+", x3:"+x3);
	}
	
	public static void main(String[] args){
		for(int i=0;i<108;i++){
			System.out.print("µÚ"+i+"ÂÖ ");
			playgame();
		}
		//System.out.println(1.0d/2.0d);
	}
}