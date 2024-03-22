package demographic_data;

public class PolymorphismExample66 {
    public static void main(String[] args) {
         
    	String a="ShaTAM";
    	
    	int j=0;
    	int k=0;
    	
			
			for(int i=0;i<a.length();i++)
			{
				
				char alpha=a.charAt(i);
				
				System.out.println(a.charAt(i));
				if(alpha<97)
				{
					System.out.println("upper");
					j++;
				}
				else
				{
					System.out.println("lower");
					k++;
				}
			}
			System.out.print("total upper case is ="+j+"    ");
			System.out.print("total lower case is ="+k);
    	
    }
}



	