package com.woact.dolplads.gameRest.testPack;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by dolplads on 13/12/2016.
 */
public class TestClassTest {
    @Test
    public void getString() throws Exception {
        TestClass testClass = new TestClass();
        assertEquals(testClass.getString(), "halla");
    }

}