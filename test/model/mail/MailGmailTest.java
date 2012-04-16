/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.mail;

import org.junit.*;

/**
 *
 * @author skuarch
 */
public class MailGmailTest {
    
    public MailGmailTest() {
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
     * Test of run method, of class MailGmail.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        MailGmail instance = new MailGmail(null, null, null, null, null, null);
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
