package view.panels;

import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.beans.SubPiece;

/**
 *
 * @author skuarch
 */
public abstract class FactoryPanel extends JPanel implements DropTargetListener {

    //==========================================================================
    public abstract void setname();

    //==========================================================================
    public abstract Object getData();

    //==========================================================================
    public abstract SubPiece getSubPiece();

    //==========================================================================
    public abstract void destroy();

    //==========================================================================
    public abstract Class getclass();

    //==========================================================================
    public void dragEnter(DropTargetDragEvent dtde) {
    } // end dragEnter

    //==========================================================================
    public void dragOver(DropTargetDragEvent dtde) {
    } // end dragOver

    //==========================================================================
    public void dropActionChanged(DropTargetDragEvent dtde) {
    } // end dropActionChanged

    //==========================================================================
    public void dragExit(DropTargetEvent dte) {
    } // dragExit

    //==========================================================================
    public void drop(DropTargetDropEvent dtde) {
    } // end drop

    //==========================================================================
    @Override
    public void updateUI() {

        new Thread(new Runnable() {

            public void run() {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {                        
                        FactoryPanel.super.updateUI();
                    }
                });
            }
        }).start();


    } // end updateUI
} // end class

