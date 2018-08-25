package me.janeldq.algorithms.sort;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;


public class StringSortTest {
    private String[] testArr;
    private static String[] forCompare;

    @BeforeClass
    public static void setup () {
        forCompare = new String[]{
                "apple", "you", "test", "floor", "rain", "notebook",
                "bottle", "water", "pencil", "eraser", "book", "knife",
                "cubic", "bag", "sun", "mouse", "keyboard", "issue",
                "code", "program", "basket", "cookie", "paper", "zoo"
        };
        Arrays.sort(forCompare);
    }

    @Before
    public void beforeTest() {
        testArr = new String[]{
                "apple", "you", "test", "floor", "rain", "notebook",
                "bottle", "water", "pencil", "eraser", "book", "knife",
                "cubic", "bag", "sun", "mouse", "keyboard", "issue",
                "code", "program", "basket", "cookie", "paper", "zoo"
        };
    }

    @Test
    public void testMSD() {
        MSDStringSort.sort(testArr);
        Assert.assertArrayEquals("Test MSD", forCompare, testArr);
    }

    @Test
    public void test3Way() {
        Quick3WayStringSort.sort(testArr);
        Assert.assertArrayEquals("Test 3-way", forCompare, testArr);
    }

    @Test
    public void testLSD() {
        String[] sortedArr = new String[] {
                "abc", "cea", "abe", "etd", "ehc", "ade", "etg", "gak", "eqk"
        };
        Arrays.sort(sortedArr);
        String[] fixWidthArr = new String[] {
                "abc", "cea", "abe", "etd", "ehc", "ade", "etg", "gak", "eqk"
        };
        LSDStringSort.sort(fixWidthArr, 3);
        Assert.assertArrayEquals("Test LSD", sortedArr, fixWidthArr);
    }
}