package co.edu.umanizales.transport.util;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class CSVUtil {
    private static final String CSV_DIR = "data/";

    static {
        try {
            Files.createDirectories(Paths.get(CSV_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToCSV(String filename, List<String> headers, List<List<String>> rows) {
        try (FileWriter writer = new FileWriter(CSV_DIR + filename)) {
            writer.append(String.join(",", headers)).append("\n");
            for (List<String> row : rows) {
                writer.append(String.join(",", row)).append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Map<String, String>> readFromCSV(String filename) {
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_DIR + filename))) {
            String headerLine = reader.readLine();
            if (headerLine == null) {
                return data;
            }
            String[] headers = headerLine.split(",");
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length && i < values.length; i++) {
                    row.put(headers[i], values[i]);
                }
                data.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean fileExists(String filename) {
        return Files.exists(Paths.get(CSV_DIR + filename));
    }
}
