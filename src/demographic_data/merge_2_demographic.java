package demographic_data;
import java.io.*;
import java.util.*;

public class merge_2_demographic {

    public static void main(String[] args) {
        String firstCSVFile = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/not_alterd/demographicData.csv";
        String secondCSVFile = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/not_alterd/anushka.csv";
        String outputCSVFile = "/home/shatam-system-i2/akash_created_demographic_project/resi_data/not_alterd/output.csv";

        String[] matchingColumns = {"TRACTCE", "BLKGRPCE"};

        try {
            List<String[]> firstRecords = readCSV(firstCSVFile);
            List<String[]> secondRecords = readCSV(secondCSVFile);

            List<String[]> mergedRecords = mergeCSVRecords(firstRecords, secondRecords, matchingColumns);

            writeCSV(outputCSVFile, mergedRecords);
            System.out.println("Merged data saved to " + outputCSVFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String[]> readCSV(String filename) throws IOException {
        List<String[]> records = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        String line;

        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            records.add(values);
        }
        reader.close();
        return records;
    }

    private static List<String[]> mergeCSVRecords(List<String[]> firstRecords, List<String[]> secondRecords, String[] matchingColumns) {
        List<String[]> mergedRecords = new ArrayList<>(firstRecords);

        for (String[] secondRecord : secondRecords) {
            boolean foundMatch = false;

            for (String[] firstRecord : firstRecords) {
                boolean matches = true;

                for (String column : matchingColumns) {
                    int columnIndex = getColumnIndex(column, firstRecord);
                    int secondColumnIndex = getColumnIndex(column, secondRecord);

                    if (columnIndex != -1 && secondColumnIndex != -1) {
                        String firstValue = firstRecord[columnIndex].replaceAll("^\\s*0+(?!$)", "");
                        String secondValue = secondRecord[secondColumnIndex].replaceAll("^\\s*0+(?!$)", "");

                        if (!firstValue.equals(secondValue)) {
                            matches = false;
                            break;
                        }
                    } else {
                        matches = false;
                        break;
                    }
                }

                if (matches) {
                    foundMatch = true;
                    mergeData(firstRecord, secondRecord);
                    break;
                }
            }

            if (!foundMatch) {
                mergedRecords.add(secondRecord);
            }
        }
        return mergedRecords;
    }

    private static void mergeData(String[] firstRecord, String[] secondRecord) {
        // Merge the data from secondRecord to firstRecord as needed
        // For example, assuming both records have the same structure, you can update the values of firstRecord
        // based on the values in secondRecord.
        // This could involve appending or updating specific columns as per your requirements.
        // Here's a simple example assuming the records have the same structure:
        for (int i = 0; i < firstRecord.length; i++) {
            if (i < secondRecord.length && !secondRecord[i].isEmpty()) {
                // Assuming the second record's non-empty values should update the first record's corresponding empty values
                if (firstRecord[i].isEmpty()) {
                    firstRecord[i] = secondRecord[i];
                }
                // If you have a different merging strategy, adjust this part accordingly
            }
        }
    }

    private static int getColumnIndex(String column, String[] record) {
        for (int i = 0; i < record.length; i++) {
            if (record[i].replaceAll("^\\s*0+(?!$)", "").equals(column.replaceAll("^\\s*0+(?!$)", ""))) {
                return i;
            }
        }
        return -1;
    }

    private static void writeCSV(String filename, List<String[]> records) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));

        for (String[] record : records) {
            writer.write(String.join(",", record));
            writer.newLine();
        }
        writer.close();
    }
}
