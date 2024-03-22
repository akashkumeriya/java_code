package demographic_data;



public class PolymorphismExample11{
	PolymorphismExample11(){
	        System.out.println("This is default constructor");
	    }  
	    
	PolymorphismExample11(int radius){
	        double area = 2*3.14*radius;
	        System.out.println("Area of Circle is:  "+area);
	    }  

	PolymorphismExample11(int length, int width){
	        int area = length * width;
	        System.out.println("Area of Rectangle is:  "+area);
	    }
	    public static void main(String[] args) {
	        int radius = 5;
	        PolymorphismExample11 circle = new PolymorphismExample11(radius);
	        
	        int length = 5;
	        int width = 10;
	        PolymorphismExample11 rectangle = new PolymorphismExample11(length, width);
	    }
	}
