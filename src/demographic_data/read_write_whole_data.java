package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class read_write_whole_data {

    public static void main(String[] args) {
        String inputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/demographic_layer.csv"; // Path to the input CSV file
        String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/demographic_layer(machine).csv"; // Path to the output CSV file

        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            List<String[]> lines = reader.readAll();
            writer.writeAll(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

