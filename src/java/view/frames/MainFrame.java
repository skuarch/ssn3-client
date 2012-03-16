package view.frames;

import controllers.ControllerConfiguration;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import view.helpers.ControlNavigators;
import view.notifications.Notifications;
import view.splits.Navigator;
import view.trees.TreeCollectors;
import view.trees.TreeViews;

/**
 *
 * @author skuarch
 */
public class MainFrame extends javax.swing.JFrame {

    private static MainFrame INSTANCE = null;

    //==========================================================================
    /**
     * Creates new form MainFrame
     */
    private MainFrame() {

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
    private void onLoad() {

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
            new Notifications().error("error loading interface", e);
        }

    } // end onLoad

    //==========================================================================
    public void control() {

        Thread t = new Thread(new ControlNavigators());
        t.start();
        t.setName("ControlNavigators");

    } // end control

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
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPaneMain.setDividerLocation(200);

        jSplitPane2.setDividerLocation(230);
        jSplitPane2.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane2.setTopComponent(jTabbedPaneView);
        jSplitPane2.setRightComponent(jTabbedPaneCollectors);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );

        jSplitPaneMain.setLeftComponent(jPanel1);

        jMenu1.setText("File");

        jMenuItem1.setText("exit");
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Tools");

        jMenuItem2.setText("event viewer");
        jMenu3.add(jMenuItem2);

        jMenuItem3.setText("configuration");
        jMenu3.add(jMenuItem3);

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
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JSplitPane jSplitPaneMain;
    private javax.swing.JTabbedPane jTabbedPaneCollectors;
    private javax.swing.JTabbedPane jTabbedPaneView;
    // End of variables declaration//GEN-END:variables
}
