package demographic_data;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.MultiPolygon;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKTReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class data_intersect_demographic {
    public static void main(String[] args) {
        String file1Path = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/demographic_layer.csv";
        String file2Path = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/parcels_res.csv";

        int columnForFirstMultiPolygon = 2;
        int columnForSecondMultiPolygon = 4;

        try {
            MultiPolygon[] firstMultiPolygons = readMultiPolygonsFromCSV(file1Path, columnForFirstMultiPolygon);
            MultiPolygon[] secondMultiPolygons = readMultiPolygonsFromCSV(file2Path, columnForSecondMultiPolygon);

            if (firstMultiPolygons != null && secondMultiPolygons != null) {
                System.out.println("First MultiPolygons count: " + firstMultiPolygons.length);
                System.out.println("Second MultiPolygons count: " + secondMultiPolygons.length);

                for (MultiPolygon firstMultiPolygon : firstMultiPolygons) {
                    for (MultiPolygon secondMultiPolygon : secondMultiPolygons) {
                        if (firstMultiPolygon != null && secondMultiPolygon != null) {
                            boolean isContained = firstMultiPolygon.contains(secondMultiPolygon);

                            if (isContained) {
                                System.out.println("One MultiPolygon from the first column contains one MultiPolygon from the second column.");
                            } else {
                              //  System.out.println("No containment between MultiPolygons from the first and second columns.");
                            }
                            
                            
                            boolean isContains = firstMultiPolygon.contains(secondMultiPolygon);
                            boolean isWithin = firstMultiPolygon.within(secondMultiPolygon);
                            boolean isOverlaps = firstMultiPolygon.overlaps(secondMultiPolygon);
                            boolean isIntersects = firstMultiPolygon.intersects(secondMultiPolygon);

                            // Print results
                            System.out.println("Contains: " + isContains);
                            System.out.println("Within: " + isWithin);
                            System.out.println("Overlaps: " + isOverlaps);
                            System.out.println("Intersects: " + isIntersects);
                      
                            
                            
                            
                            
                            
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private static MultiPolygon[] readMultiPolygonsFromCSV(String filePath, int columnIndex)
            throws IOException, ParseException {
        List<MultiPolygon> multiPolygons = new ArrayList<>();
        GeometryFactory geometryFactory = new GeometryFactory();
        WKTReader wktReader = new WKTReader(geometryFactory);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine(); // Skip header if exists

            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

                if (columns.length > columnIndex) {
                    String wkt = extractMultiPolygonWKT(columns[columnIndex]);

                    if (wkt != null) {
                        MultiPolygon multiPolygon = (MultiPolygon) wktReader.read(wkt);
                        multiPolygons.add(multiPolygon);
                    } else {
                        System.out.println("No WKT extracted from column: " + columns[columnIndex]);
                    }
                }
            }
        }

        return multiPolygons.toArray(new MultiPolygon[0]);
    }

    private static String extractMultiPolygonWKT(String columnData) {
        columnData = columnData.replaceAll("^\"|\"$", "");
        int startIdx = columnData.indexOf("MULTIPOLYGON");
        int endIdx = columnData.lastIndexOf(")");
        if (startIdx >= 0 && endIdx > startIdx) {
            return columnData.substring(startIdx, endIdx + 1);
        }
        return null;
    }
}
