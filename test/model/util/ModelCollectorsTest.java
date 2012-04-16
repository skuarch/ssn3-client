/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.util;

import model.beans.Collectors;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author skuarch
 */
public class ModelCollectorsTest {
    
    public ModelCollectorsTest() {
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
     * Test of getCollectors method, of class ModelCollectors.
     */
    @Test
    public void testGetCollectors() {
        System.out.println("getCollectors");
        ModelCollectors instance = new ModelCollectors();
        Collectors[] expResult = null;
        Collectors[] result = instance.getCollectors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getActivesCollectors method, of class ModelCollectors.
     */
    @Test
    public void testGetActivesCollectors() {
        System.out.println("getActivesCollectors");
        ModelCollectors instance = new ModelCollectors();
        Collectors[] expResult = null;
        Collectors[] result = instance.getActivesCollectors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getActivesCollectorsArray method, of class ModelCollectors.
     */
    @Test
    public void testGetActivesCollectorsArray() {
        System.out.println("getActivesCollectorsArray");
        ModelCollectors instance = new ModelCollectors();
        String[] expResult = null;
        String[] result = instance.getActivesCollectorsArray();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
