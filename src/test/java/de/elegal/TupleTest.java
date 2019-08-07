package de.elegal;

import de.elegal.Utils.Tuple;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * The testing class for the generic tuple
 *
 * @author Valerius Mattfeld, Lars Quentin
 */
public class TupleTest {
    /**
     * Just an Tuple of Integers
     */
    private static Tuple<Integer, Integer> intTuple;

    /**
     * Just an Tuple of Doubles
     */
    private static Tuple<Double, Double> doubleTuple;

    /**
     * Just an Tuple of Strings
     */
    private static Tuple<Integer, String> stringTuple;

    /**
     * Setup-Method. Gets called before every tests and generates 3 tuples
     */
    @Before
    public void setUp() {
        intTuple = new Tuple<>(3, 3);
        doubleTuple = new Tuple<>(1.0, Math.PI);
        stringTuple = new Tuple<>(42, "Hallo");
    }

    /**
     * Checking if accessing the second element works
     */
    @Test
    public void getSecond() {
        int sndInt = intTuple.getSecond();
        double sndDouble = doubleTuple.getSecond();
        String sndStrInt = stringTuple.getSecond();

        Assert.assertEquals(3, sndInt);
        Assert.assertEquals(Math.PI, sndDouble, 0.0001);
        Assert.assertEquals("Hallo", sndStrInt);
    }

    /**
     * Checking if accessing the first element works
     */
    @Test
    public void getFirst() {
        int fstInt = intTuple.getFirst();
        double fstDouble = doubleTuple.getFirst();
        int fstStrInt = stringTuple.getFirst();

        Assert.assertEquals(3, fstInt);
        Assert.assertEquals(1.0, fstDouble, 0.00001);
        Assert.assertEquals(42, fstStrInt);
    }

    /**
     * Checking if setting the first argument works
     */
    @Test
    public void setFirst() {
        intTuple.setFirst(1);
        doubleTuple.setFirst(2.0);
        stringTuple.setFirst(100);

        int fstInt = intTuple.getFirst();
        double fstDouble = doubleTuple.getFirst();
        int fstStrInt = stringTuple.getFirst();

        Assert.assertEquals(1, fstInt);
        Assert.assertEquals(2.0, fstDouble, 0.00001);
        Assert.assertEquals(100, fstStrInt);
    }

    /**
     * Checking if setting the second argument works
     */
    @Test
    public void setSecond() {
        intTuple.setSecond(1);
        doubleTuple.setSecond(3.0);
        stringTuple.setSecond("Welt");

        int sndInt = intTuple.getSecond();
        double sndDouble = doubleTuple.getSecond();
        String sndStrInt = stringTuple.getSecond();

        Assert.assertEquals(1, sndInt);
        Assert.assertEquals(3.0, sndDouble, 0.0001);
        Assert.assertEquals("Welt", sndStrInt);
    }
}