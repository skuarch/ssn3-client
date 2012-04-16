/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.util;

import model.beans.Configuration;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class InitialConfigurationTest {
    
    public InitialConfigurationTest() {
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
     * Test of getInicialConfiguration method, of class InitialConfiguration.
     */
    @Test
    public void testGetInicialConfiguration() throws Exception {
        System.out.println("getInicialConfiguration");
        InitialConfiguration instance = new InitialConfiguration();
        Configuration expResult = null;
        Configuration result = instance.getInicialConfiguration();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
