package view.charts;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author skuarch
 */
public class MyChartPanel extends ChartPanel implements MouseListener {

    private BarChart barChart = null;

    //==========================================================================
    public MyChartPanel(BarChart barChart) {
        super(barChart.getChart());
        this.barChart = barChart;
    } // end MyChartPanel

    //==========================================================================
    @Override
    public void mouseReleased(MouseEvent e) {
        super.mouseReleased(e);
        barChart.changeLabels();
    }
} // end class

