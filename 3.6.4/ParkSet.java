import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;
import com.fourinone.AuthPolicy;
public class ParkSet{
	public static void main(String[] args){
		//��ȡparkserver�û��ӿ�
		ParkLocal pl = BeanContext.getPark();
		
//��domain d1�´����ڵ�node n1,ָ��Ȩ��Ϊֻ��
		ObjectBean d1n1 = pl.create("d1","n1","v1",AuthPolicy.OP_READ);
		if(d1n1!=null)
			System.out.println("d1n1 with AuthPolicy.OP_READ create success!");
		
		//��domain d2�´����ڵ�node n2,ָ��Ȩ��Ϊ��д
		ObjectBean d2n2 = pl.create("d2","n2","v2",AuthPolicy.OP_READ_WRITE);
		if(d2n2!=null)
			System.out.println("d2n2 with AuthPolicy.OP_READ_WRITE create success!");
		
		//��domain d3�´����ڵ�node n3,ָ��Ȩ��Ϊ����
		ObjectBean d3n3 = pl.create("d3","n3","v3",AuthPolicy.OP_ALL);
		if(d3n3!=null)
			System.out.println("d3n3 with AuthPolicy.OP_ALL create success!");
		
		//��domain d4�´����ڵ�node n4,ָ��Ȩ��Ϊ���У����Ҵ������ǿ������Ϊ�������̿�ɾ��
		ObjectBean d4n4 = pl.create("d4","n4","v4",AuthPolicy.OP_ALL);
		if(d4n4!=null)
			System.out.println("d4n4 with AuthPolicy.OP_ALL create success!");
		boolean r = pl.setDeletable("d4");
		if(r)
			System.out.println("set d4 deletable!");
	}
}