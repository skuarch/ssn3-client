package view.dialogs;

import controllers.ControllerJobs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import model.beans.SubPiece;
import view.helpers.HandlerPanels;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.splits.Navigator;
import view.util.JTextFieldLimit;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class SearchPort extends javax.swing.JDialog {

    private Notifications notifications = null;
    private String collector = null;
    private String job = null;

    //==========================================================================
    /**
     * Creates new form SearchPort
     */
    public SearchPort(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(getRootPane());
        notifications = new Notifications();
        onLoad();
    }

    //==========================================================================
    private void onLoad() {
        loadCollectors();
        jTextFieldPort.setDocument(new JTextFieldLimit(5));
    }

    //==========================================================================
    private void loadCollectors() {

        try {

            jComboBoxCollectors.setModel(new SwingUtilities().getDefaultComboBoxModelCollectors());
            jComboBoxCollectors.addActionListener(new ActionListener() {

                //==========================================================================
                public void actionPerformed(ActionEvent e) {

                    String selected = jComboBoxCollectors.getSelectedItem().toString();

                    if (selected == null || selected.equalsIgnoreCase("select a collector") || selected.equalsIgnoreCase("no collectors")) {
                        collector = null;
                        jComboBoxJobs.setEnabled(false);
                        return;
                    } else {
                        collector = selected;
                        loadJobs(collector);
                    }

                }
            });

        } catch (Exception e) {
            notifications.error("error getting collectors", e);
        }

    } // end loadCollectors

    //==========================================================================
    private void loadJobs(final String collector) {

        Thread t = new Thread(new Runnable() {

            public void run() {

                job = null;
                jComboBoxJobs.setEnabled(false);
                jComboBoxJobs.setModel(new DefaultComboBoxModel(new String[]{"loading collectors"}));
                String[] jobs = null;

                try {

                    jobs = new ControllerJobs().getJobs(collector);

                    if (jobs != null) {

                        jComboBoxJobs.setModel(new DefaultComboBoxModel(jobs));
                        jComboBoxJobs.setEnabled(true);

                        jComboBoxJobs.addActionListener(new ActionListener() {

                            //==========================================================================
                            public void actionPerformed(ActionEvent e) {

                                String selected = jComboBoxJobs.getSelectedItem().toString();
                                if (selected == null || selected.equalsIgnoreCase("no jobs")) {
                                    job = null;
                                    return;
                                } else {
                                    job = selected;
                                }

                            }
                        });

                    } else {
                        jComboBoxJobs.setEnabled(false);
                        jComboBoxJobs.setModel(new DefaultComboBoxModel(new String[]{"no jobs"}));
                    }

                } catch (Exception e) {
                    notifications.error("error loading jobs", e);
                }
            }
        });
        t.start();

    } // end loadJobs    

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldPort = new javax.swing.JTextField();
        jComboBoxCollectors = new javax.swing.JComboBox();
        jComboBoxJobs = new javax.swing.JComboBox();
        jButtonSearch = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Port");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(254, 254, 254));

        jLabel1.setText("Port:");

        jLabel2.setText("Collector:");

        jLabel3.setText("Job:");

        jComboBoxCollectors.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select a collector" }));

        jComboBoxJobs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "please select a collector" }));
        jComboBoxJobs.setEnabled(false);

        jButtonSearch.setText("search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jButton1.setText("close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxJobs, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBoxCollectors, javax.swing.GroupLayout.Alignment.LEADING, 0, 180, Short.MAX_VALUE)
                            .addComponent(jTextFieldPort))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSearch)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBoxCollectors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxJobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSearch)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed

        int port = 0;

        try {

            if (collector != null && job != null) {
                
                port = Integer.parseInt(jTextFieldPort.getText());

                if (port < 1 || port > 65535) {
                    notifications.error("the port should be between 1 to 65535", new Exception());
                    return;
                }

                //here, create a new tab
                SubPiece subPiece = new SubPiece();
                subPiece.setView("Bandwidth Over Time Bits");
                subPiece.setPortNumber(String.valueOf(port));
                subPiece.setCollector(collector);
                subPiece.setJob(job);
                FactoryPanel tmp = (FactoryPanel) new HandlerPanels().getComponent(subPiece);
                Navigator.getInstance().addTabInSubNavigator(tmp);

                setVisible(false);
            }

        } catch (Exception e) {
            notifications.error("imposible to make the search", e);
        }
    }//GEN-LAST:event_jButtonSearchActionPerformed

    //==========================================================================
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButtonSearch;
    private javax.swing.JComboBox jComboBoxCollectors;
    private javax.swing.JComboBox jComboBoxJobs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextFieldPort;
    // End of variables declaration//GEN-END:variables
}