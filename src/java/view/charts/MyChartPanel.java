package view.charts;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author skuarch
 */
public class MyChartPanel extends ChartPanel implements MouseListener {

    private BarChartSelected barChartSelected = null;
    private BarChartHorizontal barChartHorizontal = null;

    //==========================================================================
    public MyChartPanel(BarChartSelected BarChartSelected) {
        super(BarChartSelected.getChart());
        this.barChartSelected = BarChartSelected;
    } // end MyChartPanel

    //==========================================================================
    public MyChartPanel(BarChartHorizontal barChartHorizontal) {
        super(barChartHorizontal.getChart());
        this.barChartHorizontal = barChartHorizontal;
    }

    //==========================================================================
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        
        if(barChartSelected != null){
            barChartSelected.changeLabels();
        }
        
    }
} // end class

