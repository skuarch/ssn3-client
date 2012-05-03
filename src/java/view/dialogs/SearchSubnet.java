/*
 * SearchSubnet.java
 *
 * Created on Dec 15, 2011, 11:48:28 AM
 */
package view.dialogs;

import controllers.ControllerJobs;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultComboBoxModel;
import model.beans.SubPiece;
import model.util.IPAddressValidator;
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
public class SearchSubnet extends javax.swing.JDialog {

    private String subnet = null;
    private String netmask = null;
    private Notifications notifications = null;
    private String collector = null;
    private String job = null;

    //==========================================================================
    /**
     * Creates new form SearchSubnet
     */
    public SearchSubnet(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        notifications = new Notifications();
        initComponents();
        setLocationRelativeTo(getRootPane());
        onLoad();
    } // end SearchSubnet

    //==========================================================================
    private void onLoad() {

        loadCollectors();
        jTextFieldIP1.setDocument(new JTextFieldLimit(3));
        jTextFieldIP2.setDocument(new JTextFieldLimit(3));
        jTextFieldIP3.setDocument(new JTextFieldLimit(3));
        jTextFieldIP4.setDocument(new JTextFieldLimit(3));
        jTextFieldMask.setDocument(new JTextFieldLimit(2));

    } // end onLoad

    //==========================================================================
    private void loadCollectors() {

        try {

            jComboBoxCollectors.setModel(new SwingUtilities().getDefaultComboBoxModelCollectors());
            jComboBoxCollectors.addActionListener(new ActionListener() {

                //==========================================================================
                @Override
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
    private void setSubnet() {

        String oct1 = null;
        String oct2 = null;
        String oct3 = null;
        String oct4 = null;

        try {

            oct1 = jTextFieldIP1.getText();
            oct2 = jTextFieldIP2.getText();
            oct3 = jTextFieldIP3.getText();
            oct4 = jTextFieldIP4.getText();

            if (oct1 == null || oct1.length() < 1 || oct1.length() > 3) {
                subnet = null;
                return;
            }

            if (oct2 == null || oct2.length() < 1 || oct2.length() > 3) {
                subnet = null;
                return;
            }

            if (oct3 == null || oct3.length() < 1 || oct3.length() > 3) {
                subnet = null;
                return;
            }

            if (oct4 == null || oct4.length() < 1 || oct4.length() > 3) {
                subnet = null;
                return;
            }

            subnet = oct1 + "." + oct2 + "." + oct3 + "." + oct4;

            if (!new IPAddressValidator().validate(subnet)) {
                subnet = null;
                return;
            }

        } catch (Exception e) {
            notifications.error("error in ip address", e);
        }

    } // end getIp    

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldIP1 = new javax.swing.JTextField(3);
        jTextFieldIP2 = new javax.swing.JTextField(3);
        jTextFieldIP3 = new javax.swing.JTextField(3);
        jTextFieldIP4 = new javax.swing.JTextField(3);
        jComboBoxCollectors = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBoxJobs = new javax.swing.JComboBox();
        jButtonSearch = new javax.swing.JButton();
        jTextFieldMask = new javax.swing.JTextField(3);
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Subnet");
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(254, 254, 254));

        jLabel2.setText("Subnet");

        jTextFieldIP1.setColumns(3);

        jTextFieldIP2.setColumns(3);

        jTextFieldIP3.setColumns(3);

        jTextFieldIP4.setColumns(3);

        jComboBoxCollectors.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "select a collector" }));

        jLabel3.setText("Collector:");

        jLabel4.setText("Job:");

        jComboBoxJobs.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "please select a collector" }));
        jComboBoxJobs.setEnabled(false);

        jButtonSearch.setText("search");
        jButtonSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSearchActionPerformed(evt);
            }
        });

        jTextFieldMask.setColumns(3);

        jLabel5.setText("/");

        jButton1.setText("close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonSearch))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxCollectors, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jComboBoxJobs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jTextFieldIP1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIP2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIP3, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIP4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldMask, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldIP1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jTextFieldIP2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldIP3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jTextFieldIP4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextFieldMask, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jComboBoxCollectors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jComboBoxJobs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSearch)
                    .addComponent(jButton1))
                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jButtonSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSearchActionPerformed

        setSubnet();

        netmask = jTextFieldMask.getText();

        if (subnet != null && subnet.length() > 5 && collector != null && job != null && netmask != null) {

            if (!new SwingUtilities().validateNetMask(netmask)) {
                notifications.error("please select a subnet, netmask, collector and job", new Exception("please select a subnet, netmask, collector and job"));
                return;
            }

            //here, create a new tab
            SubPiece subPiece = new SubPiece();
            subPiece.setView("Bandwidth Over Time Bits");
            subPiece.setCollector(collector);
            subPiece.setSubnet(subnet);
            subPiece.setNetmask(netmask);
            subPiece.setJob(job);
            FactoryPanel tmp = (FactoryPanel) new HandlerPanels().getComponent(subPiece);
            Navigator.getInstance().addTabInSubNavigator(tmp);

            setVisible(false);

        } else {
            notifications.error("please select a subnet, netmask, collector and job", new Exception());
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextFieldIP1;
    private javax.swing.JTextField jTextFieldIP2;
    private javax.swing.JTextField jTextFieldIP3;
    private javax.swing.JTextField jTextFieldIP4;
    private javax.swing.JTextField jTextFieldMask;
    // End of variables declaration//GEN-END:variables
}
