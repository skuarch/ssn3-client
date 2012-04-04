package view.frames;

import com.lowagie.text.*;
import com.lowagie.text.pdf.DefaultFontMapper;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import controllers.ControllerConfiguration;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.geom.Rectangle2D;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import model.beans.SubPiece;
import model.beans.User;
import org.jfree.chart.JFreeChart;
import view.dialogs.*;
import view.helpers.ControlNavigators;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.splits.Navigator;
import view.splits.SubNavigator;
import view.trees.TreeCollectors;
import view.trees.TreeViews;

/**
 *
 * @author skuarch
 */
public class MainFrame extends JFrame {

    private static MainFrame INSTANCE = null;
    private Notifications notifications = null;

    //==========================================================================
    /**
     * Creates new form MainFrame
     */
    private MainFrame() {

        notifications = new Notifications();
        initComponents();
        setLocationRelativeTo(getContentPane());
        onLoad();

    } // end MainFrame

    //==========================================================================
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MainFrame();
        }
    }

    //==========================================================================
    public static MainFrame getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //==========================================================================
    private synchronized void onLoad() {

        setExtendedState(MAXIMIZED_BOTH);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/view/images/ssnIcon.png")));

        try {

            //title
            setTitle("SSN " + new ControllerConfiguration().getInitialConfiguration().getProjectName().toUpperCase());

            Thread t = new Thread(new Runnable() {

                public void run() {

                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            jTabbedPaneView.add(TreeViews.getInstance());
                            jTabbedPaneCollectors.add(TreeCollectors.getInstance());
                            jSplitPaneMain.setRightComponent(Navigator.getInstance());
                        }
                    });
                }
            });
            t.setName("onLoad");
            t.start();

        } catch (Exception e) {
            notifications.error("error loading interface", e);
        }

    } // end onLoad

    //==========================================================================
    public void control() {

        Thread t = new Thread(new ControlNavigators());
        t.start();
        t.setName("ControlNavigators");

    } // end control

    //==========================================================================
    private void callEventViewer() {

        EventViewer eventViewer = null;

        try {

            eventViewer = EventViewer.getInstance();

            if (eventViewer.isVisible()) {
                eventViewer.setVisible(false);
            } else {
                eventViewer.setVisible(true);
            }

        } catch (Exception e) {
            notifications.error("error opening window", e);
        }

    }

    //==========================================================================
    private void createPdf() {

        new Thread(new Runnable() {

            public void run() {

                try {
                    String path = new ChooserFile().getPath();
                    if (path != null) {
                        SaveData saveData = new SaveData(MainFrame.getInstance(), false);
                        saveData.setVisible(true);
                        saveData.createPdfReport(Navigator.getInstance().getComponents(), path);
                    }
                } catch (Exception e) {
                    notifications.error("error creating pdf", e);
                }
            }
        }).start();


    } // end createPdf 

    //==========================================================================
    private Table tableSubPiece(SubPiece subPiece) {

        Table table = null;
        Cell c1 = null;
        Font headerFont = FontFactory.getFont(FontFactory.HELVETICA, 9, Font.BOLD, new Color(0, 0, 0));
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.NORMAL, new Color(0, 0, 0));

        try {

            table = new Table(2, 3);
            table.setPadding(2);
            table.setSpacing(2);
            table.setSpaceBetweenCells(0);
            c1 = new Cell(new Paragraph("Propertie", headerFont));
            c1.setHeader(true);
            table.addCell(c1);
            c1 = new Cell(new Paragraph("Value", headerFont));
            table.addCell(c1);
            table.endHeaders();

            table.addCell(new Paragraph("collector", cellFont));
            table.addCell(new Paragraph(subPiece.getCollector(), cellFont));
            table.addCell(new Paragraph("job", cellFont));
            table.addCell(new Paragraph(subPiece.getJob(), cellFont));

            if (!subPiece.getDates().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("dates", cellFont));
                table.addCell(new Paragraph(subPiece.getDates(), cellFont));
            }

            if (!subPiece.getIpAddress().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("ip address", cellFont));
                table.addCell(new Paragraph(subPiece.getIpAddress(), cellFont));
            }

            if (!subPiece.getDrillDown().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("drill down", cellFont));
                table.addCell(new Paragraph(subPiece.getDrillDown(), cellFont));
            }

            if (!subPiece.getIPProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("ip protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getIPProtocols(), cellFont));
            }

            if (!subPiece.getNetworkProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("network protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getNetworkProtocols(), cellFont));
            }

            if (!subPiece.getTCPProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("tcp protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getTCPProtocols(), cellFont));
            }

            if (!subPiece.getUDPProtocols().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("udp protocols", cellFont));
                table.addCell(new Paragraph(subPiece.getUDPProtocols(), cellFont));
            }

            if (!subPiece.getTypeService().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("type service", cellFont));
                table.addCell(new Paragraph(subPiece.getTypeService(), cellFont));
            }

            if (!subPiece.getWebsites().equalsIgnoreCase("not applicable")) {
                table.addCell(new Paragraph("websites", cellFont));
                table.addCell(new Paragraph(subPiece.getWebsites(), cellFont));
            }

        } catch (Exception e) {
            notifications.error("error creating table subpiece", e);
        }

        return table;

    } // end tableSubPiece

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPaneMain = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jTabbedPaneView = new javax.swing.JTabbedPane();
        jTabbedPaneCollectors = new javax.swing.JTabbedPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItemExit = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItemEventViewer = new javax.swing.JMenuItem();
        jMenuItemConfiguration = new javax.swing.JMenuItem();
        jMenuSearch = new javax.swing.JMenu();
        jMenuItemSubnet = new javax.swing.JMenuItem();
        jMenuItemIP = new javax.swing.JMenuItem();
        jMenuItemCloseAll = new javax.swing.JMenuItem();
        jMenuItemReport = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPaneMain.setDividerLocation(250);

        jSplitPane2.setDividerLocation(230);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setTopComponent(jTabbedPaneView);
        jSplitPane2.setRightComponent(jTabbedPaneCollectors);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        jSplitPaneMain.setLeftComponent(jPanel1);

        jMenu1.setText("File");

        jMenuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemExit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/shutdown-mini.png"))); // NOI18N
        jMenuItemExit.setText("exit");
        jMenuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tools");

        jMenuItemEventViewer.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_V, java.awt.event.InputEvent.SHIFT_MASK | java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemEventViewer.setIcon(new javax.swing.ImageIcon(getClass().getResource("/view/images/eventViewer.png"))); // NOI18N
        jMenuItemEventViewer.setText("event viewer");
        jMenuItemEventViewer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemEventViewerActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemEventViewer);

        jMenuItemConfiguration.setText("configuration");
        jMenuItemConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemConfigurationActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemConfiguration);

        jMenuSearch.setText("search");

        jMenuItemSubnet.setText("subnet");
        jMenuItemSubnet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemSubnetActionPerformed(evt);
            }
        });
        jMenuSearch.add(jMenuItemSubnet);

        jMenuItemIP.setText("ip");
        jMenuItemIP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemIPActionPerformed(evt);
            }
        });
        jMenuSearch.add(jMenuItemIP);

        jMenu3.add(jMenuSearch);

        jMenuItemCloseAll.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItemCloseAll.setText("close all");
        jMenuItemCloseAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemCloseAllActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemCloseAll);

        jMenuItemReport.setText("create report");
        jMenuItemReport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemReportActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItemReport);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPaneMain, javax.swing.GroupLayout.DEFAULT_SIZE, 782, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPaneMain)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jMenuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemExitActionPerformed
        System.exit(0);
    }//GEN-LAST:event_jMenuItemExitActionPerformed

    //==========================================================================
    private void jMenuItemEventViewerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemEventViewerActionPerformed
        callEventViewer();
    }//GEN-LAST:event_jMenuItemEventViewerActionPerformed

    //==========================================================================
    private void jMenuItemConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemConfigurationActionPerformed
        Configuration configuration = null;

        try {

            configuration = new Configuration(this, true);

            if (configuration.isVisible()) {
                configuration.setVisible(false);
            } else {
                configuration.setVisible(true);
            }

        } catch (Exception e) {
            notifications.error("error opening window", e);
        }
    }//GEN-LAST:event_jMenuItemConfigurationActionPerformed

    //==========================================================================
    private void jMenuItemIPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemIPActionPerformed
        new SearchIPAddress(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItemIPActionPerformed

    //==========================================================================
    private void jMenuItemSubnetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemSubnetActionPerformed
        new SearchSubnet(this, true).setVisible(true);
    }//GEN-LAST:event_jMenuItemSubnetActionPerformed

    //==========================================================================
    private void jMenuItemCloseAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemCloseAllActionPerformed
        Navigator.getInstance().closeAll();
    }//GEN-LAST:event_jMenuItemCloseAllActionPerformed

    //==========================================================================
    private void jMenuItemReportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemReportActionPerformed
        createPdf();
    }//GEN-LAST:event_jMenuItemReportActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemCloseAll;
    private javax.swing.JMenuItem jMenuItemConfiguration;
    private javax.swing.JMenuItem jMenuItemEventViewer;
    private javax.swing.JMenuItem jMenuItemExit;
    private javax.swing.JMenuItem jMenuItemIP;
    private javax.swing.JMenuItem jMenuItemReport;
    private javax.swing.JMenuItem jMenuItemSubnet;
    private javax.swing.JMenu jMenuSearch;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPaneMain;
    private javax.swing.JTabbedPane jTabbedPaneCollectors;
    private javax.swing.JTabbedPane jTabbedPaneView;
    // End of variables declaration//GEN-END:variables
}
