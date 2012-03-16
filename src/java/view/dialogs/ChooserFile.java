package view.dialogs;

import javax.swing.JFileChooser;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class ChooserFile {

    private Notifications notifications = null;

    //=========================================================================
    public ChooserFile() {
        notifications = new Notifications();
    }

    //=========================================================================
    public String getPath() {

        JFileChooser chooser = null;
        String path = null;

        try {

            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("choose location for save files");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(true);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                path = chooser.getSelectedFile().toString();
                //System.out.println("getCurrentDirectory(): " + chooser.getSelectedFile());
                //System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
            }

        } catch (Exception e) {
            notifications.error("error choosing file", e);
        }

        return path;
    } // end getPath
} // end class
