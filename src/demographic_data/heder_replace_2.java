package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class heder_replace_2 {

    public static void main(String[] args) {
        String inputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/dec_7_demograp (1).csv";
        String outputFilePath = "/home/shatam-system-i2/akash_created_demographic_project/dummy/dec_7_demograp(correct).csv";

       
        Map<String, String> replacements = new HashMap<>();
//        replacements.put("B01001e1", "Population");
//        replacements.put("B01001e2", "Male Population");
//        replacements.put("B01001e26", "Female Population");
//        replacements.put("B25001e1", "Households");
//        replacements.put("B25003e1", "Total Occupied Units");
        replacements.put("B25010e1", "OCCUPIED HOUSING UNITS"); 
//        replacements.put("B25010e2", "Owner Occupied Units");
//        replacements.put("B25010e3", "Renter Occupied Units");
        replacements.put("B29004e1", "Median HH Income");
//        replacements.put("B19001e1", "Average HH Income");       
//        replacements.put("B19001e2", "Households Under $10,000");
//        replacements.put("B19001e3", "Households $10,000 - $14,999");
//        replacements.put("B19001e4", "Households $15,000 - $19,999");
//        replacements.put("B19001e5", "Households $20,000 - $24,999");
//        replacements.put("B19001e6", "Households $25,000 - $29,999");
//        replacements.put("B19001e7", "Households $30,000 - $34,999");
//        replacements.put("B19001e8", "Households $35,000 - $39,999");
//        replacements.put("B19001e9", "Households $40,000 - $44,999");
//        replacements.put("B19001e10", "Households $45,000 - $49,999");
//        replacements.put("B19001e11", "Households $50,000 - $59,999");
//        replacements.put("B19001e12", "Households $60,000 - $74,999");
//        replacements.put("B19001e13", "Households $75,000 - $99,999");
//        replacements.put("B19001e14", "Households $100,000 - $124,999");
//        replacements.put("B19001e15", "Households $125,000 - $149,999");
//        replacements.put("B19001e16", "Households $150,000 - $199,999");
//        replacements.put("B19001e17", "Households Over $200,000");
//        replacements.put("B19101e1", "Total Families");
//        replacements.put("B19101e2", "Families Under $10,000");
//        replacements.put("B19101e3", "Families $10,000 - $14,999");
//        replacements.put("B19101e4", "Families $15,000 - $19,999");
//        replacements.put("B19101e5", "Families $20,000 - $24,999");
//        replacements.put("B19101e6", "Families $25,000 - $29,999");
//        replacements.put("B19101e7", "Families $30,000 - $34,999");
//        replacements.put("B19101e8", "Families $35,000 - $39,999");
//        replacements.put("B19101e9", "Families $40,000 - $44,999");
//        replacements.put("B19101e10", "Families $45,000 - $49,999");
//        replacements.put("B19101e11", "Families $50,000 - $59,999");
//        replacements.put("B19101e12", "Families $60,000 - $74,999");
//        replacements.put("B19101e13", "Families $75,000 - $99,999");
//        replacements.put("B19101e14", "Families $100,000 - $124,999");
//        replacements.put("B19101e15", "Families $125,000 - $149,999");
//        replacements.put("B19101e16", "Families $150,000 - $199,999");
//        replacements.put("B19101e17", "Families Over $200,000");
//        replacements.put("B19101e18", "median family income");
//        replacements.put("B01002e1", "Median Age");
//        replacements.put("B01002e2", "Male Median Age");
//        replacements.put("B01002e3", "Female Median Age");
//        replacements.put("B01002Ae1", "White Median Age");
//        replacements.put("B01002Be1", "Black Median Age");
//        replacements.put("B01002De1", "Asian Median Age");
//        replacements.put("B01002Ee1", "Native American and Alaskan Native Median Age");
//        replacements.put("B01002Fm1", "Other Race Median Age");
//        replacements.put("B01002Ge1", "Two or More Races Median Age");
//        replacements.put("B01002Ie1", "Hispanic Median Age");
//        replacements.put("B01002He1", "White Non-Hispanic Median Age");
//        
//        
//        replacements.put("C02003e3", "White Alone");
//        replacements.put("C02003e4", "Black Alone");
//        replacements.put("C02003e6", "Asian Alone");
//        replacements.put("C02003e5", "Native American and Alaska Native Alone");
//        replacements.put("C02003e7", "Native Hawaiian");
//        replacements.put("C02003e8", "Other Race Alone");
//        replacements.put("C02003e9", "Two or More Races");
//        replacements.put("B03002e1", "Hispanic");
//        replacements.put("B03002e2", "White Non-Hispanic");
//        replacements.put("B11001Ae1", "White Households");
//        replacements.put("B11001Be1", "Black Households");
//        replacements.put("B11001Ce1", "Native American and Alaskan Native Head of Households");
//        replacements.put("B11001De1", "Asian Households");
//        replacements.put("B11001Ee1", "Households Native Hawaiian");
//        
//      
//        
//        replacements.put("B11001Fe1", "Other Race Head of Households");
//        replacements.put("B11001Ge1", "Two or More Races Head of Households");
//        replacements.put("B11001Ie1", "Hispanic Households");
//        replacements.put("B11001He1", "White Non-Hispanic Households");
//        replacements.put("B29002e3", "Less than High School");
//        replacements.put("B29002e4", "High School");
//        replacements.put("B29002e5", "Some College");
//        replacements.put("B29002e6", "Associates Degree");
//        replacements.put("B29002e7", "Bachelors Degree");
//        replacements.put("B29002e8", "Professional Degree");
//        
//        
//        replacements.put("B25035e1", "Median Year Built");
//        replacements.put("B25036e3", "Built 2014 or Later");
//        replacements.put("B25036e4", "Built 2010 to 2013");
//        replacements.put("B25036e5", "Built 2000 to 2009");
//        replacements.put("B25036e6", "Built 1990 to 1999");
//        replacements.put("B25036e7", "Built 1980 to 1989");
//        replacements.put("B25036e8", "Built 1970 to 1979");
//        replacements.put("B25036e9", "Built 1960 to 1969");
//        replacements.put("B25036e10", "Built 1950 to 1959");
//        replacements.put("B25036e11", "Built 1940 to 1949");
//        replacements.put("B25036e12", "Built 1939 or Earlier");
//        
//        
       
        
       
       

        try (CSVReader reader = new CSVReader(new FileReader(inputFilePath));
             CSVWriter writer = new CSVWriter(new FileWriter(outputFilePath))) {

            List<String[]> lines = reader.readAll();

           
            for (String[] line : lines) {
                for (int i = 0; i < line.length; i++) {
                    if (replacements.containsKey(line[i])) {
                        line[i] = replacements.get(line[i]);
                    }
                }
            }

           
            writer.writeAll(lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
