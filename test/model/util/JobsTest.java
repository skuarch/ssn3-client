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
public class JobsTest {
    
    public JobsTest() {
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
     * Test of getJobs method, of class Jobs.
     */
    @Test
    public void testGetJobs() {
        System.out.println("getJobs");
        String collector = "";
        Jobs instance = new Jobs();
        String[] expResult = null;
        String[] result = instance.getJobs(collector);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
