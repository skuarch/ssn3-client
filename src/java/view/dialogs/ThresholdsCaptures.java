package view.dialogs;

import controllers.ControllerTrees;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import model.beans.SubPiece;
import model.jms.JMSProccessor;
import model.util.PieceUtilities;
import view.frames.Login;
import view.notifications.Notifications;
import view.util.TreeUtilities;

/**
 *
 * @author skuarch
 */
public class ThresholdsCaptures extends JDialog {

    private static ThresholdsCaptures INSTANCE = null;
    private Notifications notifications = new Notifications();
    private DefaultMutableTreeNode rootNodeCollectors = null;
    private DefaultMutableTreeNode rootNodeDays = null;
    private DefaultMutableTreeNode rootNodeCaptures = null;
    private DefaultTreeModel modelCollectors = null;
    private DefaultTreeModel modelDays = null;
    private DefaultTreeModel modelCaptures = null;
    private DefaultMutableTreeNode nodeWait = new DefaultMutableTreeNode("wait...");
    private DefaultMutableTreeNode nodeSelectCollector = new DefaultMutableTreeNode("select a collector");
    private DefaultMutableTreeNode nodeSelectDay = new DefaultMutableTreeNode("select a day");
    private String selectedCollector = null;
    private String selectedDay = null;

    //==========================================================================
    /**
     * Creates new form Thresholds
     */
    private ThresholdsCaptures(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        onLoad();
    }

