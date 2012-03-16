package view.util;

import javax.swing.tree.TreePath;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class TreeUtilities {    

    //==========================================================================
    public static String getSecondFromTree(TreePath treePath) {

        String rtn = null;
        int length = 0;

        try {

            rtn = treePath.toString();
            rtn = rtn.replace("[", "");
            rtn = rtn.replace("]", "");
            length = rtn.split(",").length;


            if (rtn.contains(",")) {

                //tree root
                if (length == 1) {
                    rtn = rtn.split(",")[0];
                }

                //tree second level
                if (length == 2) {
                    rtn = rtn.split(",")[1];
                }

                if (length == 3) {
                    rtn = rtn.split(",")[1];
                }

            }

            //without space
            if (rtn.startsWith(" ")) {
                rtn = rtn.replaceFirst(" ", "");
            }

        } catch (Exception e) {
            new Notifications().error("selecting the server", e);
        } finally {
            length = 0;
            treePath = null;
        }

        if (rtn == null) {
            new Notifications().error("returning null", new Exception());
        }

        return rtn;

    } //  end getSecondFromTree

    //==========================================================================
    public static String getSelect(TreePath treePath) {

        if (treePath == null) {
            return null;
        }

        Object object = null;
        String select = null;

        try {

            object = treePath.getLastPathComponent();
            if (object == null) {
                return null;
            }

            select = object.toString();

        } catch (Exception e) {
            new Notifications().error("error getting selection", e);
        }

        return select;
    }
} // end class

