/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.charts;

import org.jfree.chart.JFreeChart;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class DemoPanelTest {
    
    public DemoPanelTest() {
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
     * Test of addChart method, of class DemoPanel.
     */
    @Test
    public void testAddChart() {
        System.out.println("addChart");
        JFreeChart chart = new JFreeChart(null, null);
        DemoPanel instance = null;
        instance.addChart(chart);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getCharts method, of class DemoPanel.
     */
    @Test
    public void testGetCharts() {
        System.out.println("getCharts");
        DemoPanel instance = null;
        JFreeChart[] expResult = null;
        JFreeChart[] result = instance.getCharts();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
