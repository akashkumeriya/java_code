package demographic_data;

public class practice2 {
	
	public static void main(String[] args)
	{
		String s1="java, selenium,testNG,Maven, Jenkins";
		
		
		String values[]=s1.split(",");
		
		for(int i=0;i<values.length;i++)
		{
			System.out.println(values[i]);
		}
	}

}
