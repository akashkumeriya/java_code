package demographic_data;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class consumption_for_single_premise_id {
    public static void main(String[] args) throws IOException {
        String filePath = "/home/shatam-system-i2/Documents/saurabh/Consumption1_updated.csv";

        CSVReader reader = new CSVReader(new FileReader(filePath));

        List<String[]> csvRecords = new ArrayList<>();

        // Read records from the CSV file
        String[] record;
        while ((record = reader.readNext()) != null) {
            csvRecords.add(record);
        }

        
       //.................................................................................................................... 
        
        
        String premiseIdToFind = "100903201";
        
        
        
        //.................................................................................................................... 
        
        

        LocalDate latestDate = LocalDate.MIN;
        String[] latestRecord = null;

        // Find the latest date for the entered Premise ID
        for (String[] row : csvRecords) {
            if (row[0].equals(premiseIdToFind)) {
                LocalDate currentDate = LocalDate.parse(row[2]); // Assuming ReadingDate is in the third column

                if (currentDate.compareTo(latestDate) > 0) {
                    latestDate = currentDate;
                    latestRecord = row;
                }
            }
        }

        // Output specific columns from the latest record for the entered Premise ID
        if (latestRecord != null) {
            System.out.println("Latest Record for Premise ID  =  " + premiseIdToFind);
            for (int i = 0; i < 6; i++) { // Change 5 to the number of columns you want (0-4 in this case)
            	
            	if( i==0)
            	{
            		System.out.println("Premise id = "+latestRecord[i]);
            	}
            	else if(i==1)
            	{
            		System.out.println("meter id = "+latestRecord[i]);
            	}
            	else if(i==2)
            	{
            		System.out.println("ReadingDate = "+latestRecord[i]);
            	}
            	else if(i==3)
            	{
            		System.out.println("Allocation = "+latestRecord[i]);
            	}
            	else if(i==4)
            	{
            		System.out.println("Consumption = "+latestRecord[i]);
            	}
            	else if(i==5)
            	{
            		System.out.println("Per_Allocation ="+latestRecord[i]);
            	}
            	
            	
              //  System.out.print(latestRecord[i] + "\t");
            }
            System.out.println(); // Move to the next line
        } else {
            System.out.println("No data found for Premise ID " + premiseIdToFind);
        }

        // Close resources
        reader.close();
    }
}
