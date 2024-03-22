package demographic_data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.opencsv.CSVWriter;

public class purchase {

    public static class Data {
        private String productName;
        private double price;
        private String quantity;

        public Data(String productName, double price, String quantity) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    public static ArrayList<Data> HandleCsvData() throws IOException {
        ArrayList<Data> dataList = new ArrayList<>();
        Map<String, Data> productMap = new HashMap<>(); // Map to store products by name

        String csvFilePath = "/home/shatam-system-i2/Desktop/marchpurchase.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            String[] headers = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.matches("\\s*") || line.length() < 10) {
                    continue;
                }
                String[] values = line.split(",");
                if (headers == null) {
                    headers = values;
                } else {
                    String productName = values[1].trim(); // Name of the product
                    String priceString = values[3].trim(); // Price
                    String quantityString = values[2].trim(); // Quantity

                    if (isValidNumeric(priceString) && !quantityString.isEmpty()) {
                        double price = Double.parseDouble(priceString.replace(",", "").replaceAll("[^\\d.]", ""));
                        String quantity = quantityString.replaceAll("[^\\w\\s]", "");

                        // Check if the product already exists in the map
                        if (productMap.containsKey(productName)) {
                            // If exists, update price and quantity
                            Data existingData = productMap.get(productName);
                            double updatedPrice = existingData.getPrice() + price;
                            existingData.setPrice(updatedPrice);
                            String mergedQuantity = mergeQuantities(existingData.getQuantity(), quantity);
                            existingData.setQuantity(mergedQuantity);
                        } else {
                            // If not exists, add new entry to the map
                            Data data = new Data(productName, price, quantity);
                            productMap.put(productName, data);
                        }
                    }
                }
            }

            // Convert productMap to dataList
            dataList.addAll(productMap.values());
        }

        // Write all data to the CSV file
        String outputfile = "/home/shatam-system-i2/Desktop/output_purchase.csv";
        try (CSVWriter writer = new CSVWriter(new FileWriter(outputfile))) {
            // Write headers
            writer.writeNext(new String[]{"Product Name", "Price", "Quantity"});
            // Write data
            for (Data data : dataList) {
                writer.writeNext(new String[]{data.getProductName(), String.valueOf(data.getPrice()), data.getQuantity()});
            }
        }

        return dataList;
    }

    public static String mergeQuantities(String quantity1, String quantity2) {
        return quantity1 + ", " + quantity2;
    }

    public static boolean isValidNumeric(String str) {
        return str != null && str.matches("-?\\d+(\\.\\d+)?");
    }

    public static void main(String[] args) throws IOException {
        HandleCsvData();
    }
}
