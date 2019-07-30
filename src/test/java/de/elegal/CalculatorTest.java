package de.elegal;

import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.*;

public class CalculatorTest {
    @Test
    public void verzugszins() {
        // FIXME adding exception handling for negative input
        // 5% p.a.
        double x = Calculator.verzugszins(900, 200);
        assertTrue(900 + x > 900);
    }

    @Test
    public void isFristende() {
        String vj = "2013-01-01";
        String faellig = "2019-01-01";
        String nichtFaellig = "2020-01-01";
        String today = "2019-08-01";

        try {
            assertFalse(Calculator.isFristende(vj, today));
            assertFalse(Calculator.isFristende(nichtFaellig, today));
            assertTrue(Calculator.isFristende(faellig, today));
        } catch (ParseException e) {
            e.printStackTrace();
            fail("test failed for fristende");
        }
    }
}