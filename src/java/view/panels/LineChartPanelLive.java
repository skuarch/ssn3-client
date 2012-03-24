package view.panels;

import controllers.ControllerConfiguration;
import controllers.ControllerDataset;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import model.beans.SubPiece;
import model.util.PieceUtilities;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.RegularTimePeriod;
import view.charts.LineChartLive;
import view.dialogs.ChooserFile;
import view.dialogs.EventViewer;
import view.dialogs.SaveData;
import view.frames.MainFrame;
import view.helpers.HandlerPanels;
import view.notifications.Notifications;
import view.splits.Navigator;
import view.tables.Table;
import view.util.DropUtilities;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class LineChartPanelLive extends FactoryPanel {

    private SubPiece subPiece = null;
    private JList list = null;
    private JPanel chartPanel = null;
    private LoadingPanel loadingPanel = null;
    private JPanel panelFooter = null;
    private JScrollPane scrollList = null;
    private JScrollPane scrollInformation = null;
    private JPanel panelInformation = null;
    private JButton tableButton = null;
    private JButton exportButton = null;
    private LineChartLive lineChartLive = null;
    private Thread threadLive = null;
    private boolean shutdown = true;
    private int sleep = 0;
    private Notifications notifications = null;

    //==========================================================================
    public LineChartPanelLive(SubPiece subPiece) throws Exception {

        notifications = new Notifications();
        this.subPiece = subPiece;
        this.list = new JList();
        this.loadingPanel = new LoadingPanel();
        this.lineChartLive = new LineChartLive(subPiece, subPiece.getView(), "", "", list);
        this.sleep = Integer.parseInt(new ControllerConfiguration().getInitialConfiguration().getSecondsLive()) * 1000;

        onLoad();
    } // end BandwithOverTimeBits

    //==========================================================================
    private void onLoad() {

        setname();
        setLayout(new BorderLayout());
        setLoadingPanel();

        Thread t = new Thread(new Runnable() {

            //==================================================================
            public void run() {
                setFooter();
                changeChart();
                createDropTarget();
                live();
            }
        });
        t.start();
        t.setName("onLoad");

    } // end onLoad

    //==========================================================================
    private void live() {

        threadLive = new Thread(new Runnable() {

            public void run() {
                try {

                    while (shutdown) {

                        Thread t = new Thread(new Runnable() {

                            public void run() {

                                ArrayList arrayList = null;
                                RegularTimePeriod rtp = null;
                                double num = 0;
                                String tmp = null;

                                try {

                                    arrayList = requestData();

                                    if (arrayList != null) {
                                        rtp = (RegularTimePeriod) arrayList.get(0);
                                        tmp = arrayList.get(1).toString();

                                        if (rtp != null && tmp != null) {
                                            num = Double.parseDouble(tmp);
                                            lineChartLive.addSeries(rtp, num);
                                        }

                                        arrayList = null;
                                        rtp = null;
                                    }
                                } catch (Exception e) {
                                    notifications.error("error", e);
                                } finally {
                                    arrayList = null;
                                    rtp = null;
                                    num = 0;
                                    tmp = null;
                                }
                            }
                        });

                        t.start();
                        Thread.sleep(sleep);

                    } //  end while

                } catch (Exception e) {
                    notifications.error("error in chart live", e);
                } finally {
                    threadLive = null;
                }
            }
        });

        threadLive.start();

    }

    //==========================================================================
    public void destroy() {
        try {

            shutdown = false;
            EventViewer.getInstance().appendInfoTextConsole("stoping live thread");

        } catch (Exception e) {
            notifications.error("error stoping live thread", e);
        } finally {
            subPiece = null;
            list = null;
            chartPanel = null;
            loadingPanel = null;
            panelFooter = null;
            scrollList = null;
            scrollInformation = null;
            panelInformation = null;
            tableButton = null;
            exportButton = null;
            threadLive = null;
        }

    } // end destroy

    //==========================================================================
    private void changeChart() {

        removeChartPanel();
        setLoadingPanel();

        Thread t = new Thread(new Runnable() {

            //==================================================================
            public void run() {
                lineChartLive.addSeries(new Millisecond(), 0);
                chartPanel = lineChartLive.getJPanel();
                remove(loadingPanel);
                add(chartPanel, BorderLayout.CENTER);
                updateUI();
            }
        });
        t.start();
        t.setName("changeChart");

    } // end changeChart

    //==========================================================================    
    /**
     * create a panel with labels and JList.
     */
    private void setFooter() {

        try {

            //the footer
            panelFooter = new JPanel();
            panelFooter.setBackground(Color.LIGHT_GRAY);
            panelFooter.setLayout(new BorderLayout());

            // the list
            scrollList = new JScrollPane(list);
            scrollList.setPreferredSize(new Dimension(getMaximumSize().height, 100));

            //information
            panelInformation = new JPanel();

            //buttons
            tableButton = new SwingUtilities().getTableButton();
            tableButton.addActionListener(new ActionListener() {

                //==============================================================
                @Override
                public void actionPerformed(ActionEvent ae) {
                    //create a new tab in subnavigator
                    Navigator.getInstance().addTabInSubNavigator(new Table(subPiece));
                }
            });

            //adding button to information panel
            panelInformation.add(tableButton);

            //export button
            exportButton = new SwingUtilities().getExportExcelButton();
            exportButton.addActionListener(new ActionListener() {

                //==============================================================
                @Override
                public void actionPerformed(ActionEvent ae) {
                    String path = new ChooserFile().getPath();
                    if (path != null) {
                        SubPiece newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
                        newSubPiece.isTable(true);
                        SaveData saveData = new SaveData(MainFrame.getInstance(), false);
                        saveData.setVisible(true);
                        saveData.exportTableToExcel(newSubPiece, path);
                    }
                }
            });

            //adding button to information panel
            panelInformation.add(exportButton);

            //labels
            panelInformation.add(new JLabel("collector: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getCollector()));
            panelInformation.add(new JLabel("protocols: "));
            //panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.));
            panelInformation.add(new JLabel("ips: "));
            //panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getHosts()));
            panelInformation.add(new JLabel("drill down: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getDrillDown()));
            panelInformation.add(new JLabel("dates: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getDates()));
            panelInformation.add(new JLabel("subnet: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getSubnet() + "/" + subPiece.getNetmask()));
            panelInformation.add(new JLabel("network protocols: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getNetworkProtocols()));
            panelInformation.add(new JLabel("ip protocols: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getIPProtocols()));
            panelInformation.add(new JLabel("tcp protocols: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getTCPProtocols()));
            panelInformation.add(new JLabel("udp protocols: "));
            panelInformation.add(new SwingUtilities().getWhiteLabel(subPiece.getUDPProtocols()));

            //creating scroll
            scrollInformation = new JScrollPane(panelInformation);
            scrollInformation.setPreferredSize(new Dimension(getMaximumSize().height, 55));

            //adding everything
            panelFooter.add(scrollInformation, BorderLayout.PAGE_START);
            panelFooter.add(scrollList, BorderLayout.PAGE_END);

            //adding footer to main panel
            add(panelFooter, BorderLayout.PAGE_END);
            updateUI();

        } catch (Exception e) {
            notifications.error("error creating footer panel", e);
        }

    } // end setFooter

    //==========================================================================
    /**
     * remove the chartPanel from BorderLayout.CENTER.
     */
    private void removeChartPanel() {
        try {

            if (chartPanel == null) {
                return;
            }

            remove(chartPanel);
            updateUI();

        } catch (Exception e) {
            notifications.error("error removing panel", e);
        } finally {
            chartPanel = null;
        }
    } // end removeChartPanel()

    //==========================================================================
    private ArrayList requestData() {

        ArrayList arrayList = null;

        try {
            arrayList = new ControllerDataset().getSeries(subPiece);
        } catch (Exception e) {
            notifications.error("error requesting new data", e);
        }

        return arrayList;
    }

    //==========================================================================
    /**
     * put loadingPanel into BorderLayout.CENTER.
     */
    private void setLoadingPanel() {

        try {

            add(loadingPanel, BorderLayout.CENTER);
            updateUI();

        } catch (Exception e) {
            notifications.error("error creating panel", e);
        }

    } // end setLoadingPanel

    //==========================================================================
    /**
     * create list drop target
     */
    private void createDropTarget() {

        if (list == null) {
            return;
        }

        new DropTarget(list, this);
    } // end createDropTarget

    //==========================================================================
    /**
     * check if drop is correct and create a new tab.
     *
     * @param dtde DropTargetDropEvent
     */
    @Override
    public void drop(DropTargetDropEvent dtde) {

        if (dtde == null) {
            notifications.error("error creating drop target", new NullPointerException());
            return;
        }

        if (list.getModel().getSize() < 1) {
            notifications.error("please select something from chart above", new Exception());
            return;
        }

        String selected = null;
        SubPiece newSubPiece = null;

        try {

            selected = DropUtilities.getStringFromDrop(dtde);

            if (!DropUtilities.checkDrop(selected)) {
                return;
            }

            //create a new subPiece
            newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);

            //setView
            newSubPiece.setView(selected);

            //set protocols
            newSubPiece.setDates(getDataList());

            //set drillDown
            newSubPiece.setDrillDown(getName());

            //adding new tab
            FactoryPanel fp = (FactoryPanel) new HandlerPanels().getComponent(newSubPiece);
            fp.setName(selected);
            Navigator.getInstance().addTabInSubNavigator(fp);

            //send message to console
            EventViewer.getInstance().appendInfoTextConsole("drill down to " + selected);

        } catch (Exception e) {
            notifications.error("error in drag and drop", e);
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
    @Override
    public void setname() {
        setName(subPiece.getView());
    } // end setname

    //==========================================================================
    @Override
    public SubPiece getSubPiece() {
        return this.subPiece;
    } // end getSubPiece

    //==========================================================================
    @Override
    public Class getclass() {
        return getClass();
    } // end getclass

    //==========================================================================
    @Override
    protected void finalize() throws Throwable {

        try {
            destroy();
        } catch (Exception e) {
            notifications.error("finalize", e);
        } finally {
            super.finalize();
        }
        
    }
} // end class

