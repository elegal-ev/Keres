package de.elegal;

import au.com.bytecode.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public abstract class CSVHandle {
    private String inputFilePath;

    CSVHandle(final String csvFilePath) {
        this.inputFilePath = csvFilePath;
    }

    public void setInputFilePath(final String path) {
        this.inputFilePath = path;

    }

    public List<String[]> read() throws IOException {
        CSVReader csvReader = new CSVReader(new FileReader(inputFilePath));
        List<String[]> rows = csvReader.readAll();
        csvReader.close();
        return rows;
    }
}