    //==========================================================================
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThresholdsCaptures(null, false);
        }
    }

    //==========================================================================
    public static ThresholdsCaptures getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //==========================================================================
    private void onLoad() {

        setLocationRelativeTo(getContentPane());
        setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/view/images/ssnIcon.png")));
        executeCollectors();

    } // end onLoad

    //==========================================================================
    private void executeCollectors() {

        setModel(jTreeCollectors, nodeWait);
        setModel(jTreeDays, nodeSelectCollector);
        setModel(jTreeCaptures, nodeSelectDay);
        jTreeCollectors.setEnabled(false);
        jTreeDays.setEnabled(false);
        jTreeCaptures.setEnabled(false);
        jButtonRefresh.setEnabled(false);
        jButtonExport.setEnabled(false);


        new Thread(new Runnable() {

            public void run() {

                try {

                    rootNodeCollectors = new ControllerTrees().getRootNodeCollectorThreshold();
                    modelCollectors = new DefaultTreeModel(rootNodeCollectors);
                    jTreeCollectors.setModel(modelCollectors);

                } catch (Exception e) {
                    notifications.error("error loading collectors", e);
                } finally {
                    jTreeCollectors.setEnabled(true);
                    jButtonRefresh.setEnabled(true);
                }

            }
        }).start();

    } // end executeCollectors

    //==========================================================================
    private void handlerCollectors(String selected) {

        jButtonExport.setEnabled(false);
        setModel(jTreeDays, nodeSelectCollector);
        setModel(jTreeCaptures, nodeSelectDay);
        jTreeDays.setEnabled(false);

        if (selected == null || selected.length() < 1) {
            notifications.error("the param is null or empty", new NullPointerException("the param is null or empty"));
            return;
        }

        try {

            if (selected.equalsIgnoreCase("no collectors") || selected.equalsIgnoreCase("collectors")) {
                jTreeCaptures.setEnabled(false);
                return;
            } else {
                selectedCollector = selected;
                setModel(jTreeDays, nodeWait);
                executeDays();
            }

        } catch (Exception e) {
            notifications.error("error loading collectors", e);
        }
    } // end handlerCollectors

    //==========================================================================
    private void handlerDays(String selected) {

        jButtonExport.setEnabled(false);

        if (selected == null || selected.length() < 1) {
            notifications.error("the param is null or empty", new NullPointerException("the param is null or empty"));
            return;
        }

        try {

            if (selected.equalsIgnoreCase("no days") || selected.equalsIgnoreCase("days")) {
                setModel(jTreeCaptures, nodeSelectDay);
                jTreeCaptures.setEnabled(false);
                return;
            } else {
                selectedDay = selected;
                executeCaptures();
            }

        } catch (Exception e) {
            notifications.error("error loading collectors", e);
        }
    } // end handlerCollectors

    //==========================================================================
    private void handlerCaptures(String selectedAlarm) {
        try {

            if (jTreeCollectors.isEnabled() && jTreeDays.isEnabled() && jTreeCaptures.isEnabled() && !selectedAlarm.equalsIgnoreCase("captures")) {
                jButtonExport.setEnabled(true);
            } else {
                jButtonExport.setEnabled(false);
            }

        } catch (Exception e) {
            notifications.error("error loading captures", e);
        }
    }

    //==========================================================================
    private void executeDays() {

        jTreeDays.setModel(new DefaultTreeModel(nodeWait));
        setModel(jTreeCaptures, nodeSelectDay);
        jTreeCollectors.setEnabled(false);
        jTreeCaptures.setEnabled(false);

        new Thread(new Runnable() {

            public void run() {
                try {

                    rootNodeDays = new ControllerTrees().getRootNodeDaysThreshold(selectedCollector);
                    modelDays = new DefaultTreeModel(rootNodeDays);
                    jTreeDays.setModel(modelDays);
                    jTreeDays.setEnabled(true);

                } catch (Exception e) {
                    notifications.error("error loading tree days", e);
                } finally {
                    jTreeCollectors.setEnabled(true);
                }
            }
        }).start();

    } // end executeDays

    //==========================================================================
    private void executeCaptures() {

        setModel(jTreeCaptures, nodeWait);
        jTreeCollectors.setEnabled(false);
        jTreeDays.setEnabled(false);

        new Thread(new Runnable() {

            public void run() {
                try {

                    rootNodeCaptures = new ControllerTrees().getRootNodeCapturesThreshold(selectedCollector, selectedDay);
                    modelCaptures = new DefaultTreeModel(rootNodeCaptures);
                    jTreeCaptures.setModel(modelCaptures);
                    jTreeCaptures.setEnabled(true);

                } catch (Exception e) {
                    notifications.error("error loading tree days", e);
                } finally {
                    jTreeCollectors.setEnabled(true);
                    jTreeDays.setEnabled(true);
                }
            }
        }).start();

    } // end executeDays

    //==========================================================================
    private void setModel(final JTree tree, final DefaultMutableTreeNode node) {

        new Thread(new Runnable() {

            public void run() {

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        try {
                            tree.setModel(new DefaultTreeModel(node));
                        } catch (Exception e) {
                            notifications.error("error changing the tree", e);
                        }
                    }
                });
            }
        }).start();

    } // end setModel

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeCollectors = new javax.swing.JTree();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTreeDays = new javax.swing.JTree();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTreeCaptures = new javax.swing.JTree();
        jButtonClose = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButtonExport = new javax.swing.JButton();
        jButtonRefresh = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thresholds Captures");
        setResizable(false);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("wait");
        jTreeCollectors.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeCollectors.setEnabled(false);
        jTreeCollectors.setShowsRootHandles(false);
        jTreeCollectors.setVerifyInputWhenFocusTarget(false);
        jTreeCollectors.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeCollectorsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeCollectors);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("select a collector");
        jTreeDays.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeDays.setEnabled(false);
        jTreeDays.setShowsRootHandles(false);
        jTreeDays.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeDaysValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(jTreeDays);

        treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("select a day");
        jTreeCaptures.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeCaptures.setEnabled(false);
        jTreeCaptures.setShowsRootHandles(false);
        jTreeCaptures.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                jTreeCapturesValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(jTreeCaptures);

        jButtonClose.setText("close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jLabel1.setText("Collectors");

        jLabel2.setText("Days");

        jLabel3.setText("Captures");

        jButtonExport.setText("export");
        jButtonExport.setEnabled(false);
        jButtonExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExportActionPerformed(evt);
            }
        });

        jButtonRefresh.setText("refresh");
        jButtonRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButtonClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRefresh)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExport))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(145, 145, 145)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonExport)
                    .addComponent(jButtonRefresh)
                    .addComponent(jButtonClose))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        setVisible(false);        
    }//GEN-LAST:event_jButtonCloseActionPerformed

    //==========================================================================
    private void jButtonRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRefreshActionPerformed
        executeCollectors();
    }//GEN-LAST:event_jButtonRefreshActionPerformed

    //==========================================================================
    private void jTreeCollectorsValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeCollectorsValueChanged

        new Thread(new Runnable() {

            public void run() {
                try {

                    if (jTreeCollectors.isEnabled()) {
                        TreePath treePath = jTreeCollectors.getSelectionPath();
                        if (treePath != null) {
                            handlerCollectors(TreeUtilities.getSelect(treePath));
                        }
                    }

                } catch (Exception e) {
                    notifications.error("error getting selection from tree", e);
                }
            }
        }).start();
    }//GEN-LAST:event_jTreeCollectorsValueChanged

    //==========================================================================
    private void jTreeDaysValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeDaysValueChanged
        
        new Thread(new Runnable() {

            public void run() {
                try {

                    if (jTreeDays.isEnabled()) {
                        TreePath treePath = jTreeDays.getSelectionPath();
                        if (treePath != null) {
                            handlerDays(TreeUtilities.getSelect(treePath));
                        }
                    }

                } catch (Exception e) {
                    notifications.error("error getting selection from tree", e);
                }
            }
        }).start();
    }//GEN-LAST:event_jTreeDaysValueChanged

    //==========================================================================
    private void jTreeCapturesValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_jTreeCapturesValueChanged
        new Thread(new Runnable() {

            public void run() {
                try {

                    if (jTreeCaptures.isEnabled()) {
                        TreePath treePath = jTreeCaptures.getSelectionPath();
                        if (treePath != null) {
                            handlerCaptures(TreeUtilities.getSelect(treePath));
                        }
                    }

                } catch (Exception e) {
                    notifications.error("error getting selection from tree", e);
                }
            }
        }).start();
    }//GEN-LAST:event_jTreeCapturesValueChanged

    //==========================================================================
    private void jButtonExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExportActionPerformed
        
        SubPiece subPiece = new SubPiece();
        JMSProccessor jmsp = null;
        
        try {
            
            jmsp = new JMSProccessor();
            subPiece.setView("threshold capture");
            //jmsp.send("threshold capture", selectedCollector, "srs", "request", "mocos", new PieceUtilities().subPieceToHashMap(subPiece));
            jmsp.sendReceive("threshold capture", selectedCollector, "srs", 1000000 , new PieceUtilities().subPieceToHashMap(subPiece));
            //jmsp.receiveStreamMessage(new File("/home/skuarch/Desktop/salida.txt"), "mocos");            
            
        } catch (Exception e) {
            notifications.error("error receiving file", e);
        }
        
    }//GEN-LAST:event_jButtonExportActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonExport;
    private javax.swing.JButton jButtonRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTree jTreeCaptures;
    private javax.swing.JTree jTreeCollectors;
    private javax.swing.JTree jTreeDays;
    // End of variables declaration//GEN-END:variables
}