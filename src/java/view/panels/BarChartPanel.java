package view.panels;

import controllers.ControllerRequestObject;
import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.UnexpectedException;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListModel;
import model.beans.SubPiece;
import model.util.PieceUtilities;
import org.jfree.data.category.CategoryDataset;
import view.charts.BarChart;
import view.dialogs.ChooserFile;
import view.dialogs.Detail;
import view.dialogs.EventViewer;
import view.dialogs.SaveData;
import view.frames.MainFrame;
import view.helpers.HandlerPanels;
import view.notifications.Notifications;
import view.splits.Navigator;
import view.tables.TablePagination;
import view.util.DropUtilities;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class BarChartPanel extends FactoryPanel {

    private Notifications notifications = null;
    private BarChart barChart = null;
    private SubPiece subPiece = null;
    private JPanel panelFooter = null;
    private LoadingPanel loadingPanel = null;
    private JList list = null;
    private JPanel panelPagination = null;
    private int totalResult = 0;
    private int numBars = 0;
    private CategoryDataset dataset = null;
    private Thread execute = null;
    private int limit1 = 1;
    private int limit2 = 25;
    private HeaderPagination hp = null;
    private JPanel panelBarChart = null;
    private boolean finishPagination = false;

    //==========================================================================
    public BarChartPanel(SubPiece subPiece) {
        notifications = new Notifications();
        this.subPiece = subPiece;
        loadingPanel = new LoadingPanel();
        list = SwingUtilities.getListBarChart();
        onLoad();
    } // end BarChartPanel    

    //==========================================================================
    private void onLoad() {

        setname();
        subPiece.setLimit("1,25");
        setLayout(new BorderLayout());
        setDropTarget(new DropTarget(list, this));
        setLoadingPanel();
        execute();

    } // end onLoad

    //==========================================================================
    private void execute() {

        remover();
        setLoadingPanel();

        execute = new Thread(new Runnable() {

            public void run() {

                try {

                    requestTotalResult();
                    requestDataset();
                    //setNumBars(); xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
                    createChart();
                    createPanelFooter();
                    createPanelPagination();
                    panelBarChart = barChart.getPanel();
                    remover();
                    add(panelBarChart, BorderLayout.CENTER);
                    add(panelFooter, BorderLayout.SOUTH);
                    add(panelPagination, BorderLayout.NORTH);
                    updateUI();

                } catch (Exception e) {
                    notifications.error("error creating panel", e);
                }
            }
        });
        execute.start();
        execute.setName("BarChartPanel.execute");
    }

    //==========================================================================
    private void remover() {
        try {
            if (panelPagination != null) {
                remove(panelPagination);
            }

            if (panelFooter != null) {
                remove(panelFooter);
            }

            if (barChart != null) {
                remove(panelBarChart);
            }
        } catch (Exception e) {
            notifications.error("error removing panels", e);
        } finally {
            removeAll();
        }
    }

    //==========================================================================
    private void setNumBars() {
        numBars = dataset.getColumnKeys().size();
    }

    //==========================================================================
    private void createChart() {
        barChart = new BarChart(subPiece, dataset, "", "", "", list);
    }

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

        try {

            selected = DropUtilities.getStringFromDrop(dtde);

            if (!DropUtilities.checkDrop(selected)) {
                return;
            }

            //create a new subPiece
            newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);

            //setView
            newSubPiece.setView(selected);

            //set protocols or hosts
            setOption(newSubPiece);

            //set drillDown
            newSubPiece.setDrillDown(getName());

            //adding new tab
            FactoryPanel fp = (FactoryPanel) new HandlerPanels().getComponent(newSubPiece);
            fp.setName(selected);
            Navigator.getInstance().addTabInSubNavigator(fp);

        } catch (Exception e) {
            notifications.error("error in drop", e);
        }

    } // end drop

    //==========================================================================
    private void setOption(SubPiece newSubPiece) {

        String view = null;

        try {

            view = subPiece.getView();


            if (view.equalsIgnoreCase("Network Protocols")) {
                newSubPiece.setNetworkProtocols(getDataList());
                newSubPiece.setTypeProtocol(view);
            } else if (view.equalsIgnoreCase("IP Protocols")) {
                newSubPiece.setIPProtocols(getDataList());
                newSubPiece.setTypeProtocol(view);
            } else if (view.equalsIgnoreCase("TCP Protocols")) {
                newSubPiece.setTCPProtocols(getDataList());
                newSubPiece.setTypeProtocol(view);
            } else if (view.equalsIgnoreCase("UDP Protocols")) {
                newSubPiece.setUDPProtocols(getDataList());
                newSubPiece.setTypeProtocol(view);
            } else if (view.equalsIgnoreCase("IP Talkers Bytes")) {
                newSubPiece.setIpAddress(getDataList());
            } else if (view.equalsIgnoreCase("IP Sources Bytes")) {
                newSubPiece.setIpAddress(getDataList());
            } else if (view.equalsIgnoreCase("IP Destinations Bytes")) {
                newSubPiece.setIpAddress(getDataList());
            } else if (view.equalsIgnoreCase("Websites")) {
                newSubPiece.setWebsites(getDataList());
            } else if (view.equalsIgnoreCase("Web Server Hosts")) {
                newSubPiece.setWebServerHosts(getDataList());
            } else if (view.equalsIgnoreCase("Type of Service")) {
                newSubPiece.setTypeService(getDataList());
            }

        } catch (Exception e) {
            notifications.error("error setting ip or protocols", e);
        }

    } // end setProtocolsIps

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
    private void createPanelPagination() {

        try {

            hp = new HeaderPagination();
            panelPagination = hp.getHeaderBarChart();

            if (finishPagination) {
                limit2 = limit1 - 1 + 25;
                finishPagination = false;
            }

            if (totalResult <= 25) {
                hp.enableAllComponents(false);
            }

            if (totalResult > 24) {
                hp.enableNextButton(true);
            }

            if (totalResult <= 1) {
                hp.enableAllComponents(false);
            }

            if (limit1 <= 1) {
                limit1 = 1;
                limit2 = 25;
                hp.enableBackButton(false);
            }

            if (limit2 > totalResult) {
                limit2 = totalResult;
                finishPagination = true;
                hp.enableNextButton(false);
            }


            hp.setLabelText(limit1 + " to " + limit2 + " of " + totalResult);

            //next
            hp.addActionListenerNextButton(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    limit1 += 25;
                    limit2 += 25;
                    subPiece.setLimit(limit1 + "," + limit2);
                    execute();
                }
            });

            //back
            hp.addActionListenerBackButton(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    limit1 -= 25;
                    limit2 -= 25;
                    subPiece.setLimit(limit1 + "," + limit2);
                    execute();
                }
            });

        } catch (Exception e) {
            notifications.error("error creating header", e);
        }

    } // end createPanelPagination    

    //==========================================================================
    private void createPanelFooter() {

        Footer footer = null;

        try {

            footer = new Footer(subPiece);
            panelFooter = footer.getFooterBarChart(list);

            //excel
            footer.addActionListenerExportExcel(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportExcel();
                }
            });

            //pdf
            footer.addActionListenerPdfButton(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportChartToPDF();
                }
            });

            //detail
            footer.addActionListenerDetailsButton(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Detail detail = new Detail(MainFrame.getInstance(), true, subPiece);
                    detail.setVisible(true);
                }
            });

            //table
            footer.addActionListenerTableButton(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    openTable();
                }
            });

        } catch (Exception e) {
            notifications.error("error creating footer", e);
        }

    } // end createFooter
    //==========================================================================

    private void openTable() {
        new Thread(new Runnable() {

            public void run() {

                javax.swing.SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        try {
                            SubPiece newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
                            newSubPiece.isTable(true);
                            Navigator.getInstance().addTabInSubNavigator(new TablePagination(newSubPiece));
                        } catch (Exception e) {
                            notifications.error("error opening table", e);
                        }
                    }
                });
            }
        }).start();
    } // end openTable

    //==========================================================================
    private void exportChartToPDF() {

        new Thread(new Runnable() {

            public void run() {

                try {
                    String path = new ChooserFile().getPath();
                    if (path != null) {
                        SubPiece newSubPiece = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
                        newSubPiece.isTable(true);
                        SaveData saveData = new SaveData(MainFrame.getInstance(), false);
                        saveData.setVisible(true);
                        saveData.exportChartToPDF(barChart.createChart(dataset), newSubPiece, path);
                    }
                } catch (Exception e) {
                    notifications.error("error creating pdf", e);
                }
            }
        }).start();

    }

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

    } // end exportExcel

    //==========================================================================
    private void requestTotalResult() {
        try {
            SubPiece nsp = new PieceUtilities().createSubPieceFromSubPiece(subPiece);
            nsp.setView(subPiece.getView() + " Rows");
            totalResult = (Integer) new ControllerRequestObject().getObject(nsp);
        } catch (Exception e) {
            notifications.error("error requesting result", e);
        }
    }

    //==========================================================================
    private void requestDataset() {

        try {
            dataset = (CategoryDataset) new ControllerRequestObject().getObject(subPiece);
        } catch (Exception e) {
            notifications.error("error", e);
        }

    }

    //==========================================================================
    private void setLoadingPanel() {

        new Thread(new Runnable() {

            public void run() {
                javax.swing.SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        add(loadingPanel, BorderLayout.CENTER);
                        updateUI();
                    }
                });
            }
        }).start();

    } // end setLoadingPanel

    //==========================================================================
    @Override
    public void setname() {
        setName(subPiece.getView());
    }

    //==========================================================================
    @Override
    public SubPiece getSubPiece() {
        return this.subPiece;
    }

    //==========================================================================
    @Override
    public void destroy() {
        if (execute != null) {
            EventViewer.getInstance().appendInfoTextConsole("stoping execute thread");
            execute.interrupt();
        }

    }
//==========================================================================

    @Override
    public Class getclass() {
        return getClass();
    }
    //==========================================================================

    @Override
    protected void finalize() throws Throwable {
        try {
            destroy();
            notifications = null;
            barChart = null;
            panelFooter = null;
            loadingPanel = null;
            list = null;
            panelPagination = null;
            totalResult = 0;
            numBars = 0;
            dataset = null;
            execute = null;
            limit1 = 1;
            limit2 = 25;
            hp = null;
            panelBarChart = null;
        } catch (Exception e) {
            notifications.error("error", e);
        } finally {
            super.finalize();
        }
    } // end finalize
} // end class

