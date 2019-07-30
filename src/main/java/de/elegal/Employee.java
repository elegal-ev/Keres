package de.elegal;

import java.io.IOException;
import java.util.*;

// FIXME duplicate ClientCSV
public class Employee extends CSVHandle implements TagStrings {

    private final Stack<String[]> rows;
    private final String[] header;

    public Employee(String csvFilePath) throws IOException { // csv file path
        super(csvFilePath);
        List<String[]> tmpList = read();
        Collections.reverse(tmpList);
        this.rows = new Stack<>();
        this.rows.addAll(tmpList);
        this.header = this.rows.pop(); // sperated header and rows
    }

    public List<String[]> getRows() { // csv rows
        return new ArrayList<>(rows);
    }

    public String[] getHeader() { // header
        return header;
    }

    @Override
    public Map<String, String>[] getSubstitutions() { // returns an array of maps for each client
        Map[] result = new Map[this.rows.size()];
        for (int i = 0; i <= this.rows.size() + 1; i++) {
            String[] currentRow = this.rows.pop();
            Map<String, String> tmpres = new HashMap<>();
            for (int j = 0; j < currentRow.length; j++) {
                tmpres.put(header[j], currentRow[j]);
            }
            result[i] = tmpres;
        }
        return result;
    }
}
