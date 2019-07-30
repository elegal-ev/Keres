package de.elegal;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class CalculatorTest {
    @Test
    public void verzugszins() {
        // FIXME adding exception handling for negative input
        // 5% p.a.
        double zins = 0;
        int days = 200;
        double schuld = 900;
        try {
            zins = Calculator.verzugszins(schuld, days);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            fail("Negative inputs provided!");
        }
        assertTrue(schuld + zins > schuld);
    }

    @Test
    public void isFristende() {
        String faellig = "2019-01-01";
        String faellig2 = "2019-03-11";
        String faellig3 = "2019-07-29";
        String vj = "2013-01-01";
        String nichtFaellig = "2020-01-01";
        String nichtFaellig2 = "2019-08-02";
        String today = "2019-08-01";

        try {
            assertFalse(Calculator.isFristende(today, vj));
            assertFalse(Calculator.isFristende(today, nichtFaellig));
            assertFalse(Calculator.isFristende(today, nichtFaellig2));
            assertTrue(Calculator.isFristende(today, faellig));
            assertTrue(Calculator.isFristende(today, faellig2));
            assertTrue(Calculator.isFristende(today, faellig3));
        } catch (ParseException e) {
            e.printStackTrace();
            fail("test failed for fristende");
        }
    }
}