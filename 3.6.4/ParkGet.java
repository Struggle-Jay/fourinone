import com.fourinone.BeanContext;
import com.fourinone.ParkLocal;
import com.fourinone.ObjectBean;
import java.util.List;
public class ParkGet{
	public static void main(String[] args){
		//��ȡparkserver�û��ӿ�
		ParkLocal pl = BeanContext.getPark();
		
		//��ȡ�ڵ�d1n1���ڵ�Ȩ��ΪAuthPolicy.OP_READ
		ObjectBean d1n1 = pl.get("d1","n1");//��ȡ�ڵ�
		System.out.println("get d1n1:"+(String)d1n1.toObject());
		d1n1 = pl.update("d1","n1","v1-update");//���½ڵ�
		if(d1n1!=null)
			System.out.println("update node d1n1 success!");
		else
			System.out.println("update node d1n1 failure!");
		List<ObjectBean> d1 = pl.delete("d1");//ɾ��domain
		if(d1!=null)
			System.out.println("delete domain d1 success!");
		else
			System.out.println("delete domain d1 failure!");
		d1n1 = pl.delete("d1","n1");//ɾ���ڵ�
		if(d1n1!=null)
			System.out.println("delete node d1n1 success!");
		else
			System.out.println("delete node d1n1 failure!");
		
		//��ȡ�ڵ�d2n2���ڵ�Ȩ��ΪAuthPolicy.OP_READ_WRITE
		ObjectBean d2n2 = pl.get("d2","n2");
		System.out.println("get d2n2:"+(String)d2n2.toObject());
		d2n2 = pl.update("d2","n2","v2-update");
		if(d2n2!=null)
			System.out.println("update node d2n2 success!");
		else
			System.out.println("update node d2n2 failure!");
		List<ObjectBean> d2 = pl.delete("d2");
		if(d2!=null)
			System.out.println("delete domain d2 success!");
		else
			System.out.println("delete domain d2 failure!");
		d2n2 = pl.delete("d2","n2");
		if(d2n2!=null)
			System.out.println("delete node d2n2 success!");
		else
			System.out.println("delete node d2n2 failure!");
		
		//��ȡ�ڵ�d3n3, �ڵ�Ȩ��ΪAuthPolicy.OP_ALL
		ObjectBean d3n3 = pl.get("d3","n3");
		System.out.println("get d3n3:"+(String)d3n3.toObject());
		d3n3 = pl.update("d3","n3","v3-update");
		if(d3n3!=null)
			System.out.println("update node d3n3 success!");
		else
			System.out.println("update node d3n3 failure!");
		List<ObjectBean> d3 = pl.delete("d3");
		if(d3!=null)
			System.out.println("delete domain d3 success!");
		else
			System.out.println("delete domain d3 failure!");
		d3n3 = pl.delete("d3","n3");
		if(d3n3!=null)
			System.out.println("delete node d3n3 success!");
		else
			System.out.println("delete node d3n3 failure!");
		
		//��ȡ�ڵ�d4n4���ڵ�Ȩ��ΪAuthPolicy.OP_ALL
		ObjectBean d4n4 = pl.get("d4","n4");
		System.out.println("get d4n4:"+(String)d4n4.toObject());
		d4n4 = pl.update("d4","n4","v4-update");
		if(d4n4!=null)
			System.out.println("update node d4n4 success!");
		else
			System.out.println("update node d4n4 failure!");
		//���ڴ��������Ѿ�ǿ��ָ����domain��ɾ��setDeletable(d4)������������ɾ����
		List<ObjectBean> d4 = pl.delete("d4");
		if(d4!=null)
			System.out.println("delete domain d4 success!");
		else
			System.out.println("delete domain d4 failure!");
		d4n4 = pl.delete("d4","n4");//����ɾ���ڵ��ʧ�ܣ���Ϊ�����Ѿ�ɾ���˸�domian�����нڵ�
		if(d4n4!=null)
			System.out.println("delete node d4n4 success!");
		else
			System.out.println("delete node d4n4 failure!");
	}
}