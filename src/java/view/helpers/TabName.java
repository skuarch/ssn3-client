package view.helpers;

import view.splits.SubNavigator;

/**
 *
 * @author skuarch
 */
public class TabName {

    //==========================================================================
    public static String getTabName(SubNavigator subNavigator, String name) throws Exception {

        if (subNavigator == null) {
            throw new NullPointerException("subNavigator is null");
        }

        String tabName = null;
        int numComponentsSameName = 0;

        try {
            numComponentsSameName = subNavigator.getComponentsSameName(name);

            if (numComponentsSameName > 0) {
                tabName = name + " " + numComponentsSameName;
            } else {
                tabName = name;
            }

        } catch (Exception e) {
            throw e;
        }

        return tabName;
    }
} // end class
