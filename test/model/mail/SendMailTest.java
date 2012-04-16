/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mail;

import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class SendMailTest {
    
    public SendMailTest() {
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
     * Test of sendEventViewer method, of class SendMail.
     */
    @Test
    public void testSendEventViewer() throws Exception {
        System.out.println("sendEventViewer");
        String message = "s";
        SendMail instance = new SendMail();
        instance.sendEventViewer(message);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
