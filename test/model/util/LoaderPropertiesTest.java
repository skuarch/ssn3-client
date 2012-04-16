/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.util;

import java.util.Properties;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class LoaderPropertiesTest {
    
    public LoaderPropertiesTest() {
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
     * Test of getProperties method, of class LoaderProperties.
     */
    @Test
    public void testGetProperties() throws Exception {
        System.out.println("getProperties");
        LoaderProperties instance = new LoaderProperties(null);
        Properties expResult = null;
        Properties result = instance.getProperties();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
