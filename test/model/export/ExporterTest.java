/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model.export;

import java.awt.Component;
import model.beans.SubPiece;
import org.jfree.chart.JFreeChart;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author skuarch
 */
public class ExporterTest {
    
    public ExporterTest() {
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
     * Test of exportTableToExcel method, of class Exporter.
     */
    @Test
    public void testExportTableToExcel() throws Exception {
        System.out.println("exportTableToExcel");
        SubPiece subPiece = new SubPiece();
        String path = "";
        Exporter instance = new Exporter();
        instance.exportTableToExcel(null, path);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of exportChartToPDF method, of class Exporter.
     */
    @Test
    public void testExportChartToPDF() throws Exception {
        System.out.println("exportChartToPDF");
        JFreeChart chart = null;
        SubPiece subPiece = new SubPiece();
        String path = "";
        Exporter instance = new Exporter();
        instance.exportChartToPDF(chart, subPiece, path);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of createPDFReport method, of class Exporter.
     */
    @Test
    public void testCreatePDFReport() throws Exception {
        System.out.println("createPDFReport");
        Component[] subs = null;
        String path = "";
        Exporter instance = new Exporter();
        instance.createPDFReport(subs, path);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
