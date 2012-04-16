/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jms;

import javax.jms.*;
import model.ssn.Main;
import static org.junit.Assert.assertEquals;
import org.junit.*;

/**
 *
 * @author skuarch
 */
public class JMSTest {
    
    public JMSTest() {
        new Main();
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
     * Test of getMessageProducer method, of class JMS.
     */
    @Test
    public void testGetMessageProducer() throws Exception{
        System.out.println("getMessageProducer");
        JMS instance = new JMS();
        MessageProducer expResult = null;
        MessageProducer result = instance.getMessageProducer();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getObjectMessage method, of class JMS.
     */
    @Test
    public void testGetObjectMessage() throws Exception{
        System.out.println("getObjectMessage");
        JMS instance = new JMS();
        ObjectMessage expResult = null;
        ObjectMessage result = instance.getObjectMessage();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTopic method, of class JMS.
     */
    @Test
    public void testGetTopic() throws Exception{
        System.out.println("getTopic");
        JMS instance = new JMS();
        Topic expResult = null;
        Topic result = instance.getTopic();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
       // fail("The test case is a prototype.");
    }

    /**
     * Test of getTopicConnection method, of class JMS.
     */
    @Test
    public void testGetTopicConnection() throws Exception{
        System.out.println("getTopicConnection");
        JMS instance = new JMS();
        TopicConnection expResult = null;
        TopicConnection result = instance.getTopicConnection();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTopicConnectionFactory method, of class JMS.
     */
    @Test
    public void testGetTopicConnectionFactory() throws Exception{
        System.out.println("getTopicConnectionFactory");
        JMS instance = new JMS();
        TopicConnectionFactory expResult = null;
        TopicConnectionFactory result = instance.getTopicConnectionFactory();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTopicSession method, of class JMS.
     */
    @Test
    public void testGetTopicSession() throws Exception{
        System.out.println("getTopicSession");
        JMS instance = new JMS();
        TopicSession expResult = null;
        TopicSession result = instance.getTopicSession();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getTopicSubscriber method, of class JMS.
     */
    @Test
    public void testGetTopicSubscriber() throws Exception{
        System.out.println("getTopicSubscriber");
        JMS instance = new JMS();
        TopicSubscriber expResult = null;
        TopicSubscriber result = instance.getTopicSubscriber();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of shutdownConnection method, of class JMS.
     */
    @Test
    public void testShutdownConnection() throws Exception{
        System.out.println("shutdownConnection");
        JMS instance = new JMS();
        instance.shutdownConnection();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
