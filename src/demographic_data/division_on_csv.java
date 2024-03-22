package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class division_on_csv {

    public static void main(String[] args) {
        String inputFile = "/home/shatam-system-i2/akash_created_demographic_project/water_data/census_gdb_ca_2020_json/output7.csv"; // Replace with your input CSV file
        String outputFile = "/home/shatam-system-i2/akash_created_demographic_project/dummy/output.csv"; // Replace with your output CSV file

        // Columns for division
        int columnIndex1 = 13; // Replace with the index of the first column (0-based index)
        int columnIndex2 = 79; // Replace with the index of the second column (0-based index)

        try {
            CSVReader reader = new CSVReader(new FileReader(inputFile));
            CSVWriter writer = new CSVWriter(new FileWriter(outputFile));

            List<String[]> outputData = new ArrayList<>();
            String[] line;

            while ((line = reader.readNext()) != null) {
                String[] outputLine = new String[line.length + 1]; // Increase the length by 1 for the new column
                System.arraycopy(line, 0, outputLine, 0, line.length);

                try {
                    double val1 = Double.parseDouble(line[columnIndex1]);
                    double val2 = Double.parseDouble(line[columnIndex2]);
                    double result = val1 / val2;
                    outputLine[line.length] = String.valueOf(result); // Add the result as a new column
                } catch (NumberFormatException | ArithmeticException e) {
                    outputLine[line.length] = "Error";
                }

                outputData.add(outputLine);
            }

            writer.writeAll(outputData);
            writer.close();
            reader.close();

            System.out.println("Division performed and stored in " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
