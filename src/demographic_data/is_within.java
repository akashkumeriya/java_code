
package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

//... (imports and class definition remain the same)

public class is_within {

 public static void main(String[] args) throws IOException {
     String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/demographic_layer_waterdistrict.csv";
     String outputFilePath2 = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/parcels_res.csv";
     String outputFilePath3 = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/matched_data.csv"; // New output file

     String[] cell;
     String[] cell2;

     try (CSVReader reader1 = new CSVReader(new FileReader(outputFilePath))) {
         reader1.readNext(); // Skip header row if present

         while ((cell = reader1.readNext()) != null) {
             if (cell.length > 0) {
                 String firstColumnValue = cell[2]; // Access first column value

                 try (CSVReader reader2 = new CSVReader(new FileReader(outputFilePath2))) {
                     reader2.readNext(); // Skip header row if present

                     while ((cell2 = reader2.readNext()) != null) {
                         if (cell2.length > 4) {
                             String firstColumnValue2 = cell2[4]; // Access fifth column value

                             GeometryFactory geometryFactory = new GeometryFactory();
                             WKTReader wktReader = new WKTReader(geometryFactory);

                             try {
                                 MultiPolygon firstMultiPolygon = (MultiPolygon) wktReader.read(firstColumnValue);
                                 MultiPolygon secondMultiPolygon = (MultiPolygon) wktReader.read(firstColumnValue2);

                                 boolean isContained = firstMultiPolygon.contains(secondMultiPolygon);

                                 if (isContained) {
                                     System.out.println("The second multipolygon is contained within the first multipolygon.");

                                     // Gather data from both CSV files
                                     String[] combinedData = Arrays.copyOf(cell, cell.length + cell2.length);
                                     System.arraycopy(cell2, 0, combinedData, cell.length, cell2.length);

                                     // Write combinedData to the third CSV file
                                     try (CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath3, true))) {
                                         writer.writeNext(combinedData);
                                     } catch (IOException e) {
                                         e.printStackTrace();
                                     }
                                     
                                     
                                 } else {
                                   //  System.out.println("The second multipolygon is NOT contained within the first multipolygon.");
                                 }
                             } catch (ParseException e) {
                                 e.printStackTrace();
                             }
                             
                             
                         }
                     }
                 }
             }
            
         }
     } catch (IOException e) {
         e.printStackTrace();
     }
 }
}
   