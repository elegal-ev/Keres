package de.elegal;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

// Fristen und Zinsberechnung
public class Calculator {
    private Calculator() {
    }

    // 5 % p.a. nach dt. Methode
    public static double verzugszins(double betrag, int tage) throws NumberFormatException {
        if (betrag < 0) throw new NumberFormatException("Negativer Betrag!");
        if (tage < 0) throw new NumberFormatException("Negative Tagesdifferenz!");
        return (betrag * 5 * tage) / (100 * 360);
    }

    // ob eine mahnung wirksam ist.
    // TODO add 196, 197, 651g, 548, 407ff., 864 BGB
    public static boolean isFristende(String due) throws ParseException {
        LocalDate today = LocalDate.now();
        return isFristende(today.toString(), due);
    }

    public static boolean isFristende(String today, String due) throws ParseException {
        LocalDate todayDate = LocalDate.parse(today);
        LocalDate dueDate = LocalDate.parse(due);

        // Ultimoregelung, 199 I 1. HS BGB
        LocalDate lastDay = LocalDate.parse(dueDate.getYear() + "-12-31");
        long timeDelta = ChronoUnit.DAYS.between(todayDate, lastDay);  // getting days between due and today's date

        if (timeDelta <= -1095) return false; // after 3 years
        else return dueDate.isBefore(todayDate) || dueDate.equals(todayDate);
    }
}
