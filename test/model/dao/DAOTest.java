/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author skuarch
 */
public class DAOTest {
    
    public DAOTest() {
        
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
     * Test of create method, of class DAO.
     */
    @Test
    public void testCreate() throws Exception {
        System.out.println("create");        
        Object object = null;
        DAO instance = new DAO();
        long expResult = 0L;
        long result = instance.create(object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class DAO.
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        Object object = null;
        DAO instance = new DAO();
        instance.update(object);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of get method, of class DAO.
     */
    @Test
    public void testGet() throws Exception {
        System.out.println("get");
        long id = 0L;
        Class clazz = null;
        DAO instance = new DAO();
        Object expResult = null;
        Object result = instance.get(id, clazz);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class DAO.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        Object object = null;
        DAO instance = new DAO();
        instance.delete(object);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAll method, of class DAO.
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        String stringClass = "";
        DAO instance = new DAO();
        List expResult = null;
        List result = instance.getAll(stringClass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class DAO.
     */
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        Class clazz = null;
        String stringToFind = "";
        String field = "";
        DAO instance = new DAO();
        List expResult = null;
        List result = instance.find(clazz, stringToFind, field);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of deleteAll method, of class DAO.
     */
    @Test
    public void testDeleteAll() throws Exception {
        System.out.println("deleteAll");
        String table = "";
        DAO instance = new DAO();
        instance.deleteAll(table);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of hsql method, of class DAO.
     */
    @Test
    public void testHsql() throws Exception {
        System.out.println("hsql");
        String hsql = "";
        DAO instance = new DAO();
        List expResult = null;
        List result = instance.hsql(hsql);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
