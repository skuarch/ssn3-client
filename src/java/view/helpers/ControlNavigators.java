package view.helpers;

import java.awt.Component;
import javax.swing.SwingUtilities;
import model.beans.SubPiece;
import view.notifications.Notifications;
import view.splits.Navigator;
import view.splits.SubNavigator;
import view.trees.TreeCollectors;
import view.trees.TreeViews;

/**
 *
 * @author skuarch
 */
public class ControlNavigators implements Runnable {

    private Notifications notifications = null;

    //==========================================================================
    public ControlNavigators() {
        notifications = new Notifications();
    } // end ControlNavigators

    //==========================================================================
    public void run() {

        Thread t = new Thread(new Runnable() {

            public void run() {
                try {

                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {

                            Component component = null;
                            SubNavigator subNavigator = null;
                            int numComponentsSameName = 0;
                            String job = null;
                            String view = null;
                            String collector = null;
                            SubPiece subPiece = null;

                            try {

                                view = TreeViews.getInstance().getSelected();
                                job = TreeCollectors.getInstance().getSelected();
                                collector = TreeCollectors.getInstance().getCollector();

                                if (view == null || view.equalsIgnoreCase("not applicable")) {
                                    return;
                                }

                                if (!Verifier.verifyView(view)) {
                                    return;
                                }

                                if (job == null || job.equalsIgnoreCase("not applicable") || !job.contains("job") || job.equalsIgnoreCase("without collectors") || job.equalsIgnoreCase("without jobs") || job.equalsIgnoreCase("without connection") || job.equalsIgnoreCase("servers") || job.equalsIgnoreCase("collectors")) {
                                    return;
                                }

                                if (collector == null || collector.equalsIgnoreCase("not applicable") || collector.equalsIgnoreCase("wait") || collector.equalsIgnoreCase("collectors")) {
                                    return;
                                }

                                subPiece = new SubPiece();
                                subPiece.setView(view);
                                subPiece.setCollector(collector);
                                subPiece.setJob(job);

                                subNavigator = (SubNavigator) Navigator.getInstance().getComponent(job);

                                if (subNavigator != null) {

                                    //subnavigator already exits !!
                                    component = new HandlerPanels().getComponent(subPiece);
                                
                                    //name of tab
                                    component.setName(TabName.getTabName(subNavigator, view));

                                    subNavigator.addTab(component.getName(), component);
                                    Navigator.getInstance().addTab(job, subNavigator);

                                } else {

                                    subNavigator = new SubNavigator(subPiece);
                                    component = new HandlerPanels().getComponent(subPiece);
                                    subNavigator.addTab(view, component);
                                    Navigator.getInstance().addTab(job, subNavigator);

                                }

                            } catch (Exception e) {
                                notifications.error("An error has occurred in the application", e);
                            }
                        }
                    });

                } catch (Exception e) {
                    notifications.error("error creating panel", e);
                }
            }
        });
        t.start();
        t.setName("ControlNavigators");

    } // end run
    
} // end class

