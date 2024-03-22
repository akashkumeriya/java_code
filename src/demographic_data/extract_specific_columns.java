
package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class extract_specific_columns {

    public static void main(String[] args) {
        String inputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/water_data/census_gdb_ca_2020_json/output7.csv"; // Path to the input CSV file
        String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/Aklash.csv"; // Path to the output CSV file

        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            List<String[]> lines = reader.readAll();
            List<String[]> selectedColumns = selectSpecificColumns(lines);

            writer.writeAll(selectedColumns);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Function to select specific columns
    private static List<String[]> selectSpecificColumns(List<String[]> lines) {
        List<String[]> selectedColumns = new ArrayList<>();
        
        // Specify the indexes of columns you want to select
        int[] columnIndexesToSelect = {1,2, 3, 13, 83, 97}; // Change these indexes to your desired columns
        
        for (String[] line : lines) {
            List<String> selectedValues = new ArrayList<>();
            for (int index : columnIndexesToSelect) {
                if (index < line.length) {
                    selectedValues.add(line[index]);
                }
            }
            selectedColumns.add(selectedValues.toArray(new String[0]));
        }
        
        return selectedColumns;
    }
}
