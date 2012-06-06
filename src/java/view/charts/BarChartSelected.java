package view.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import javax.swing.JList;
import javax.swing.JPanel;
import model.beans.SubPiece;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.ui.ApplicationFrame;
import view.util.NumberFormatLabel;

/**
 *
 * @author skuarch
 */
public class BarChartSelected extends ApplicationFrame {

    private SubPiece subPiece = null;
    private JFreeChart chart = null;
    private CategoryPlot plot = null;
    private NumberAxis yAxis = null;
    private double upperBound;
    private CategoryDataset dataset = null;
    private String title = null;
    private String titlex = null;
    private String titley = null;
    private JList list = null;

    //==========================================================================
    public BarChartSelected(SubPiece subPiece, CategoryDataset dataset, String title, String titlex, String titley, JList list) {

        super(title);
        this.subPiece = subPiece;
        this.dataset = dataset;
        this.title = title;
        this.titlex = titlex;
        this.titley = titley;
        this.list = list;

        onLoad();

    } // end barChart

    //==========================================================================
    private void onLoad() {
        JPanel chartPanel = getPanel();
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

    } // end onLoad

    //==========================================================================
    /**
     * Creates a sample chart.
     *
     * @param dataset the dataset.
     *
     * @return The chart.
     */
    public JFreeChart createChart(CategoryDataset dataset) {

        // create the chart...
        chart = ChartFactory.createBarChart(
                title, // chart title
                titlex, // domain axis label
                titley, // range axis label
                dataset, // data
                PlotOrientation.VERTICAL, // orientation
                false, // include legend
                true, // tooltips?
                false // URLs?
                );

        // get a reference to the plot for further customisation...
        plot = (CategoryPlot) chart.getPlot();
        plot.setDomainGridlinesVisible(true);
        plot.setNoDataMessage("no data");

        // set vertical labels
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(
                Math.PI / 6.0));

        // set the range axis to display integers only...
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        MyBarRenderer renderer = new MyBarRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setBaseOutlineStroke(new BasicStroke(1.5f));
        plot.setRenderer(renderer);

        ChartUtilities.applyCurrentTheme(chart);

        // set up gradient paints for series...
        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue,
                0.0f, 0.0f, new Color(0, 0, 64));
        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green,
                0.0f, 0.0f, new Color(0, 64, 0));
        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red,
                0.0f, 0.0f, new Color(64, 0, 0));
        renderer.setSeriesPaint(0, gp0);
        renderer.setSeriesPaint(1, gp1);
        renderer.setSeriesPaint(2, gp2);

        changeLabels();

        return chart;

    } // end chart  

    //==========================================================================
    public void changeLabels() {
        //label format
        yAxis = (NumberAxis) plot.getRangeAxis();
        upperBound = yAxis.getRange().getUpperBound();
        yAxis.setNumberFormatOverride(new NumberFormatLabel(upperBound));
    }

    //==========================================================================
    public JFreeChart getChart() {
        return chart;
    }

    //==========================================================================
    /**
     * Creates a panel for the demo (used by SuperDemo.java).
     *
     * @return A panel.
     */
    public JPanel getPanel() {

        //JFreeChart c = createChart(dataset); //descomentar si existe un error
        createChart(dataset);

        // we need to fetch a reference to the renderer
        //CategoryPlot p = (CategoryPlot) c.getPlot(); //descomentar si existe un error y quitar createCharPanel
        MyBarRenderer renderer = (MyBarRenderer) plot.getRenderer();
        MyDemoPanel demoPanel = new MyDemoPanel(renderer, list);
        MyChartPanel chartPanel = new MyChartPanel(this);
        demoPanel.addChart(chart);
        chartPanel.addChartMouseListener(demoPanel);
        demoPanel.add(chartPanel);

        return demoPanel;

    } // end getPanel  
    
} 