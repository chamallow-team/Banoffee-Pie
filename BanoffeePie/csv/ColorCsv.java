package BanoffeePie.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ColorCsv implements CsvFile {
    public static final char SEPARATOR = ',';

    private final File f;
    private List<String[]> data = new ArrayList<>();

    public boolean isEmpty = true;

    public ColorCsv(String path){
        this.f = new File(path);

        if (!f.exists() || !f.isFile())
            throw new IllegalArgumentException("The file does not exist or is not a file");
    }

    public ColorCsv(File f) {
        if (!f.exists() || !f.isFile())
            throw new IllegalArgumentException("The file does not exist or is not a file");

        this.f = f;
    }

    public void init() throws IOException {
        List<String> lines = CsvFileHelper.readFile(this.f);

        if (!lines.isEmpty())
            isEmpty = false;
    }

    public void clearData(){
        this.data = new ArrayList<>();
    }

    @Override
    public File getFile() {
        return this.f;
    }

    @Override
    public List<String[]> getData(){
        String[] lines = CsvFileHelper.readFile(this.f).toArray(new String[0]);

        for (String line : lines)
            this.data.add(line.split(String.valueOf(SEPARATOR)));

        return this.data;
    }

    public void addData(String[] data) {
        this.data.add(data);
    }

    public void addData(int r, int g, int b, int isWhiter) {
        this.data.add(new String[]{String.valueOf(r), String.valueOf(g), String.valueOf(b), String.valueOf(isWhiter)});
    }

    /**
     * Write the new data to the csv file, but don't overwrite the old data in the file
     */
    public void writeNewData(){
        StringBuilder c = new StringBuilder();
        String sep = String.valueOf(SEPARATOR);

        for (String[] datum : data) {
            c.append(String.join(sep, datum)).append("\n");
        }

        final String content = c.toString();

        // create the file if it doesn't exist
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create the csv file: " + e);
                e.printStackTrace();
            }
        }

        try (FileWriter fw = new FileWriter(f, true)) {
            fw.write(content);
        } catch (Exception err) {
            System.err.println("Cannot write to the csv file: " + err);
            err.printStackTrace();
        }
    }

    @Override
    public void writeData() {
        StringBuilder c = new StringBuilder();
        String sep = String.valueOf(SEPARATOR);

        for (int i = 0; i < data.size(); i++) {
            c.append(String.join(sep, data.get(i))).append("\n");
        }

        final String content = c.toString();

        // create the file if it doesn't exist
        if (!f.exists()) {
            try {
                 f.createNewFile();
            } catch (IOException e) {
                System.err.println("Cannot create the csv file: " + e);
                e.printStackTrace();
            }
        }

        try (FileWriter fw = new FileWriter(f)) {
            fw.write(content);
        } catch (Exception err) {
            System.err.println("Cannot write to the csv file: " + err);
            err.printStackTrace();
        }
    }
}
