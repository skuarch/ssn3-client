package view.panels;

import controllers.ControllerDataset;
import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.UnexpectedException;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import model.beans.SubPiece;
import model.util.PieceUtilities;
import org.jfree.data.xy.XYDataset;
import view.charts.LineChart;
import view.dialogs.ChooserFile;
import view.dialogs.Detail;
import view.dialogs.EventViewer;
import view.dialogs.SaveData;
import view.frames.MainFrame;
import view.helpers.HandlerPanels;
import view.notifications.Notifications;
import view.splits.Navigator;
import view.tables.Table;
import view.util.DropUtilities;

/**
 *
 * @author skuarch
 */
public class LineChartPanel extends FactoryPanel {

    private SubPiece subPiece = null;
    private Notifications notifications = null;
    private JList list = null;
    private LoadingPanel loadingPanel = null;
    private JPanel chartPanel = null;
    private Footer footer = null;
    private Thread threadExecute = null;
    private XYDataset dataset = null;
    private LineChart lineChart = null;

    //==========================================================================
    public LineChartPanel(SubPiece subPiece) {
        this.subPiece = subPiece;
        this.notifications = new Notifications();
        this.list = new JList();
        this.loadingPanel = new LoadingPanel();
        footer = new Footer(subPiece);
        onLoad();
    } // end LineChartPanel

    //==========================================================================
    //@Override
    public Object getData() {
        return lineChart.createChart(dataset);
    }

    //==========================================================================
    private void onLoad() {

        setname();
        //setDrop
        setDropTarget(new DropTarget(list, this));

        Thread t = new Thread(new Runnable() {

            public void run() {

                try {

                    SwingUtilities.invokeAndWait(new Runnable() {

                        public void run() {
                            setLayout(new BorderLayout());
                            add(loadingPanel, BorderLayout.CENTER);
                            addListeners();
                            execute();
                        }
                    });

                } catch (Exception e) {
                    notifications.error("error loading chart", e);
                }
            }
        });
        t.start();
        t.setName("onLoad");

    } // end onLoad

    //==========================================================================
    private void execute() {

        try {

            threadExecute = new Thread(new Runnable() {

                @Override
                public void run() {

                    try {

                        dataset = (XYDataset) requestDataset();
                        lineChart = new LineChart(subPiece, dataset, subPiece.getView(), "", "", list);
                        chartPanel = lineChart.getJPanel();
                        remove(loadingPanel);
                        add(chartPanel, BorderLayout.CENTER);
                        add(footer.getFooterLineChart(list), BorderLayout.SOUTH);
                        
                    } catch (Exception e) {
                        notifications.error("error creating chart", e);
                    } finally {
                        updateUI();
                    }

                }
            });
            threadExecute.start();
            threadExecute.setName("LineChartPanel.execute");

        } catch (Exception e) {
            notifications.error("error creation chart", e);
        }

    } // end execute

    //==========================================================================
    private XYDataset requestDataset() {

        dataset = null;

        try {
            dataset = new ControllerDataset().xyDataset(subPiece);
        } catch (Exception e) {
            notifications.error("error requesting new data", e);
        }

        return dataset;
    } // end requestDataSet

