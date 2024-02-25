package BanoffeePie.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

class CsvFileHelper {
    public static List<String> readFile(File f) {
        List<String> result = new ArrayList<>();

        try (FileReader fr = new FileReader(f); BufferedReader br = new BufferedReader(fr)) {

            for (String line = br.readLine(); line != null && !line.isEmpty(); line = br.readLine())
                result.add(line);

            return result;
        } catch (Exception err) {
            System.err.println("Cannot open the csv file: " + err);
            err.printStackTrace();
            return new ArrayList<>();
        }
    }
}