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
public class ConnectivityTest {
    
    public ConnectivityTest() {
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
     * Test of requestConnectivity method, of class Connectivity.
     */
    @Test
    public void testRequestConnectivity() throws Exception {
        System.out.println("requestConnectivity");
        String host = "sss";
        Connectivity instance = new Connectivity();
        boolean expResult = false;
        boolean result = instance.requestConnectivity(host);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
