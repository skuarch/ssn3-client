package view.frames;

import controllers.ControllerTrees;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.beans.Collectors;
import model.beans.SubPiece;
import model.dao.DAO;
import view.dialogs.EventViewer;
import view.notifications.Notifications;
import view.panels.BarChartHorizontalPanel;
import view.panels.FactoryPanel;
import view.util.JTabPaneUtilities;
import view.util.TreeUtilities;

/**
 *
 * @author skuarch
 */
public class E2E extends JFrame {

    private static E2E INSTANCE = null;
    private static final Notifications notifications = new Notifications();
    private DefaultTreeModel modelSource = null;
    private DefaultTreeModel modelDestination = null;
    private DefaultMutableTreeNode genericRootNode = null;

    //==========================================================================
    /**
     * Creates new form E2E
     */
    private E2E(Frame parent, boolean modal) {
        super();
        initComponents();
        setLocationRelativeTo(getContentPane());
        setIconImage(Toolkit.getDefaultToolkit().getImage(E2E.class.getResource("/view/images/ssnIcon.png")));
        onLoad();
    } // end E2E

    //==========================================================================
    private synchronized static void createInstance(Frame parent, boolean modal) {
        if (INSTANCE == null) {
            INSTANCE = new E2E(parent, modal);
        }
    } // end createInstance

    //==========================================================================
    public static E2E getInstance(Frame parent, boolean modal) {
        if (INSTANCE == null) {
            createInstance(parent, modal);
        }
        return INSTANCE;
    } // end getIntance

    //==========================================================================
    private void onLoad() {

        try {

            genericRootNode = new ControllerTrees().getRootNodeCollectors();

            modelSource = new DefaultTreeModel(genericRootNode);
            jTreeSource.setModel(modelSource);

            modelDestination = new DefaultTreeModel(genericRootNode);
            jTreeDestination.setModel(modelDestination);

        } catch (Exception e) {
            notifications.error("error creating tree sources", e);
        }

    } // end onLoad

    //==========================================================================
    private void remodelTreeDestination() {

        DefaultMutableTreeNode rootNode = null;

        try {

            rootNode = (DefaultMutableTreeNode) modelDestination.getRoot();
            rootNode.add(new DefaultMutableTreeNode("<" + jTextFieldAdd.getText() + ">"));
            modelDestination = new DefaultTreeModel(rootNode);
            jTreeDestination.setModel(modelDestination);
            jTextFieldAdd.setText("");

        } catch (Exception e) {
            notifications.error("error creating tree destination", e);
        }

    } // end remodelTreeDestination

