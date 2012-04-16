/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dataset;

import model.beans.SubPiece;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.xy.XYDataset;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author skuarch
 */
public class DatasetTest {
    
    public DatasetTest() {
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
     * Test of getCategoryDataset method, of class Dataset.
     */
    @Test
    public void testGetCategoryDataset() throws Exception {
        System.out.println("getCategoryDataset");
        SubPiece subPiece = new SubPiece();
        Dataset instance = new Dataset();
        CategoryDataset expResult = null;
        CategoryDataset result = instance.getCategoryDataset(subPiece);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getXYDataset method, of class Dataset.
     */
    @Test
    public void testGetXYDataset() throws Exception {
        System.out.println("getXYDataset");
        SubPiece subPiece = new SubPiece();
        Dataset instance = new Dataset();
        XYDataset expResult = null;
        XYDataset result = instance.getXYDataset(subPiece);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
