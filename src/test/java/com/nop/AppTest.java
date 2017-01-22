package com.nop;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public static void main(String[] args) {
        String vopOrder="[48492890141, 49266066953]";
        int idx = vopOrder.indexOf("[");
        int endIdx = vopOrder.lastIndexOf("]");
        System.out.println(vopOrder.substring(idx + 1, endIdx));

    }

    public void getVopOrder() {
    }
}