    //==========================================================================
    @Override
    public void drop(DropTargetDropEvent dtde) {

        if (dtde == null) {
            notifications.error("dtde is null", new NullPointerException("dtde is null"));
            return;
        }

        if (list.getModel().getSize() < 1) {
            notifications.error("please select something from chart above", new UnexpectedException("please select something from chart above"));
            return;
        }

        String selected = null;
        SubPiece newSubPiece = null;
        String[] e2eSelected = null;
        SubPiece spE2E = null;        
        
        try {

            selected = DropUtilities.getStringFromDrop(dtde);

            if (!DropUtilities.checkDrop(selected)) {
                return;
            }

            //create a new subPiece
            newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);

            //setView
            newSubPiece.setView(selected);

            //set dates
            newSubPiece.setDates(getDataList());

            //set drillDown
            newSubPiece.setDrillDown(getName());

            //in end to end maybe the e2eSelected has a lot of data and each data is a new tab
            if (selected.equalsIgnoreCase("End to End")) {                    
                
                e2eSelected = subPiece.getIpAddress().split(",");
                for (String string : e2eSelected) {

                    newSubPiece.setE2E(string);
                    spE2E = new PieceUtilities().createSubPieceFromSubPiece(newSubPiece);
                    spE2E.setE2E(string);
                    FactoryPanel fp = (FactoryPanel) new HandlerPanels().getComponent(spE2E);
                    fp.setName(selected);
                    Navigator.getInstance().addTabInSubNavigator(fp);

                }

            } else {

                //adding new tab
                FactoryPanel fp = (FactoryPanel) new HandlerPanels().getComponent(newSubPiece);
                fp.setName(selected);
                Navigator.getInstance().addTabInSubNavigator(fp);

            }

        } catch (Exception e) {
            notifications.error("error in drop", e);
        }

    } // end drop

    //==========================================================================
    /**
     * return the items in list separated by commas.
     *
     * @return String
     */
    private String getDataList() {

        StringBuffer data = null;
        int size = 0;
        ListModel model = null;

        try {

            size = list.getModel().getSize();

            if (size < 1) {
                return null;
            }

            model = list.getModel();
            data = new StringBuffer();

            for (int i = 0; i < size; i++) {
                data.append(model.getElementAt(i).toString());
                data.append(",");
            }

        } catch (Exception e) {
            notifications.error("error getting data from list", e);
        }

        return data.toString();
    } // end getDataList

    //==========================================================================
    private void addListeners() {

        //pdf
        footer.addActionListenerPdfButton(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exportChartToPDF();
            }
        });

        //excel
        footer.addActionListenerExportExcel(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                exportExcel();
            }
        });


        //table
        footer.addActionListenerTableButton(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                openTable();
            }
        });

        //detail
        footer.addActionListenerDetailsButton(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                detail();
            }
        });


    } // addListeners

    //==========================================================================
    private void detail() {
        Detail detail = new Detail(MainFrame.getInstance(), true, subPiece);
        detail.setVisible(true);
    }

    //==========================================================================
    private void exportChartToPDF() {
        new Thread(new Runnable() {

            public void run() {

                try {
                    String path = new ChooserFile().getPath();
                    if (path != null) {
                        SaveData saveData = new SaveData(MainFrame.getInstance(), false);
                        saveData.setVisible(true);
                        saveData.exportChartToPDF(lineChart.createChart(dataset), subPiece, path);
                    }
                } catch (Exception e) {
                    notifications.error("error creating pdf", e);
                }
            }
        }).start();
    } // exportChartToPDF

    //==========================================================================
    private void exportExcel() {

        new Thread(new Runnable() {

            public void run() {
                String path = new ChooserFile().getPath();
                if (path != null) {
                    SubPiece newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
                    newSubPiece.isTable(true);
                    SaveData saveData = new SaveData(MainFrame.getInstance(), false);
                    saveData.setVisible(true);
                    saveData.exportTableToExcel(newSubPiece, path);
                }
            }
        }).start();

    } // end exportTableToExcel

    //==========================================================================
    private void openTable() {
        new Thread(new Runnable() {

            public void run() {

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        try {
                            SubPiece newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
                            newSubPiece.isTable(true);
                            Navigator.getInstance().addTabInSubNavigator(new Table(newSubPiece));
                        } catch (Exception e) {
                            notifications.error("error opening table", e);
                        }
                    }
                });
            }
        }).start();
    } // end openTable

    //==========================================================================
    @Override
    public void setname() {
        setName(subPiece.getView());
    } // end setName

    //==========================================================================
    @Override
    public SubPiece getSubPiece() {
        return this.subPiece;
    } // end getSubPiece

    //==========================================================================
    @Override
    public Class getclass() {
        return getClass();
    } // end getClass

    //==========================================================================
    @Override
    public void destroy() {
        if (threadExecute != null) {
            EventViewer.getInstance().appendInfoTextConsole("stoping execute thread");
            threadExecute.interrupt();
        }
    } // end destroy

    //==========================================================================
    @Override
    protected void finalize() throws Throwable {

        try {
            destroy();
            notifications = null;
            list = null;
            loadingPanel = null;
            chartPanel = null;
            footer = null;
        } catch (Exception e) {
            throw e;
        } finally {
            super.finalize();
        }

    } // end finalize
} // end class

