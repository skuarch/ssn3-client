package view.dialogs;

import controllers.ControllerJMS;
import controllers.ControllerRequestObject;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.beans.SubPiece;
import view.notifications.Notifications;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class ConfigurationThresholds extends javax.swing.JDialog {

    private String collector = null;
    private Notifications notifications = null;
    private String[] data = null;
    private String thresholdBandwidth = "0";
    private String thresholdSnapLen = null;
    private String thresholdMeasurement = "mbps";
    private boolean isThresholdActive = false;

    //==========================================================================
    /**
     * Creates new form ConfigurationThresholds
     */
    public ConfigurationThresholds(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        notifications = new Notifications();
        initComponents();
        onLoad();
    }

    //==========================================================================
    private void onLoad() {
        setLocationRelativeTo(getRootPane());
        loadCollectors();
    }

    //==========================================================================
    private void loadCollectors() {

        try {

            jComboBoxCollectors.setModel(new SwingUtilities().getDefaultComboBoxModelCollectors());
            jComboBoxCollectors.setEnabled(true);

            jComboBoxCollectors.addActionListener(new ActionListener() {

                //==========================================================================
                public void actionPerformed(ActionEvent e) {

                    String selected = jComboBoxCollectors.getSelectedItem().toString();

                    if (selected == null || selected.equalsIgnoreCase("select a collector") || selected.equalsIgnoreCase("no collectors")) {
                        enableComponents(false);
                        collector = null;
                    } else {
                        collector = selected;
                        execute();
                    }
                }
            });

        } catch (Exception e) {
            notifications.error("error getting collectors", e);
        } finally {
            jComboBoxCollectors.setEnabled(true);
        }

    } // end loadCollectors

    //==========================================================================
    private void execute() {

        new Thread(new Runnable() {

            public void run() {
                try {

                    cleanComponents();
                    enableComponents(false);
                    requestData();
                    setComponents();
                    enableComponents(true);

                } catch (Exception e) {
                    notifications.error("error imposible get configuration", e);
                } finally {
                    jComboBoxCollectors.setEnabled(true);
                }
            }
        }).start();

    } // end execute

    //==========================================================================
    private void setComponents() {       
        
        int items = 0;

        try {

            jComboBoxCollectors.setEnabled(false);

            if (data != null) {

                thresholdBandwidth = data[0];
                thresholdMeasurement = data[1];
                isThresholdActive = Boolean.parseBoolean(data[2]);
                thresholdSnapLen = data[3];

                jTextFieldBandwidth.setText(thresholdBandwidth);
                jTextFieldSpanLen.setText(thresholdSnapLen);
                jCheckBoxActive.setSelected(isThresholdActive);


                items = jComboBoxMeasurement.getItemCount();

                for (int i = 0; i < items; i++) {

                    if (jComboBoxMeasurement.getItemAt(i).equals(thresholdMeasurement)) {
                        jComboBoxMeasurement.setSelectedIndex(i);
                        break;
                    }

                }


            }

        } catch (Exception e) {
            notifications.error("error setting components", e);
        }

    } // end setComponents   

    //==========================================================================
    private void cleanComponents() {

        jTextFieldBandwidth.setText("");
        jCheckBoxActive.doClick();

    }

    //==========================================================================
    private void requestData() {

        SubPiece subPiece = new SubPiece();

        try {

            subPiece.setView("threshold get");
            subPiece.setCollector(collector);

            data = (String[]) new ControllerRequestObject().getObject(subPiece);

        } catch (Exception e) {
            notifications.error("error getting objects", e);
        }

    } // end requestData

    //==========================================================================
    private void enableComponents(boolean flag) {
        jTextFieldBandwidth.setEnabled(flag);
        jComboBoxMeasurement.setEnabled(flag);
        jCheckBoxActive.setEnabled(flag);
        jComboBoxCollectors.setEnabled(flag);
        jButtonSave.setEnabled(flag);
        jTextFieldSpanLen.setEnabled(flag);
    } // end enableComponents

    //==========================================================================
    private boolean validateThreshold() {

        int bw = 0;
        int sl = 0;

        try {

            //bandwidth
            thresholdBandwidth = jTextFieldBandwidth.getText();
            if (thresholdBandwidth.equalsIgnoreCase("0")) {
                notifications.error("bandwidth must be bigger than 0", new Exception("bandwidth must be bigger than 0"));
                return false;
            } else {
                bw = Integer.parseInt(thresholdBandwidth);
                if (bw < 1) {
                    notifications.error("bandwidth must be bigger than 0", new Exception("bandwidth must be bigger than 0"));
                    return false;
                }
            }

            //snaplen
            thresholdSnapLen = jTextFieldSpanLen.getText();
            if (thresholdSnapLen.equalsIgnoreCase("0")) {
                notifications.error("snapLen must be bigger than 0", new Exception("snapLen must be bigger than 0"));
                return false;
            } else {
                sl = Integer.parseInt(thresholdSnapLen);
                if (sl < 64 || sl > 65535) {
                    notifications.error("snapLen must be bigger than 63 and less than 65355", new Exception("snapLen must be bigger than 63 and less than 65355"));
                    return false;
                }
            }

        } catch (NumberFormatException nfe) {
            notifications.error("bandwidth and snaplen must be numbers", nfe);
        } catch (Exception e) {
            notifications.error("error in validating", e);
        }

        return true;

    } // end validateThreshold
    
    //==========================================================================
    private void sendData() {       
        
        SubPiece thresholds = new SubPiece();
        
        try {
            
            //thresholds = subpiece
            
            thresholdMeasurement = jComboBoxMeasurement.getSelectedItem().toString();
            
            thresholds.setView("threshold save");
            thresholds.setThresholdBandwidth(thresholdBandwidth);
            thresholds.setThresholdMeasurement(thresholdMeasurement);
            thresholds.setThresholdSnapLen(thresholdSnapLen);
            thresholds.isThresholdActive(isThresholdActive);
            
            new ControllerJMS().send("threshold save", collector, thresholds);
            
            jLabelMessage.setText("Done !!!");
            
        } catch (Exception e) {
            notifications.error("imposible send data", e);
        }
        
    } // end sendData

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxCollectors = new javax.swing.JComboBox();
        jTextFieldBandwidth = new javax.swing.JTextField();
        jButtonSave = new javax.swing.JButton();
        jComboBoxMeasurement = new javax.swing.JComboBox();
        jButtonClose = new javax.swing.JButton();
        jCheckBoxActive = new javax.swing.JCheckBox();
        jTextFieldSpanLen = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabelMessage = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Configuration Thresholds Captures");
        setResizable(false);

        jComboBoxCollectors.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "wait" }));
        jComboBoxCollectors.setEnabled(false);

        jTextFieldBandwidth.setEnabled(false);

        jButtonSave.setText("save");
        jButtonSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveActionPerformed(evt);
            }
        });

        jComboBoxMeasurement.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "mbps", "gbps", "kbps" }));
        jComboBoxMeasurement.setEnabled(false);

        jButtonClose.setText("close");
        jButtonClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCloseActionPerformed(evt);
            }
        });

        jCheckBoxActive.setText("active");
        jCheckBoxActive.setEnabled(false);

        jTextFieldSpanLen.setEnabled(false);

        jLabel1.setText("SnapLen");

        jLabelMessage.setForeground(new java.awt.Color(4, 0, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jComboBoxCollectors, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelMessage)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonClose)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSave))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldBandwidth)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxMeasurement, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextFieldSpanLen)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCheckBoxActive)))
                .addGap(46, 46, 46))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jComboBoxCollectors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxMeasurement, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldBandwidth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldSpanLen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxActive)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonSave)
                    .addComponent(jButtonClose)
                    .addComponent(jLabelMessage))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jButtonCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCloseActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_jButtonCloseActionPerformed

    //==========================================================================
    private void jButtonSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveActionPerformed
        if (validateThreshold()) {
            sendData();
        }        
    }//GEN-LAST:event_jButtonSaveActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonClose;
    private javax.swing.JButton jButtonSave;
    private javax.swing.JCheckBox jCheckBoxActive;
    private javax.swing.JComboBox jComboBoxCollectors;
    private javax.swing.JComboBox jComboBoxMeasurement;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelMessage;
    private javax.swing.JTextField jTextFieldBandwidth;
    private javax.swing.JTextField jTextFieldSpanLen;
    // End of variables declaration//GEN-END:variables
}
