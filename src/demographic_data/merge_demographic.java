package demographic_data;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class merge_demographic {

    
    private static String normalize(String value) {
        return value.replaceAll("^\\s*0+(?!$)", "");
    }

    public static void main(String[] args) {
        String file1Path = "/home/shatam-system-i2/akash_created_demographic_project/dummy/new_file_dmographic/anushka.csv";
        String file2Path = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/not_alterd/demographicData.csv";

        int columnToCompare1 = 0; // Index of the second column (0-indexed)
        int columnToCompare2 = 1; // Index of the third column (0-indexed)

        int columnToAppend1 = 15; // Index of the column to append from file2 (0-indexed)
        int columnToAppend2 = 16; // Index of another column to append from file2 (0-indexed)
        int columnToAppend3 = 17;
        int columnToAppend4 = 18;
        int columnToAppend5 = 19;
        int columnToAppend6 = 20;
        int columnToAppend7 = 21;
     //   int columnToAppend8 = 22;
    //    int columnToAppend9 = 23;
   //     int columnToAppend10 = 24;
   //     int columnToAppend11 = 25;
   //     int columnToAppend12 = 26;
   //     int columnToAppend13 = 27;
   //     int columnToAppend14 = 28;
   //     int columnToAppend15 = 29;
   //     int columnToAppend16 = 30;
   //     int columnToAppend17 = 31;
  //      int columnToAppend18 = 32;
  //      int columnToAppend19 = 33;
  //      int columnToAppend20 = 34;
  //      int columnToAppend21 = 35;
 //       int columnToAppend22 = 36;
 //       int columnToAppend23 = 37;
 //       int columnToAppend24 = 38;
 //       int columnToAppend25 = 39;
 //       int columnToAppend26 = 40;
 //       int columnToAppend27 = 41;
 //       int columnToAppend28 = 42;
 //       int columnToAppend29 = 43;
 //       int columnToAppend30 = 44;
 //       int columnToAppend31 = 45;
 //       int columnToAppend32 = 46;
 //       int columnToAppend33 = 47;
 //       int columnToAppend34 = 48;

        try {
            CSVReader reader1 = new CSVReader(new FileReader(file1Path));
            CSVReader reader2 = new CSVReader(new FileReader(file2Path));
            CSVWriter writer = new CSVWriter(new FileWriter("/home/shatam-system-i2/akash_created_demographic_project/water_data/census_gdb_ca_2020_json/output7.csv"));

            String[] nextLine1;
            String[] nextLine2;

            while ((nextLine1 = reader1.readNext()) != null && (nextLine2 = reader2.readNext()) != null) {
                if (nextLine1.length > columnToCompare2 && nextLine2.length > columnToCompare2) {
                    // Normalize values before comparison
                    String val1 = normalize(nextLine1[columnToCompare1]);
                    String val2 = normalize(nextLine2[columnToCompare1]);

                    if (val1.equals(val2)) {
                        val1 = normalize(nextLine1[columnToCompare2]);
                        val2 = normalize(nextLine2[columnToCompare2]);

                        if (val1.equals(val2)) {
                            String[] newLine = new String[nextLine1.length + 7];
                            Arrays.fill(newLine, ""); // Fill the array with empty strings

                            // Copy contents from file1
                            System.arraycopy(nextLine1, 0, newLine, 0, nextLine1.length);

                            // Append specific columns from file2
                            newLine[nextLine1.length] = nextLine2[columnToAppend1] != null ? nextLine2[columnToAppend1] : "";
                            newLine[nextLine1.length + 1] = nextLine2[columnToAppend2] != null ? nextLine2[columnToAppend2] : "";
                            newLine[nextLine1.length + 2] = nextLine2[columnToAppend3] != null ? nextLine2[columnToAppend3] : "";
                            newLine[nextLine1.length + 3] = nextLine2[columnToAppend4] != null ? nextLine2[columnToAppend4] : "";
                            newLine[nextLine1.length + 4] = nextLine2[columnToAppend5] != null ? nextLine2[columnToAppend5] : "";
                            newLine[nextLine1.length + 5] = nextLine2[columnToAppend6] != null ? nextLine2[columnToAppend6] : "";
                            newLine[nextLine1.length + 6] = nextLine2[columnToAppend7] != null ? nextLine2[columnToAppend7] : "";
               //             newLine[nextLine1.length + 7] = nextLine2[columnToAppend8] != null ? nextLine2[columnToAppend8] : "";
             //               newLine[nextLine1.length + 8] = nextLine2[columnToAppend9] != null ? nextLine2[columnToAppend9] : "";
            //                newLine[nextLine1.length + 9] = nextLine2[columnToAppend10] != null ? nextLine2[columnToAppend10] : "";
             //               newLine[nextLine1.length + 10] = nextLine2[columnToAppend11] != null ? nextLine2[columnToAppend11] : "";
             //               newLine[nextLine1.length + 11] = nextLine2[columnToAppend12] != null ? nextLine2[columnToAppend12] : "";
             //              newLine[nextLine1.length + 12] = nextLine2[columnToAppend13] != null ? nextLine2[columnToAppend13] : "";
            //              newLine[nextLine1.length + 13] = nextLine2[columnToAppend14] != null ? nextLine2[columnToAppend14] : "";
            //                newLine[nextLine1.length + 14] = nextLine2[columnToAppend15] != null ? nextLine2[columnToAppend15] : "";
             //              newLine[nextLine1.length + 15] = nextLine2[columnToAppend16] != null ? nextLine2[columnToAppend16] : "";
                //            newLine[nextLine1.length + 16] = nextLine2[columnToAppend17] != null ? nextLine2[columnToAppend17] : "";
               //         newLine[nextLine1.length + 17] = nextLine2[columnToAppend18] != null ? nextLine2[columnToAppend18] : "";
               //            newLine[nextLine1.length + 18] = nextLine2[columnToAppend19] != null ? nextLine2[columnToAppend19] : "";
                //           newLine[nextLine1.length + 19] = nextLine2[columnToAppend20] != null ? nextLine2[columnToAppend20] : "";
                //           newLine[nextLine1.length + 20] = nextLine2[columnToAppend21] != null ? nextLine2[columnToAppend21] : "";
                //            newLine[nextLine1.length + 21] = nextLine2[columnToAppend22] != null ? nextLine2[columnToAppend22] : "";
                //           newLine[nextLine1.length + 22] = nextLine2[columnToAppend23] != null ? nextLine2[columnToAppend23] : "";
                //           newLine[nextLine1.length + 23] = nextLine2[columnToAppend24] != null ? nextLine2[columnToAppend24] : "";
                //           newLine[nextLine1.length + 24] = nextLine2[columnToAppend25] != null ? nextLine2[columnToAppend25] : "";
                //            newLine[nextLine1.length + 25] = nextLine2[columnToAppend26] != null ? nextLine2[columnToAppend26] : "";
                //            newLine[nextLine1.length + 26] = nextLine2[columnToAppend27] != null ? nextLine2[columnToAppend27] : "";
                //            newLine[nextLine1.length + 27] = nextLine2[columnToAppend28] != null ? nextLine2[columnToAppend28] : "";
                //            newLine[nextLine1.length + 28] = nextLine2[columnToAppend29] != null ? nextLine2[columnToAppend29] : "";
                //            newLine[nextLine1.length + 29] = nextLine2[columnToAppend30] != null ? nextLine2[columnToAppend30] : "";
                //            newLine[nextLine1.length + 30] = nextLine2[columnToAppend31] != null ? nextLine2[columnToAppend31] : "";
                //            newLine[nextLine1.length + 31] = nextLine2[columnToAppend32] != null ? nextLine2[columnToAppend32] : "";
                //           newLine[nextLine1.length + 32] = nextLine2[columnToAppend33] != null ? nextLine2[columnToAppend33] : "";
                //            newLine[nextLine1.length + 33] = nextLine2[columnToAppend34] != null ? nextLine2[columnToAppend34] : "";
                           
                            

                            writer.writeNext(newLine);
                        } else {
                           
                            writer.writeNext(nextLine1);
                        }
                    } else {
                      
                        writer.writeNext(nextLine1);
                    }
                } else {
                    System.out.println("One of the lines does not have the specified columns.");
                }
            }

            reader1.close();
            reader2.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
