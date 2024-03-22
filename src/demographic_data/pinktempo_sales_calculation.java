package demographic_data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class pinktempo_sales_calculation {

    private final static String pinktempo_input = "/home/shatam-system-i2/Desktop/sales_1_18_march.csv";
    private final static String OUTPUT_FIXADDRESS_LOGIN_PASS_FILEPATH = "/home/shatam-system-i2/Desktop/reports.csv";

    public static void main(String[] args) throws IOException {

        String strFile = "/home/shatam-system-i2/Desktop/sales_1_18_march.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(OUTPUT_FIXADDRESS_LOGIN_PASS_FILEPATH));
        CSVReader reader = new CSVReader(new FileReader(strFile));
        String[] nextLine;

        Multimap<String, Double> salesMultimap = ArrayListMultimap.create();
        Multimap<String, Double> netQuantityMultimap = ArrayListMultimap.create();

        while ((nextLine = reader.readNext()) != null) {
            String productNameSingle = nextLine[16];
            String variantName = nextLine[17];
            String productName = productNameSingle + variantName;
            String values = nextLine[26];

            if (!values.equals("Total sales")) {
                Double saleValue = Double.parseDouble(values);
                salesMultimap.put(productName.toLowerCase(), saleValue);
            }

            // Assuming net quantity is stored in column index 27
            String netQuantityValue = nextLine[19];
            if (!netQuantityValue.equals("Net quantity")) {
                Double netQuantity = Double.parseDouble(netQuantityValue);
                netQuantityMultimap.put(productName.toLowerCase(), netQuantity);
            }
        }

        // Calculate total sales for each product
        Map<String, Double> totalSalesMap = new HashMap<>();
        for (String productName : salesMultimap.keySet()) {
            Collection<Double> salesValues = salesMultimap.get(productName);
            double totalSales = salesValues.stream().mapToDouble(Double::doubleValue).sum();
            totalSalesMap.put(productName, totalSales);
        }

        // Calculate total net quantity for each product
        Map<String, Double> totalNetQuantityMap = new HashMap<>();
        for (String productName : netQuantityMultimap.keySet()) {
            Collection<Double> netQuantityValues = netQuantityMultimap.get(productName);
            double totalNetQuantity = netQuantityValues.stream().mapToDouble(Double::doubleValue).sum();
            totalNetQuantityMap.put(productName, totalNetQuantity);
        }

        // Write data to CSV
        for (String productName : totalSalesMap.keySet()) {
            double totalSales = totalSalesMap.get(productName);
            double totalNetQuantity = totalNetQuantityMap.getOrDefault(productName, 0.0);

            String[] data = {productName, String.valueOf(totalSales), String.valueOf(totalNetQuantity)};
            writer.writeNext(data);
        }

        writer.flush();
        writer.close();
        System.out.println("Data written to CSV successfully.");
    }
}
