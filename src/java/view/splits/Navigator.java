package view.splits;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.UnexpectedException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import model.beans.SubPiece;
import view.dialogs.EventViewer;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.util.JTabPaneUtilities;

/**
 *
 * @author skuarch
 */
public class Navigator extends FactoryTab {

    private static Navigator INSTANCE = null;
    private Notifications notifications = null;

    //==========================================================================
    private Navigator() {
        notifications = new Notifications();
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
    } // end Navigator    

    //==========================================================================
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Navigator();
        }
    }

    //==========================================================================
    public static Navigator getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //==========================================================================
    @Override
    public synchronized void addTab(final String string, final Component component) {

        if (component == null) {
            notifications.error("tabContent is null", new Exception());
            return;
        }

        if (string == null || string.length() < 1) {
            notifications.error("tabContent doesn't have name", new Exception());
            return;
        }

        if (component.getName() == null || component.getName().length() < 1) {
            notifications.error("component doesn't have name", new Exception());
            return;
        }

        Thread t = new Thread(new Runnable() {

            public void run() {

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {

                        addTabComponent(string, component);

                    }
                });
            }
        });
        t.setName("addTabnavigator");
        t.start();
    } // end addTab

    //==========================================================================    
    private synchronized void addTabComponent(final String string, final Component component) {

        if (component == null) {
            notifications.error("tabContent is null", new Exception());
            return;
        }

        if (string == null || string.length() < 1) {
            notifications.error("tabContent hasn't name", new Exception());
            return;
        }

        if (component.getName() == null || component.getName().length() < 1) {
            notifications.error("component doesn't have name", new Exception());
            return;
        }

        Thread t = new Thread(new Runnable() {

            public void run() {

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        JPanel tabTitle = null;

                        try {

                            tabTitle = JTabPaneUtilities.getTabTitle(string, getCloseButton(string));

                            component.setName(string);
                            Navigator.super.addTab(string, component);
                            setTabComponentAt(getTabCount() - 1, tabTitle);
                            setSelectedIndex(getTabCount() - 1);

                        } catch (Exception e) {
                            notifications.error("error adding tab", e);
                        }
                    }
                });
            }
        });
        t.start();
        t.setName("addTabComponent");

    } // end addTabComponent

    //=========================================================================
    public synchronized void addTabInSubNavigator(FactoryPanel newTab) {

        if (newTab == null) {
            notifications.error("newTab is null", new NullPointerException());
            return;
        }

        if (newTab.getName() == null || newTab.getName().length() < 1) {
            notifications.error("error newTab doesn't have name", new UnexpectedException("error newTab doesn't have name"));
            return;
        }

        SubPiece subPiece = null;
        boolean foundSubNavigator = false;
        int numSubnavigator = 0;
        SubNavigator sub = null;
        String nameSubnavigator = null;

        try {

            subPiece = newTab.getSubPiece();
            nameSubnavigator = subPiece.getJob();
            foundSubNavigator = JTabPaneUtilities.findName(nameSubnavigator, this);

            if (foundSubNavigator) {

                numSubnavigator = JTabPaneUtilities.getNumComponent(nameSubnavigator, this);
                sub = (SubNavigator) getComponentAt(numSubnavigator);
                sub.addTab(newTab.getName(), newTab);

            } else {

                sub = new SubNavigator(subPiece);

                if (subPiece.getDrillDown().equalsIgnoreCase("not applicable")) {
                    sub.addTab(subPiece.getView(), newTab);
                } else {
                    sub.addTab(subPiece.getDrillDown(), newTab);
                }

                addTab(nameSubnavigator, sub);
            }

        } catch (Exception e) {
            notifications.error("error adding tab", e);
        }

    } // end addTabInSubNavigator    
    
    //==========================================================================
    @Override
    public JButton getCloseButton(final String nameComponent) {

        JButton button = null;

        try {

            button = new JButton("");
            button.setPreferredSize(new Dimension(10, 10));
            button.setToolTipText("close this tab");

            button.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent ae) {

                    SubNavigator subNavigator = null;

                    try {

                        //removing subnavigator

                        //getting subNavigator for close all your tabs
                        subNavigator = (SubNavigator) getComponent(nameComponent);
                        subNavigator.closeAll();

                        //closing SubNavigator
                        closeTab(nameComponent);

                    } catch (Exception e) {
                        notifications.error("imposible close this tab", e);
                    } finally {
                        subNavigator = null;
                    }

                }
            });

        } catch (Exception e) {
            notifications.error("imposible close this tab", e);
        }

        return button;

    } // end getCloseButton            

    //==========================================================================
    @Override
    public void closeTab(final String tabName) {

        if (tabName == null || tabName.length() < 1) {
            notifications.error("tabName is null or empty", new NullPointerException());
            return;
        }

        SubNavigator sub = null;
        Component component = null;

        try {

            EventViewer.getInstance().appendInfoTextConsole("closing tab job: " + tabName);
            component = getComponent(tabName);
            remove(component);
            sub = (SubNavigator) component;
            sub.destroy();

        } catch (Exception e) {
            notifications.error("error closing tab job: " + tabName, e);
        } finally {
            component = null;
            sub = null;
        }

    } // end closeTab    

    //==========================================================================
    public void closeSubnavigator(String nameSubNavigator) {
        EventViewer.getInstance().appendInfoTextConsole("closing tab job: " + nameSubNavigator);
        //remove(getNumOfComponent(nameSubNavigator));        
        remove(JTabPaneUtilities.getNumComponent(nameSubNavigator, this));
    } // end closeSubnavigator

    //==========================================================================
    @Override
    public Component getComponent(String name) {

        if (name == null || name.length() < 1) {
            notifications.error("name is null or empty", new NullPointerException());
            return null;
        }

        Component component = null;
        boolean found = false;
        int num = 0;

        try {

            found = JTabPaneUtilities.findName(name, this);

            if (found) {
                num = JTabPaneUtilities.getNumComponent(name, this);
                component = getComponentAt(num);
            } else {
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
    @Override
    public void closeAll() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                removeAll();
            }
        });
    } // end closeAll

    //==========================================================================
    @Override
    public void destroy() {
    } // end destroy
} // end class
