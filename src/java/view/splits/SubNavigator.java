package view.splits;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import model.beans.SubPiece;
import view.frames.FloatFrame;
import view.dialogs.EventViewer;
import view.helpers.TabName;
import view.notifications.Notifications;
import view.panels.FactoryPanel;
import view.util.JTabPaneUtilities;

/**
 *
 * @author skuarch
 */
public class SubNavigator extends FactoryTab {

    private SubNavigator subNavigator = null;
    private Notifications notifications = null;
    private JTabbedPane jTabbedPaneSubNavigator = null;
    private String name = null; // name of this subnavigator
    private SubPiece subPiece = null;

    //==========================================================================
    public SubNavigator(SubPiece subPiece) {
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        this.subNavigator = (SubNavigator) this;
        this.notifications = new Notifications();
        this.jTabbedPaneSubNavigator = (JTabbedPane) this;
        this.subPiece = subPiece;
        this.name = subPiece.getJob();
        setName(name);
    } // end SubNavigator

    //==========================================================================
    @Override
    public void addTab(final String string, final Component component) {

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

                try {
                    String tabName = TabName.getTabName(subNavigator, string);
                    addTabComponent(tabName, component);
                } catch (Exception e) {
                    notifications.error("error in name of tab",e);
                }
                
            }
        });
        t.setName("addTabsubnavigator");
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
                        JButton buttonFloat = null;

                        try {

                            tabTitle = JTabPaneUtilities.getTabTitle(string, getCloseButton(string));
                            //floating tab
                            buttonFloat = new view.util.SwingUtilities().getFloatButton();
                            buttonFloat.addActionListener(new ActionListener() {

                                //==============================================================
                                public void actionPerformed(ActionEvent ae) {

                                    SwingUtilities.invokeLater(new Runnable() {

                                        public void run() {

                                            new FloatFrame(component).setVisible(true);

                                            if (subNavigator.getTabCount() < 1) {
                                                Navigator.getInstance().closeTab(name);
                                            }
                                        }
                                    });
                                }
                            });

                            tabTitle.add(buttonFloat);
                            component.setName(string);
                            SubNavigator.super.addTab(string, component);
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

    //==========================================================================
    @Override
    public void closeTab(final String tabName) {

        if (tabName == null || tabName.length() < 1) {
            notifications.error("name is null or empty", new NullPointerException());
            return;
        }

        FactoryPanel fp = null;
        Component component = null;

        try {

            EventViewer.getInstance().appendInfoTextConsole("closing tab job: " + tabName);
            component = getComponent(tabName);
            remove(component);

            fp = (FactoryPanel) component;
            fp.destroy();

        } catch (Exception e) {
            notifications.error("error closing tab job: " + tabName, e);
        } finally {
            component = null;
            fp = null;
        }

    }

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
    }

    //==========================================================================
    @Override
    public void closeAll() {
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                closeAllTabs();
                removeAll();
            }
        });
    }

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

                    try {

                        closeTab(nameComponent);

                        if (getTabCount() < 1) {
                            Navigator.getInstance().closeTab(subPiece.getJob());
                        }

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
    public int getComponentsSameName(String nameComponent) {

        if (nameComponent == null) {
            notifications.error("error the name of the component is null", new NullPointerException());
            return 0;
        }

        Component[] components = null;
        int nComponent = 0;
        String tmpName = null;

        try {

            components = this.jTabbedPaneSubNavigator.getComponents();

            for (int i = 0; i < components.length; i++) {

                tmpName = components[i].getName();

                if (tmpName == null) {
                    continue;
                }

                if (tmpName.contains(nameComponent)) {
                    nComponent++;
                }

                tmpName = null;
            }

        } catch (Exception e) {
            notifications.error("error finding names of components", e);
        } finally {
            components = null;
        }

        return nComponent;
    } // end getComponentSameName

    //==========================================================================
    private void closeAllTabs() {

        Component[] components = getComponents();
        FactoryPanel tmp = null;

        try {
            if (subNavigator.getTabCount() > 0) {
                for (int i = 0; i < components.length; i++) {
                    if (components[i] instanceof FactoryPanel) {
                        tmp = (FactoryPanel) (components[i]);
                        tmp.destroy();
                        tmp = null;
                    }

                    components[i] = null;
                }
            }
        } catch (Exception e) {
            notifications.error("error closing all tabs", e);
        } finally {
            components = null;
            tmp = null;
            removeAll();
        }

    } // end closeAllTabs

    //==========================================================================
    @Override
    public void destroy() {
        closeAllTabs();
    }

    //==========================================================================
    @Override
    protected void finalize() throws Throwable {

        try {
            destroy();
            subNavigator = null;
            notifications = null;
            jTabbedPaneSubNavigator = null;
            name = null; // name of this subnavigator
            //subPiece = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.finalize();
        }

    } // end finalize
} // end class

