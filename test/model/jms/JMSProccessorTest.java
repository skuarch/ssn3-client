/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.jms;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class JMSProccessorTest {
    
    public JMSProccessorTest() {
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
     * Test of sendReceive method, of class JMSProccessor.
     */
    @Test
    public void testSendReceive() throws Exception {
        System.out.println("sendReceive");
        String select = "a";
        String sendTo = "q";
        String tagTo = "q";
        int time = 11;
        Object object = new Object();
        JMSProccessor instance = new JMSProccessor();
        Object expResult = null;
        Object result = instance.sendReceive(select, sendTo, tagTo, time, object);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
