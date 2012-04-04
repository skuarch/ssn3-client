package controllers;

import java.awt.Component;
import model.beans.SubPiece;
import model.export.Exporter;
import org.jfree.chart.JFreeChart;

/**
 *
 * @author skuarch
 */
public class ControllerExporter {

    //==========================================================================
    public ControllerExporter(){
        
    } // end ControllerExporter

    //==========================================================================
    public void exportTableToExcel(SubPiece subPiece, String path) throws Exception{
        new Exporter().exportTableToExcel(subPiece, path);
    }
    
    //==========================================================================
    public void exportChartToPDF(JFreeChart chart, SubPiece subPiece, String path) throws Exception{    
        new Exporter().exportChartToPDF(chart, subPiece, path);    
    }
    
    //==========================================================================
    public void createPdfReport(Component[] subs,String path) throws Exception{
        new Exporter().createPdfReport(subs,path);
    }
    
    
} // end class
