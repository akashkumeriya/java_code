package demographic_data;
import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class monthwise_consumption {
    public static void main(String[] args) throws IOException {
        String filePath = "/home/shatam-system-i2/Documents/saurabh/Consumption1_updated.csv";

        CSVReader reader = new CSVReader(new FileReader(filePath));

        List<String[]> csvRecords = new ArrayList<>();

        // Skip the header line
        reader.readNext(); // Skip the header

        // Read records from the CSV file
        String[] record;
        while ((record = reader.readNext()) != null) {
            csvRecords.add(record);
        }

       // Scanner scanner = new Scanner(System.in);
       // System.out.println("Enter year and month (yyyy-MM) to calculate total:");
        
        
      //..............................................................................................  
        
        
        
        String inputYearMonth = "2020-04";
        
        
      //.............................................................................................  
        

        double allocationTotal = 0.0;
        double consumptionTotal = 0.0;
        double perAllocationTotal = 0.0;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Map to store totals for each year-month combination
        Map<String, Double[]> totalsMap = new HashMap<>();

        // Calculate the totals for each year-month combination
        for (String[] row : csvRecords) {
            LocalDate currentDate = LocalDate.parse(row[2], formatter);
            String currentMonthYear = currentDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));

            if (currentMonthYear.equals(inputYearMonth)) {
                Double[] totals = totalsMap.getOrDefault(currentMonthYear, new Double[3]);
                totals[0] = totals[0] == null ? 0 : totals[0] + Double.parseDouble(row[3]);
                totals[1] = totals[1] == null ? 0 : totals[1] + Double.parseDouble(row[4]);
                totals[2] = totals[2] == null ? 0 : totals[2] + Double.parseDouble(row[5]);

                totalsMap.put(currentMonthYear, totals);
            }
        }

        // Output totals for the specified year and month
        Double[] totals = totalsMap.getOrDefault(inputYearMonth, new Double[]{0.0, 0.0, 0.0});
        allocationTotal = totals[0];
        consumptionTotal = totals[1];
        perAllocationTotal = totals[2];

        System.out.println("Total Allocation for " + inputYearMonth + ": " + allocationTotal);
        System.out.println("Total Consumption for " + inputYearMonth + ": " + consumptionTotal);
        
        
        
        double per= allocationTotal/consumptionTotal*100;
        
        System.out.println("% allocation = "+per);
        
        
        
       // System.out.println("Total Per_Allocation for " + inputYearMonth + ": " + perAllocationTotal);

        // Close resources
        reader.close();
    }
}
