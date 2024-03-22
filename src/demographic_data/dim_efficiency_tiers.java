package demographic_data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import au.com.bytecode.opencsv.CSVReader;

	public class dim_efficiency_tiers {

		public static void main(String[] args) throws IOException {
	        // Set the path to the ChromeDriver executable


			String premise_id;
			String consumption=null;
			String allocation=null;
			
			 double above200 = 0;
			 double between151And200 = 0;
			 double between101And150 = 0;
			 double between51And100 = 0;
			 double below50 = 0;
			 
			 int countabove200=0;
			 int countbetween151And200=0;
			 int countbetween101And150=0;
			 int countbetween51And100=0;
			 int countbelow50=0;
			 double percent=0;
	    	
			CSVReader reader = null;
			try
			{
				reader = new CSVReader(new FileReader("/home/shatam-system-i2/Documents/saurabh/Consumption1_updated.csv"));
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}

			String[] cell;

			while ((cell = reader.readNext()) != null) 
			{
				for (int i = 0; i < 1; i++) 
				{
					premise_id = cell[i];
					consumption = cell[i + 4];
					allocation = cell[i + 3];
					
					if (!consumption.equals("Consumption") && !consumption.isEmpty()
			                && !allocation.isEmpty()) 
					{
			            double new_consumption = Double.parseDouble(consumption); 
			            double new_allocation = Double.parseDouble(allocation);  
			            
			            percent = new_consumption / new_allocation * 100;
	
			      //      System.out.println(premise_id+"  "+new_consumption+"  "+new_allocation+" "+percent+" ");
			            
			                if (percent > 200) {
			                    above200++;
			                    countabove200++;
			                    
			                //    System.out.println(premise_id+" ======== "+"new_consumption=="+new_consumption+"new_allocation=="+new_allocation+"percent"+percent+" ======");
			                  //  System.out.println("  "+premise_id+" "+new_allocation);
			                    
			                } 
			                else if (percent >= 151 && percent <= 200) 
			                {
			                    between151And200++;
			                    countbetween151And200++;
			                   
			                }
			                else if (percent >= 101 && percent<=150) 
			                {
			                    between101And150++;
			                    countbetween101And150++;
			                    
			                    
			                } 
			                else if (percent >= 51 && percent<=100) 
			                {
			                    between51And100++;
			                    countbetween51And100++;
			                } 
			                else
			                {
			                    below50++;
			                    countbelow50++;
			                  //  System.out.println(premise_id+" ======== "+percent+" ======");
			                }
			            }
					
					else if (!consumption.equals("Consumption"))
					{
						
						below50++;
						countbelow50++;
						// System.out.println(premise_id+" ======== "+percent+" ======");
					}
				}     
		}    
//			             System.out.println("countabove200==="+countabove200);
//			             System.out.println("countbetween151And200==="+countbetween151And200);
//			             System.out.println("countbetween101And150==="+countbetween101And150);
//			             System.out.println("countbetween51And100==="+countbetween51And100);
//			             System.out.println("ountbelow50==="+countbelow50);
			            System.out.println();
					    System.out.println("Percentage above 200% = " + above200/177*100);
				        System.out.println();
				        System.out.println("Percentage between 151% and 200% = " + between151And200/177*100);
				        System.out.println();
				        System.out.println("Percentage between 101% and 150% = " + between101And150/177*100);
				        System.out.println();
				        System.out.println("Percentage between 51% and 100% = " + between51And100/177*100);
				        System.out.println();
				        System.out.println("Percentage below 50% = " + below50/177*100);  
			    
			}
		
	    }
