package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class only_single_column_read {

    public static void main(String[] args) {
    	  String inputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/ACS_2020_5YR_BG_2020 (1).csv"; // Path to the input CSV file
    	  
          String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/new_generated_for_write.csv"; // Path to the output CSV file
          
           int columnToRead = 14; // Column index (0-based) to read

        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            List<String[]> lines = reader.readAll();
            for (String[] line : lines) {
                if (columnToRead < line.length) {
                    String[] dataToWrite = {line[columnToRead]};
                    writer.writeNext(dataToWrite);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
