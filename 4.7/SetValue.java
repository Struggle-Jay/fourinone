import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;

public class SetValue
{
	public static void main(String[] args)
	{
		ParkLocal pl = BeanContext.getPark();
		int i=0;
		while(true){
			ObjectBean ob = pl.create(i+"", i+"", i+"");
			System.out.println("No."+i);
			System.out.println(ob);
			i=i+1;
		}
	}
}