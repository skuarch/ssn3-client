/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.util;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class DateUtilitiesTest {
    
    public DateUtilitiesTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getDate method, of class DateUtilities.
     */
    @Test
    public void testGetDate() throws Exception {
        System.out.println("getDate");
        double timeStamp = 0.0;
        String expResult = "";
        String result = DateUtilities.getDate(timeStamp);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCurrentDate method, of class DateUtilities.
     */
    @Test
    public void testGetCurrentDate() {
        System.out.println("getCurrentDate");
        String expResult = "";
        String result = DateUtilities.getCurrentDate();
        assertEquals(expResult, result);
        /// TODO review the generated test code and remove the default call to fail.
       //fail("The test case is a prototype.");
    }
}
