package demographic_data;

public class BaseClass{
	  public static void main(String[] args) {
		  PolymorphismExample obj = new PolymorphismExample();
		  PolymorphismExample obj1= new PolymorphismExample2();
		  PolymorphismExample obj2= new PolymorphismExample3();
		  obj.processOfDevelopment();
		  obj1.processOfDevelopment();
		  obj2.processOfDevelopment();  
	    }
}



