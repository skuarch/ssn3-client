package view.tables;

import controllers.ControllerDataTable;
import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.beans.SubPiece;
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
    private JTable jtable = null;
    private DefaultTableModel model = null;
    private JScrollPane scrollPane = null;

    //==========================================================================
    public Table(SubPiece subPiece) {
        this.subPiece = subPiece;
        loadingPanel = new LoadingPanel();
        notifications = new Notifications();
        jtable = new JTable();
        scrollPane = new JScrollPane();
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

        Thread t = new Thread(new Runnable() {

            public void run() {


                ArrayList arrayList = null;
                String[] columnNames = null;
                Object[][] data = null;

                try {

                    arrayList = requestData();

                    if (arrayList == null) {
                        notifications.error("the data for table is null", new NullPointerException());
                        return;
                    }

                    columnNames = (String[]) arrayList.get(0);
                    data = (Object[][]) arrayList.get(1);
                    model = new DefaultTableModel(data, columnNames) {

                        @Override
                        public Class<?> getColumnClass(int column) {
                            Class<?> c = null;
                            try {
                                c = getValueAt(0, column).getClass();
                            } catch (Exception e) {
                                notifications.error("error in getColumnClass", e);
                            }
                            return c;
                        }
                    }; //  end getColumnClass

                    //setting the new model to table
                    jtable.setModel(model);

                    remove(loadingPanel);
                    scrollPane.setViewportView(jtable);
                    add(scrollPane, BorderLayout.CENTER);
                    sortTable();
                    add(new Footer(subPiece).getFooterTable(), BorderLayout.SOUTH);
                    updateUI();

                } catch (Exception e) {
                    notifications.error("error creating table", e);
                }
            }
        });
        t.setName("execute");
        t.start();

    } // end execute

    //=========================================================================
    private void sortTable() {

        Thread t = new Thread(new Runnable() {

            public void run() {
                try {

                    TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
                    jtable.setRowSorter(sorter);

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
    private ArrayList requestData() {

        ArrayList arrayList = null;

        try {

            arrayList = (ArrayList) new ControllerDataTable().getData(subPiece);

        } catch (Exception e) {
            notifications.error("error creating table", e);
        }

        return arrayList;

    } // end requestData

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
        loadingPanel = null;
        notifications = null;
        jtable = null;
        model = null;
        scrollPane = null;
    }
//==========================================================================

    @Override
    public Class getclass() {
        return this.getClass();
    }

    //==========================================================================
    @Override
    protected void finalize() throws Throwable {

        try {
            destroy();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.finalize();
        }

    } // end finalize
} // end class
