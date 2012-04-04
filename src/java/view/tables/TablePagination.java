package view.tables;

import controllers.ControllerDataTable;
import controllers.ControllerRequestObject;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.beans.SubPiece;
import model.util.PieceUtilities;
import view.dialogs.ChooserFile;
import view.dialogs.Detail;
import view.dialogs.EventViewer;
import view.dialogs.SaveData;
import view.frames.MainFrame;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.panels.Footer;
import view.panels.HeaderPagination;
import view.panels.LoadingPanel;

/**
 *
 * @author skuarch
 */
public class TablePagination extends FactoryPanel {

    private Notifications notifications = null;
    private SubPiece subPiece = null;
    private Thread execute = null;
    private JPanel loadingPanel = null;
    private JPanel panelFooter = null;
    private JPanel panelPagination = null;
    private int limit1 = 1;
    private int limit2 = 25;
    private JScrollPane scrollPane = null;
    private int totalResult = 0;
    private ArrayList arrayList = null;
    private JTable table = null;
    private DefaultTableModel model = null;
    private boolean finishPagination = false;

    //==========================================================================
    public TablePagination(SubPiece subPiece) {
        
        notifications = new Notifications();
        this.subPiece = subPiece;
        loadingPanel = new LoadingPanel();
        table = new JTable();

        onLoad();
    } // end TablePagination

    //==========================================================================
    private void onLoad() {
        setname();
        checkTable();
        setLimits();
        setLayout(new BorderLayout());
        execute();
    }

    //==========================================================================
    private void checkTable() {
        if (!subPiece.getView().contains("Table")) {
            subPiece.setView(subPiece.getView() + " Table");
            subPiece.isTable(true);
        }
    }

    //==========================================================================
    private void setLimits() {
        if (subPiece.getLimit().equalsIgnoreCase("not applicable")) {
            subPiece.setLimit(limit1 + "," + limit2);
        } else {
            limit1 = Integer.parseInt(subPiece.getLimit().split(",")[0]);
            limit2 = Integer.parseInt(subPiece.getLimit().split(",")[1]);
        }
    }

    //==========================================================================
    private void execute() {

        remover();
        setLoadingPanel();

        execute = new Thread(new Runnable() {

            public void run() {

                try {

                    requestTotalResult();
                    requestData();
                    createTable();
                    sortTable();
                    createPanelFooter();
                    createPanelPagination();
                    remover();
                    add(scrollPane, BorderLayout.CENTER);
                    add(panelFooter, BorderLayout.SOUTH);
                    add(panelPagination, BorderLayout.NORTH);
                    updateUI();

                } catch (Exception e) {
                    notifications.error("error creating panel", e);
                }

            }
        });
        execute.start();
        execute.setName("TablePagination.execute");

    }

    //==========================================================================
    private void createPanelPagination() {

        HeaderPagination hp = null;

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

    //=========================================================================    
    private void sortTable() {

        Thread t = new Thread(new Runnable() {

            public void run() {
                try {

                    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                    table.setRowSorter(sorter);

                } catch (Exception e) {
                    notifications.error("error sorting table", e);
                }
            }
        });

        t.start();

    } // end sortTable

    //==========================================================================
    private void remover() {
        try {
            if (panelPagination != null) {
                remove(panelPagination);
            }

            if (panelFooter != null) {
                remove(panelFooter);
            }

            if (scrollPane != null) {
                remove(scrollPane);
            }

        } catch (Exception e) {
            notifications.error("error removing panels", e);
        } finally {
            removeAll();
        }
    }

    //==========================================================================
    private void createPanelFooter() {

        Footer footer = null;

        try {

            footer = new Footer(subPiece);
            panelFooter = footer.getFooterTable();

            //excel
            footer.addActionListenerExportExcel(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    exportExcel();
                }
            });


            //detail
            footer.addActionListenerDetailsButton(new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    Detail detail = new Detail(MainFrame.getInstance(), true, subPiece);
                    detail.setVisible(true);
                }
            });

        } catch (Exception e) {
            notifications.error("error creating footer", e);
        }

    } // end createFooter

    //==========================================================================
    private void exportExcel() {

        new Thread(new Runnable() {

            public void run() {
                String path = new ChooserFile().getPath();
                if (path != null) {
                    SaveData saveData = new SaveData(MainFrame.getInstance(), false);
                    saveData.setVisible(true);
                    saveData.exportTableToExcel(subPiece, path);
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
    private void createTable() {

        String[] columnNames = null;
        Object[][] data = null;

        try {            

            columnNames = (String[]) arrayList.get(0);
            data = (Object[][]) arrayList.get(1);

            model = new DefaultTableModel(data, columnNames) {

                @Override
                public Class<?> getColumnClass(int column) {
                    Class<?> c = null;
                    try {
                        c = getValueAt(0, column).getClass();
                    } catch (Exception e) {
                        notifications.error("error creating table", e);
                    }
                    return c;
                }
            }; //  end getColumnClass

            //setting the new model to table
            table.setModel(model);
            scrollPane = new JScrollPane(table);

        } catch (Exception e) {
            notifications.error("error creating table", e);
        }

    }

    //==========================================================================
    private void requestData() {

        try {

            arrayList = (ArrayList) new ControllerDataTable().getData(subPiece);

        } catch (Exception e) {
            notifications.error("error creating table", e);
        }

    } // end requestData

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
        setName(subPiece.getView() + " Table");
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
            execute = null;
            loadingPanel = null;
            panelFooter = null;
            panelPagination = null;
            limit1 = 1;
            limit2 = 25;
            scrollPane = null;
            totalResult = 0;
            arrayList = null;
            table = null;
            model = null;
        } catch (Exception e) {
            notifications.error("error in finalize", e);
        } finally {
            super.finalize();
        }

    } // end finalize

    //==========================================================================
    @Override
    public Object getData() {
        return arrayList;
    } // end getData
    
} // end class

