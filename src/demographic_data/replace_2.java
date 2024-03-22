package demographic_data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class replace_2 {

    public static void main(String[] args) {
        String inputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/Aklash.csv";
        String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/kumeriya.csv";

        // Define your replacement mappings
        Map<String, String> replacements = new HashMap<>();
        replacements.put("B25010e1", "OCCUPIED HOUSING UNITS");
        replacements.put("B29004e1", "Median HH Income");
       
        
       
        
       
        // Add other replacements based on your mappings

        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            List<String[]> lines = reader.readAll();

            // Iterate through the lines and replace content based on mappings
            for (String[] line : lines) {
                for (int i = 0; i < line.length; i++) {
                    if (replacements.containsKey(line[i])) {
                        line[i] = replacements.get(line[i]);
                    }
                }
            }

            // Write modified content to the output CSV file
            writer.writeAll(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
