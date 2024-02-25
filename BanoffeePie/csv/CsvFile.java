package BanoffeePie.csv;

import java.io.*;
import java.util.List;

public interface CsvFile {
    File getFile();
    List<String[]> getData();

    void addData(String[] line);

    void writeData();
}