    //==========================================================================
    private boolean validationAdd() {

        boolean flag = false;
        String text = null;

        try {

            text = jTextFieldAdd.getText();

            if (text == null || text.length() < 1 || text.contains(" ")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            notifications.error("error in validation", e);
        }

        return flag;

    } // end validationAdd

    //==========================================================================
    private void execute() {

        new Thread(new Runnable() {

            public void run() {

                BarChartHorizontalPanel barChartHorizontalPanel = null;
                jButtonExecute.setEnabled(false);
                jButtonExecute.setText("loading");
                String collectorSource = TreeUtilities.getSelect(jTreeSource.getSelectionPath());
                String collectorDestination = TreeUtilities.getSelect(jTreeDestination.getSelectionPath());
                SubPiece sp1 = new SubPiece();
                JPanel tabTitle = null;
                ArrayList<Collectors> al = null;

                try {

                    if (collectorSource == null || collectorSource.length() < 1) {
                        return;
                    }

                    if (collectorDestination == null || collectorDestination.length() < 1) {
                        collectorDestination = jTextFieldAdd.getText();
                    }

                    if (collectorDestination == null || collectorDestination.length() < 1) {
                        return;
                    }


                    if (collectorDestination.contains("<")) {
                        collectorDestination = collectorDestination.replace("<", "");
                        collectorDestination = collectorDestination.replace(">", "");
                    } else {
                        al = (ArrayList<Collectors>) new DAO().find(Collectors.class, collectorDestination, "host");
                        collectorDestination = al.get(0).getIp();                        
                    }
                    
                    sp1.setE2E(collectorDestination);
                    sp1.setView("End to End");
                    sp1.setCollector(collectorSource);

                    barChartHorizontalPanel = new BarChartHorizontalPanel(sp1);
                    barChartHorizontalPanel.showFooter(false);
                    barChartHorizontalPanel.showPagination(false);
                    barChartHorizontalPanel.setTitleChart(collectorSource + " -> " + collectorDestination);
                    barChartHorizontalPanel.execute();
                    barChartHorizontalPanel.setName(collectorSource + " -> " + collectorDestination);

                    jTabbedPaneCharts.add(barChartHorizontalPanel);

                    tabTitle = JTabPaneUtilities.getTabTitle(collectorSource + " -> " + collectorDestination, getCloseButton(barChartHorizontalPanel.getName()));
                    //jTabbedPaneCharts.setTabComponentAt(jTabbedPaneCharts.getTabCount() - 1, tabTitle);
                    jTabbedPaneCharts.setSelectedIndex(jTabbedPaneCharts.getTabCount() - 1);

                } catch (Exception e) {
                    notifications.error("error requesting data", e);
                } finally {
                    jButtonExecute.setText("execute");
                    jButtonExecute.setEnabled(true);
                }
            }
        }).start();
    } // end execute   

    //==========================================================================
    public Component getComponent(String name) {

        if (name == null || name.length() < 1) {
            notifications.error("name is null or empty", new NullPointerException());
            return null;
        }

        Component component = null;
        boolean found = false;
        int num = 0;

        try {

            found = JTabPaneUtilities.findName(name, jTabbedPaneCharts);

            if (found) {
                num = JTabPaneUtilities.getNumComponent(name, jTabbedPaneCharts);
                component = jTabbedPaneCharts.getComponentAt(num);
            } else {
                notifications.error("component " + name + " not found", new Exception());
                return null;
            }

        } catch (Exception e) {
            notifications.error("error getting component", e);
        }

        if (component == null) {
            notifications.error("returning null", new Exception());
        }

        return component;
    } // end getComponent

    //==========================================================================    
    public void closeTab(final String tabName) {

        if (tabName == null || tabName.length() < 1) {
            notifications.error("name is null or empty", new NullPointerException());
            return;
        }

        FactoryPanel fp = null;
        Component component = null;

        try {

            EventViewer.getInstance().appendInfoTextConsole("closing tab: " + tabName);
            component = getComponent(tabName);
            remove(component);

            fp = (FactoryPanel) component;
            fp.destroy();

        } catch (Exception e) {
            notifications.error("error closing tab: " + tabName, e);
        } finally {
            component = null;
            fp = null;
        }

    } // end closeTab

    //==========================================================================
    public JButton getCloseButton(final String nameComponent) {
        JButton button = null;

        try {

            button = new JButton("");
            button.setPreferredSize(new Dimension(10, 10));
            button.setToolTipText("close this tab");
            button.setBackground(Color.red);

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    try {

                        closeTab(nameComponent);

                    } catch (Exception e) {
                        notifications.error("imposible close this tab", e);
                    }

                }
            });

        } catch (Exception e) {
            notifications.error("imposible close this tab", e);
        }

        return button;

    } // end closeButton    

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jTabbedPaneCharts = new javax.swing.JTabbedPane();
        jSplitPane3 = new javax.swing.JSplitPane();
        jTabbedPane4 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTreeSource = new javax.swing.JTree();
        jTabbedPane5 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTreeDestination = new javax.swing.JTree();
        jTextFieldAdd = new javax.swing.JTextField();
        jButtonAdd = new javax.swing.JButton();
        jButtonExecute = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("End to End");

        jSplitPane1.setDividerLocation(300);

        jTabbedPaneCharts.setTabLayoutPolicy(javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT);
        jSplitPane1.setRightComponent(jTabbedPaneCharts);

        jSplitPane3.setDividerLocation(200);
        jSplitPane3.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("wait");
        jTreeSource.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeSource.setRootVisible(false);
        jTreeSource.setShowsRootHandles(false);
        jScrollPane3.setViewportView(jTreeSource);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)
        );

        jTabbedPane4.addTab("Source", jPanel3);

        jSplitPane3.setTopComponent(jTabbedPane4);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("wait");
        jTreeDestination.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeDestination.setRootVisible(false);
        jTreeDestination.setShowsRootHandles(false);
        jScrollPane4.setViewportView(jTreeDestination);

        jButtonAdd.setText("add");
        jButtonAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAddActionPerformed(evt);
            }
        });

        jButtonExecute.setText("execute");
        jButtonExecute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExecuteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonAdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExecute))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 162, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonAdd)
                    .addComponent(jButtonExecute))
                .addContainerGap())
        );

        jTabbedPane5.addTab("Destination", jPanel4);

        jSplitPane3.setRightComponent(jTabbedPane5);

        jSplitPane1.setLeftComponent(jSplitPane3);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 798, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jButtonAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAddActionPerformed
        if (validationAdd()) {
            remodelTreeDestination();
        }
    }//GEN-LAST:event_jButtonAddActionPerformed

    //==========================================================================
    private void jButtonExecuteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExecuteActionPerformed
        execute();
    }//GEN-LAST:event_jButtonExecuteActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonAdd;
    private javax.swing.JButton jButtonExecute;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane3;
    private javax.swing.JTabbedPane jTabbedPane4;
    private javax.swing.JTabbedPane jTabbedPane5;
    private javax.swing.JTabbedPane jTabbedPaneCharts;
    private javax.swing.JTextField jTextFieldAdd;
    private javax.swing.JTree jTreeDestination;
    private javax.swing.JTree jTreeSource;
    // End of variables declaration//GEN-END:variables
}
