/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.ssn;

import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.sql.DataSource;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class MainTest {
    
    public MainTest() {
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
     * Test of getDataSource method, of class Main.
     */
    @Test
    public void testGetDataSource() {
        System.out.println("getDataSource");
        DataSource expResult = null;
        DataSource result = Main.getDataSource();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTopic method, of class Main.
     */
    @Test
    public void testGetTopic() {
        System.out.println("getTopic");
        Topic expResult = null;
        Topic result = Main.getTopic();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTopicConnectionFactory method, of class Main.
     */
    @Test
    public void testGetTopicConnectionFactory() {
        System.out.println("getTopicConnectionFactory");
        TopicConnectionFactory expResult = null;
        TopicConnectionFactory result = Main.getTopicConnectionFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        Main.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
