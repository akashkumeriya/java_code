package demographic_data;
import org.json.JSONException;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import au.com.bytecode.opencsv.CSVReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class csv_to_json {
    public static void main(String[] args) throws IOException {
        String csvFilePath = "/home/shatam-system-i2/Documents/saurabh/Consumption1_updated.csv";
        String opFilePath = "/home/shatam-system-i2/Documents/saurabh/Consumption1_updated.json";
        CSVReader reader = new CSVReader(new FileReader(csvFilePath));

        String lines[] = null;
        String header[] = null;
        JsonArray jArray  = new JsonArray();
        int row = 0;
        while( (lines = reader.readNext()) != null) {
            //header of csv file
            if(row ++ == 0) {
                header = lines;
                continue;
            }

            JsonObject obj = new JsonObject();
            for(int i = 0; i < header.length; i++) {
                obj.addProperty(header[i], lines[i]);
            }
            jArray.add(obj); //add to Array
        }
        reader.close();
        // FileUtil.writeAllText(opFilePath, jArray.toString());
        
        // Write JSON to file
        try (FileWriter jsonWriter = new FileWriter(opFilePath)) {
            jsonWriter.write(jArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
