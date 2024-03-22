package demographic_data;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class a1 {

    public static void main(String[] args) throws IOException { // Changed "maon" to "main"

        String csvFile1 = "/home/shatam-system-i2/Downloads/orders_export_1 (1).csv";

        // Create a CSVReader to read from the input CSV file
        CSVReader reader = new CSVReader(new FileReader(csvFile1));

        // Create a CSVWriter to write to the output CSV file
        CSVWriter writer = new CSVWriter(new FileWriter("/home/shatam-system-i2/Desktop/varient_wise_profit.csv"));

        String[] cell1;

        String order_id;
        String sale_id;
        String date;
        String order;
        String product;
        String varient;
        String net_quantity;
        String gross_sales;
        String discount;
        String returns;
        String net_sales;
        String shipping;
        String taxes;
        String total_sales;

        while ((cell1 = reader.readNext()) != null) {
        	
        	
        	
        	
        	
        	
            order_id = cell1[0];
            sale_id = cell1[1];
            date = cell1[2];
            order = cell1[3];
            product = cell1[16];
            varient = cell1[17];
            net_quantity = cell1[19];
            gross_sales = cell1[20];
            discount = cell1[21];
            returns = cell1[22];
            net_sales = cell1[23];
            shipping = cell1[24];
            taxes = cell1[25];
            total_sales = cell1[26];
            
            
            
          //  if (product.contains("Kings oil")) {
            
            String productLowerCase = product.toLowerCase();

            if (productLowerCase.contains("rin") || productLowerCase.contains("surf") || productLowerCase.contains("vim") || 
                productLowerCase.contains("santoor") || productLowerCase.contains("dettol") || 
                productLowerCase.contains("cinthol") || productLowerCase.contains("lifebuoy") || 
                productLowerCase.contains("harpic") || productLowerCase.contains("vim") || 
                productLowerCase.contains("wheel") || productLowerCase.contains("lux")|| productLowerCase.contains("liquid gel") || 
                productLowerCase.contains("dove soap")|| productLowerCase.contains("detergent")|| productLowerCase.contains("soap")|| 
                productLowerCase.contains("handwash")|| productLowerCase.contains("bar")|| productLowerCase.contains("shampoo")
                || productLowerCase.contains("cleaner")|| productLowerCase.contains("godrej")|| productLowerCase.contains("sunsilk")|| productLowerCase.contains("vatika")
                || productLowerCase.contains("nirma")|| productLowerCase.contains("ghadi")|| productLowerCase.contains("cleaner")|| productLowerCase.contains("conditioner")
                
                
                || productLowerCase.contains("wheel active blue powder")|| productLowerCase.contains("comfort fabric conditioner")|| productLowerCase.contains("toilet cleaner") 
                || productLowerCase.contains("face wash")|| productLowerCase.contains("easy wash")|| productLowerCase.contains("liquid gel")|| productLowerCase.contains("floor cleaner") 

                || productLowerCase.contains("Clinic Plush - With Egg Protein")|| productLowerCase.contains("Dove Soup")|| productLowerCase.contains("Nirma powder") 

                
                
                || productLowerCase.contains("face wash")|| productLowerCase.contains("Expert")|| productLowerCase.contains("easy wash")) {
              

            
            	
            	

            String[] data3 = { order_id, sale_id, date, order, product, varient, net_quantity, gross_sales, discount, returns, net_sales, shipping, taxes, total_sales };

            writer.writeNext(data3);
            writer.flush();
            
            }
        }

        // Close the reader and writer to release resources
        reader.close();
        writer.close();
        
        System.out.println("execution done");
    }
}
