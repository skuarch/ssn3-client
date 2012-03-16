package model.ssn;

import javax.annotation.Resource;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.sql.DataSource;
import javax.swing.JFrame;
import javax.swing.UIManager;
import view.dialogs.EventViewer;
import view.notifications.Notifications;
import view.splashscreen.SplashScreen;

/**
 *
 * @author skuarch
 */
public class Main {

    @Resource(mappedName = "jms_connection_factory_ssn")
    private static TopicConnectionFactory topicConnectionFactory;
    @Resource(mappedName = "jms_topic_ssn")
    private static Topic topic;
    @Resource(mappedName = "jdbc/ssn")
    private static DataSource dataSource;

    //==========================================================================
    public static DataSource getDataSource() {
        return dataSource;
    }

    //==========================================================================
    public static Topic getTopic() {
        return topic;
    }

    //==========================================================================
    public static TopicConnectionFactory getTopicConnectionFactory() {
        return topicConnectionFactory;
    }

    //==========================================================================
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        try {

            //setting theme
            JFrame.setDefaultLookAndFeelDecorated(true);

            for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(laf.getName())) {
                    UIManager.setLookAndFeel(laf.getClassName());
                }
            }

            EventViewer.getInstance().appendInfoTextConsole("** program start **");

            // for show message when the program finished
            Runtime.getRuntime().addShutdownHook(new Main().shutdownThread);

            new SplashScreen().showSplash();

            //setting look and feel
            //UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            new Notifications().error("error opening application", e);
        }
    } // end main
    //==========================================================================
    /**
     * This method displays a message before the program ends.
     */
    private Thread shutdownThread = new Thread() {

        //======================================================================
        @Override
        public void run() {
            //on exit
            EventViewer.getInstance().appendInfoTextConsole("** program finished **");
        } // end run
    }; //end shutdownThread
} // end class
