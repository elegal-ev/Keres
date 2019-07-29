package de.elegal;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TupleTest {
    private static Tuple<Integer, Integer> intTuple;
    private static Tuple<Double, Double> doubleTuple;
    private static Tuple<Integer, String> stringTuple;

    @Before
    public void setUp() throws Exception {
        intTuple = new Tuple<>(3, 3);
        doubleTuple = new Tuple<>(1.0, Math.PI);
        stringTuple = new Tuple<>(42, "Hallo");
    }

    @Test
    public void getSecond() {
        int sndInt = intTuple.getSecond();
        double sndDouble = doubleTuple.getSecond();
        String sndStrInt = stringTuple.getSecond();

        Assert.assertEquals(3, sndInt);
        Assert.assertEquals(Math.PI, sndDouble, 0.0001);
        Assert.assertEquals("Hallo", sndStrInt);
    }

    @Test
    public void getFirst() {
        int fstInt = intTuple.getFirst();
        double fstDouble = doubleTuple.getFirst();
        int fstStrInt = stringTuple.getFirst();

        Assert.assertEquals(3, fstInt);
        Assert.assertEquals(1.0, fstDouble, 0.00001);
        Assert.assertEquals(42, fstStrInt);
    }

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