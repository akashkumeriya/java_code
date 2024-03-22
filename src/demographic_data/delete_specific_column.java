package demographic_data;









import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class delete_specific_column {
    public static void main(String[] args) {
        // Input CSV file path
        String inputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/report_demographic.csv";
        // Output CSV file path
        String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/output_55.csv";
        // Column index to delete (zero-based index)
        int columnToDelete = 13; // Change this to the index of the column you want to delete

        try {
            // Read the input CSV file
            CSVReader reader = new CSVReader(new FileReader(inputFilePath));
            List<String[]> csvBody = reader.readAll();
            reader.close();

            // Iterate through each row and remove the column
            for (String[] row : csvBody) {
                if (columnToDelete < row.length) {
                    List<String> newRow = new ArrayList<>();
                    for (int i = 0; i < row.length; i++) {
                        if (i != columnToDelete) {
                            newRow.add(row[i]);
                        }
                    }
                    row = newRow.toArray(new String[0]);
                }
            }

            // Write the updated content to a new CSV file
            CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath));
            writer.writeAll(csvBody);
            writer.close();

            System.out.println("Column deleted successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
