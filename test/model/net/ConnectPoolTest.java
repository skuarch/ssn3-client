/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.net;

import java.sql.ResultSet;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author skuarch
 */
public class ConnectPoolTest {
    
    public ConnectPoolTest() {
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
     * Test of executeQuery method, of class ConnectPool.
     */
    @Test
    public void testExecuteQuery() throws Exception {
        System.out.println("executeQuery");
        String sql = "s";
        ConnectPool instance = new ConnectPool();
        ResultSet expResult = null;
        ResultSet result = instance.executeQuery(sql);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class ConnectPool.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        String sql = "s";
        ConnectPool instance = new ConnectPool();
        instance.update(sql);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getNumRows method, of class ConnectPool.
     */
    @Test
    public void testGetNumRows() throws Exception {
        System.out.println("getNumRows");
        ResultSet resultSet = null;
        ConnectPool instance = new ConnectPool();
        int expResult = 0;
        int result = instance.getNumRows(resultSet);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
