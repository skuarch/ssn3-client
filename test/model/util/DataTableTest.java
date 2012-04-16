/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.util;

import model.beans.SubPiece;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class DataTableTest {

    public DataTableTest() {
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
     * Test of getDataTable method, of class DataTable.
     */
    @Test
    public void testGetDataTable() throws Exception {

        try {
            System.out.println("getDataTable");
            SubPiece subPiece = null;
            DataTable instance = new DataTable();
            Object expResult = null;
            Object result = instance.getDataTable(subPiece);
            assertEquals(expResult, result);
        } catch (Exception e) {
            e.printStackTrace();
        }


        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
