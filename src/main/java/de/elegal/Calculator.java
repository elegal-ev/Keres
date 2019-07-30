package de.elegal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;

// Fristen und Zinsberechnung
public class Calculator {
    private Calculator() {
    }

    // 5 % p.a.
    public static double verzugszins(double betrag, int tage) {
        double factor = ((double) tage) / 365; // TODO schaltjahr
        return betrag + (betrag * 1.05) * factor;
    }

    // ob eine mahnung wirksam ist.
    public static boolean isFristende(String faelligkeitsdatum, String datum) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Period period = Period.between(LocalDate.parse(datum), LocalDate.parse(faelligkeitsdatum));
        int timeDelta = period.getDays();

        return timeDelta >= 900; // fixme hard!
    }
}
