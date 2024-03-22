package demographic_data;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public class now_json_to_csv {
    public static void main(String[] args) {
        // JSON file path
    	 String jsonFilePath = "/home/shatam-system-i2/Downloads/shapefile_california.json"; // Replace with your JSON file path

         // Define CSV file path
         String csvFilePath = "/home/shatam-system-i2/Desktop/shapefile_california.csv";

        try {
            // Read JSON data from file
            JsonParser parser = new JsonParser();
            JsonElement jsonElement = parser.parse(new FileReader(jsonFilePath));
            JsonArray jsonArray = jsonElement.getAsJsonArray();

            // Create FileWriter and CSVWriter objects
            FileWriter fileWriter = new FileWriter(csvFilePath);
            CSVWriter csvWriter = new CSVWriter(fileWriter);

            // Write CSV header
            JsonObject firstObject = jsonArray.get(0).getAsJsonObject();
            int numberOfKeys = firstObject.entrySet().size();
            String[] headers = new String[numberOfKeys];
            int index = 0;
            for (Map.Entry<String, JsonElement> entry : firstObject.entrySet()) {
                headers[index++] = entry.getKey();
            }
            csvWriter.writeNext(headers);

            // Write JSON data to CSV
            for (JsonElement element : jsonArray) {
                JsonObject jsonObj = element.getAsJsonObject();
                String[] data = new String[numberOfKeys];
                index = 0;
                for (Map.Entry<String, JsonElement> entry : jsonObj.entrySet()) {
                    JsonElement value = entry.getValue();
                    data[index++] = (value != null && !value.isJsonNull()) ? value.getAsString() : null;
                }
                csvWriter.writeNext(data);
            }

            // Close CSV writer
            csvWriter.close();

            System.out.println("Conversion completed. CSV file created at: " + csvFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
