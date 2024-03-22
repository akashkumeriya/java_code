package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class replace_header_names {
    public static void main(String[] args) {
        String inputFilePath = "data.csv"; // Replace with your data file path
        String replacementFilePath = "replacement.csv"; // Replace with your replacement file path
        String outputFilePath = "output.csv"; // Replace with your output file path

        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            // Read replacement headers from the replacement file
            Map<String, String> replacementMap = readReplacementFile(replacementFilePath);

            String[] headers = reader.readNext(); // Read headers

            // Replace headers if replacements exist
            for (int i = 0; i < headers.length; i++) {
                if (replacementMap.containsKey(headers[i])) {
                    headers[i] = replacementMap.get(headers[i]);
                }
            }

            // Write modified headers to the output CSV file
            writer.writeNext(headers);

            // Write the remaining data rows from the original CSV file
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                writer.writeNext(nextLine);
            }

            System.out.println("Replacement of headers completed successfully.");
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, String> readReplacementFile(String filePath) throws IOException, CsvValidationException {
        Map<String, String> replacementMap = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                replacementMap.put(nextLine[0], nextLine[1]); // Assumes first column as original header and second column as replacement
            }
        }
        return replacementMap;
    }
}
