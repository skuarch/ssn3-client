/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view.charts;

import javax.swing.JPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.event.AxisChangeEvent;
import org.jfree.data.DomainOrder;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.xy.XYDataset;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class LineChartTest {
    
    public LineChartTest() {
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
     * Test of createChart method, of class LineChart.
     */
    @Test
    public void testCreateChart() {
        System.out.println("createChart");
        XYDataset dataset = new XYDataset() {

            public DomainOrder getDomainOrder() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public int getItemCount(int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Number getX(int i, int i1) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public double getXValue(int i, int i1) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Number getY(int i, int i1) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public double getYValue(int i, int i1) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public int getSeriesCount() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public Comparable getSeriesKey(int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public int indexOf(Comparable cmprbl) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void addChangeListener(DatasetChangeListener dl) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void removeChangeListener(DatasetChangeListener dl) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public DatasetGroup getGroup() {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            public void setGroup(DatasetGroup dg) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        LineChart instance = null;
        JFreeChart expResult = null;
        JFreeChart result = instance.createChart(dataset);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of axisChanged method, of class LineChart.
     */
    @Test
    public void testAxisChanged() {
        System.out.println("axisChanged");
        AxisChangeEvent ace = null;
        LineChart instance = null;
        instance.axisChanged(ace);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getJPanel method, of class LineChart.
     */
    @Test
    public void testGetJPanel() {
        System.out.println("getJPanel");
        LineChart instance = null;
        JPanel expResult = null;
        JPanel result = instance.getJPanel();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
}
