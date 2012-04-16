package view.charts;

import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.CategoryDataset;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class BarChartTest {
    
    public BarChartTest() {
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
     * Test of createChart method, of class BarChart.
     */
    @Test
    public void testCreateChart() {
        System.out.println("createChart");
        CategoryDataset dataset = null;
        BarChart instance = new BarChart(null, dataset, null, null, null, null);
        JFreeChart expResult = null;
        JFreeChart result = instance.createChart(dataset);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getPanel method, of class BarChart.
     */
    @Test
    public void testGetPanel() {
        System.out.println("getPanel");
        BarChart instance = null;
        JPanel expResult = null;
        JPanel result = instance.getPanel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
