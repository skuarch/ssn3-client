package view.helpers;

/**
 *
 * @author skuarch
 */
public class Verifier {

    //==========================================================================
    public static boolean verifyView(String view) {

        boolean flag = false;

        if (view == null) {
            flag = false;
        } else if (view.equalsIgnoreCase("bandwidth")) {
            flag = false;
        } else if (view.equalsIgnoreCase("protocols")) {
            flag = false;
        } else if (view.equalsIgnoreCase("host")) {
            flag = false;
        } else if (view.equalsIgnoreCase("tos")) {
            flag = false;
        } else if (view.equalsIgnoreCase("conversations")) {
            flag = false;
        } else if (view.equalsIgnoreCase("web")) {
            flag = false;
        } else if (view.equalsIgnoreCase("QoS")) {
            flag = false;
        } else if (view.equalsIgnoreCase("Ports")) {
            flag = false;
        } else if (view.equalsIgnoreCase("Tools")) {
            flag = false;
        } else {
            flag = true;
        }

        return flag;

    } // end verifyView
} // end class

