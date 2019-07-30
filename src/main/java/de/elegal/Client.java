package de.elegal;

import java.io.IOException;
import java.util.*;

public class Client extends CSVHandle implements TagStrings {
    private final Stack<String[]> rows;
    private final String[] header;

    public Client(String csvFilePath) throws IOException {
        super(csvFilePath);
        List<String[]> tmpList = read();
        Collections.reverse(tmpList);
        this.rows = new Stack<>();
        this.rows.addAll(tmpList);
        this.header = this.rows.pop();
    }

    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client("/home/valerius/code/Keres/src/test/resources/csv/mandate.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert client != null;
        for (Map<String, String> m :
                client.getSubstitutions()) {
            System.out.println(m);
        }
    }

    public List<String[]> getRows() {
        return new ArrayList<>(rows);
    }

    public String[] getHeader() {
        return header;
    }

    @Override
    public Map<String, String>[] getSubstitutions() {
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
