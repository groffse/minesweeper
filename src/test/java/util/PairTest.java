package util;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PairTest {

    @Test
    public void testThatCanRetrivePairValues() {
        Integer val_1 = 10;
        Integer val_2 = 20;
        Pair<Integer,Integer> pair = new Pair<>();
        pair.first  = val_1;
        pair.second = val_2;

        assertEquals(val_1, pair.first);
        assertEquals(val_2,pair.second);
    }

    /*Gonna do dis later on, so why not just check that it works now. Should work by construction, but I still do it*/
    @Test
    public void testThatCanInstantiatePairWithArrayList() {
        ArrayList<String> arr = new ArrayList<>();
        arr.add("Test");
        Integer i = 30;

        Pair<ArrayList<String>, Integer> pair = new Pair<>();
        pair.first = arr;
        pair.second = i;

        assertEquals("Test",pair.first.get(0));
        assertEquals(i,pair.second);
    }

}