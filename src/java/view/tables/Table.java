package view.tables;

import controllers.ControllerDataTable;
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
import view.dialogs.ChooserFile;
import view.dialogs.Detail;
import view.dialogs.SaveData;
import view.frames.MainFrame;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.panels.Footer;
import view.panels.LoadingPanel;

/**
 *
 * @author skuarch
 */
public class Table extends FactoryPanel {

    private SubPiece subPiece = null;
    private LoadingPanel loadingPanel = null;
    private Notifications notifications = null;
    private DefaultTableModel model = null;
    private JScrollPane scrollPane = null;
    private JPanel footerPanel = null;
    private Thread execute = null;
    private ArrayList arrayList = null;
    private JTable table = null;
    private JPanel panelFooter = null;

    //==========================================================================
    public Table(SubPiece subPiece) {
        this.subPiece = subPiece;
        arrayList = new ArrayList();
        loadingPanel = new LoadingPanel();
        notifications = new Notifications();
        scrollPane = new JScrollPane();
        footerPanel = new Footer(subPiece).getFooterTable();
        table = new JTable();
        onLoad();
    } // end Table

    //==========================================================================
    private void onLoad() {

        setname();
        checkTable();
        setLayout(new BorderLayout());

        new Thread(new Runnable() {

            public void run() {
                setLoadingPanel();
                execute();
            }
        }).start();

    } // end onLoad

    //==========================================================================
    private void checkTable() {
        if (!subPiece.getView().contains("Table")) {
            subPiece.setView(subPiece.getView() + " Table");
            subPiece.isTable(true);
        }
    }

    //==========================================================================
    private void execute() {

        remover();
        setLoadingPanel();

        execute = new Thread(new Runnable() {

            public void run() {

                try {

                    requestData();
                    createTable();
                    sortTable();
                    createPanelFooter();
                    remover();
                    add(scrollPane, BorderLayout.CENTER);
                    add(panelFooter, BorderLayout.SOUTH);

                } catch (Exception e) {
                    notifications.error("error creating table", e);
                } finally {
                    updateUI();
                }
            }
        });
        execute.setName("execute");
        execute.start();

    } // end execute

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
    private void remover() {
        try {

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

    } // end createTable

    //==========================================================================
    private void requestData() {

        try {

            arrayList = (ArrayList) new ControllerDataTable().getData(subPiece);

        } catch (Exception e) {
            notifications.error("error creating table", e);
        }

    } // end requestData

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
    private void setLoadingPanel() {

        add(loadingPanel, BorderLayout.CENTER);
        updateUI();

    } // end setLoadingPanel    

    //==========================================================================
    @Override
    public void setname() {
        setName(subPiece.getView() + " Table");
    } // end setname

    //==========================================================================
    @Override
    public SubPiece getSubPiece() {
        return this.subPiece;
    } //end getSubPiece

    //==========================================================================
    @Override
    public void destroy() {
        if (execute != null) {
            execute.interrupt();
        }
    } // end destroy

    //==========================================================================
    @Override
    public Class getclass() {
        return this.getClass();
    } // end getClass

    //==========================================================================
    @Override
    protected void finalize() throws Throwable {

        try {
            destroy();
            loadingPanel = null;
            notifications = null;
            table = null;
            model = null;
            scrollPane = null;
        } catch (Exception e) {
            e.printStackTrace();
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
