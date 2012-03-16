package view.notifications;

import view.dialogs.EventViewer;

/**
 *
 * @author skuarch
 */
public class Notifications {

    //==========================================================================
    public Notifications() {
    } // end Notifications

    //==========================================================================
    public void error(String userError, Exception e) {

        if (userError == null || userError.length() < 1) {
            return;
        }

        if (e == null) {
            return;
        }

        // in event viewer
        EventViewer.getInstance().appendErrorTextConsole(e.getLocalizedMessage() + " " + e.getStackTrace()[0].getMethodName() + "()" + " " + e.getStackTrace()[0].getClassName() + " Line:" + e.getStackTrace()[0].getLineNumber());
        
        //in console
        System.err.println("ERROR: " + e.getLocalizedMessage() + " " + e.getStackTrace()[0].getMethodName() + "()" + " " + e.getStackTrace()[0].getClassName() + " Line:" + e.getStackTrace()[0].getLineNumber());        
        e.printStackTrace();
        
        //show alert
        Alert alert = new Alert(null, true);
        alert.setTitle("ERROR");
        alert.setLabel(userError);
        alert.setVisible(true);        

    } // end error
} // end class
