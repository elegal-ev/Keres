package de.elegal;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Client extends CSVHandle implements TagStrings {
    private final List<String[]> rows;
    private final String[] header;

    public Client(String csvFilePath) throws IOException {
        super(csvFilePath);
        this.rows = read();
        this.header = this.rows.get(0);
    }

    public static void main(String[] args) {
        Client client = null;
        try {
            client = new Client("/home/valerius/code/Keres/src/test/resources/csv/mandate.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert client != null;
        for (String[] row :
                client.getRows()) {
            for (String s : row) {
                System.out.print(s + " ");
            }
            System.out.println();
        }
    }

    public List<String[]> getRows() {
        return rows;
    }

    public String[] getHeader() {
        return header;
    }

    @Override
    public Map<String, String> getSubstitutions() {
        return null;
    }
}
