package de.elegal;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.Assert.*;

public class ClientTest {
    private static Client client;

    @Before
    public void setUp() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            client = new Client(
                    Objects.requireNonNull(classLoader.getResource("csv/mandate.csv")).getPath()
            );
        } catch (IOException e) {
            e.printStackTrace();
            fail("Cannot import mandate.csv!");
        }
    }

    @Test
    public void getHeader() {
        String[] header = {"ID", "NAME", "VORNAME", "ANREDE", "FIRMA", "STRASSE", "NUMMER", "STADT",
                "PLZ", "EMAIL", "FAX", "LEISTUNG", "BETRAG", "DATUM"};
        assertArrayEquals(header, client.getHeader());
    }

    @Test
    public void getRows() {
        int len = 4; // incl. header row.

        assertEquals(len, client.getRows().size());

        String[] row0 = {"1", "Mustermann", "Max", "Herr", "", "Maxstr.", "8a", "Dortmund", "58239", "max@muster.de",
                "12931-241", "Kauf von Nudelsuppen (1231)", "324.11", "2018-09-03"};
        String[] row1 = {"2", "Schumacher", "Nadine", "Frau", "", "Doenermeile", "13", "Berlin", "30303", "gone@girl.com",
                "454445-87", "Autovermietung - Tesla XL", "25.71", "2014-03-03"};
        String[] row2 = {"3", "Rechtsstudent", "R", "Herr", "Kanzlei Recht und Ordnung", "Backstreet", "25", "Potsdam",
                "13371", "norm@kontrolle.de", "013/923", "Gyros Spezial", "11.99", "2019-11-23"};

        assertArrayEquals(row0, client.getRows().get(1));
        assertArrayEquals(row1, client.getRows().get(2));
        assertArrayEquals(row2, client.getRows().get(3));
    }

    @Test
    public void getSubstitutions() {
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("ID", "1");
        expectedMap.put("NAME", "Mustermann");
        expectedMap.put("VORNAME", "Max");
        expectedMap.put("ANREDE", "Herr");
        expectedMap.put("FIRMA", "");
        expectedMap.put("STRASSE", "Maxstr.");
        expectedMap.put("NUMMER", "8a");
        expectedMap.put("STADT", "Dortmund");
        expectedMap.put("PLZ", "58239");
        expectedMap.put("EMAIL", "max@muster.de");
        expectedMap.put("FAX", "12931-241");
        expectedMap.put("LEISTUNG", "Kauf von Nudelsuppen (1231)");
        expectedMap.put("BETRAG", "324.11");
        expectedMap.put("DATUM", "2018-09-03");

        assertSame();
    }

    @Test
    public void getClients() {
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("ID", "1");
        expectedMap.put("NAME", "Mustermann");
        expectedMap.put("VORNAME", "Max");
        expectedMap.put("ANREDE", "Herr");
        expectedMap.put("FIRMA", "");
        expectedMap.put("STRASSE", "Maxstr.");
        expectedMap.put("NUMMER", "8a");
        expectedMap.put("STADT", "Dortmund");
        expectedMap.put("PLZ", "58239");
        expectedMap.put("EMAIL", "max@muster.de");
        expectedMap.put("FAX", "12931-241");
        expectedMap.put("LEISTUNG", "Kauf von Nudelsuppen (1231)");
        expectedMap.put("BETRAG", "324.11");
        expectedMap.put("DATUM", "2018-09-03");

        assertSame();
    }
}