package view.util;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class JTabPaneUtilities {

    //==========================================================================
    public static JPanel getTabTitle(String title, JButton closeButton) {

        if (title == null || title.equalsIgnoreCase("")) {
            new Notifications().error("title is empty or null", new Exception());
        }

        JPanel tabTitle = null;
        JLabel label = null;

        try {

            tabTitle = new JPanel();
            tabTitle.setName(title);
            tabTitle.setOpaque(false);

            //close button
            tabTitle.add(closeButton);

            //adding label
            label = new JLabel(title);
            tabTitle.add(label);

        } catch (Exception e) {
            new Notifications().error("imposible add tab", e);
        }

        return tabTitle;
    } // end getTabTitle

    //==========================================================================    
    public static boolean findName(String name, JTabbedPane jTabbedPane) {

        boolean flag = false;
        Component[] components;
        String nameComponent = null;

        try {

            components = jTabbedPane.getComponents();

            if (components == null) {
                return false;
            }

            //finding one by one
            for (int i = 0; i < components.length; i++) {

                nameComponent = components[i].getName();

                // I need the name for to make the search
                if (nameComponent == null) {
                    continue;
                }

                // find SubNavigator one by one
                if (components[i].getName().equalsIgnoreCase(name)) {
                    flag = true;
                    break;
                }
            }

        } catch (Exception e) {
            new Notifications().error("problems finding tab " + nameComponent, e);
        } finally {
            components = null;
            nameComponent = null;
            name = null;
        }

        return flag;

    } // end findTabName

    //=========================================================================    
    public static synchronized int getNumComponent(String nameComponent, JTabbedPane jTabbedPane) {

        if (nameComponent == null) {
            new Notifications().error("the name of the component is null", new Exception());
        }

        Component[] components = null;
        int index = 0;

        try {

            if (!findName(nameComponent, jTabbedPane)) {
                new Notifications().error("the  " + nameComponent + " not found", new Exception());
                return -1;
            }

            components = jTabbedPane.getComponents();

            if (components.length == 0) {
                return 0;
            }

            for (int i = 0; i < components.length; i++) {
                if (nameComponent.equalsIgnoreCase(components[i].getName())) {
                    break;
                }
                components[i].invalidate();
                components[i] = null;
                //index++;
                index = i;
            }

            index -= 1;

            if (index < 0) {
                index = 0;
            }


        } catch (Exception e) {
            new Notifications().error("getting the number of component", e);
        } finally {
            components = null;
        }

        return index - 1;
    } //end getNumOfComponent

    //==========================================================================    
    public static String[] getTabsNames(JTabbedPane jTabbedPane) {

        String[] names = null;
        Component[] components = null;
        StringBuffer stringBuffer = null;

        try {

            components = jTabbedPane.getComponents();

            if (components.length < 1) {
                return null;
            }

            names = new String[components.length];
            stringBuffer = new StringBuffer();

            for (int i = 0; i < components.length; i++) {

                if (components[i].getName() == null || components[i].getName().length() < 1 || components[i].getName().equalsIgnoreCase("TabbedPane.scrollableViewport")) {
                    continue;
                } else {
                    stringBuffer.append(components[i].getName());
                    stringBuffer.append(",");
                }

            }

            names = stringBuffer.toString().split(",");

        } catch (Exception e) {
            new Notifications().error("error while getting the names of tabs", e);
        } finally {
            stringBuffer = null;
        }

        if (names == null) {
            new Notifications().error("the names of components are null", new Exception());
        }

        return names;
    } // end getTabsNames
} // end class

