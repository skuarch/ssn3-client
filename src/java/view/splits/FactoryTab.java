package view.splits;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author skuarch
 */
public abstract class FactoryTab extends JTabbedPane {

    //==========================================================================
    @Override
    public void addTab(String string, Component component) {        
        super.addTab(string, component);
    } // end addTab

    public abstract void closeTab(String tabName);

    public abstract Component getComponent(String name);

    public abstract void closeAll();

    public abstract JButton getCloseButton(String nameComponent);
    
    public abstract void destroy();
    
    //==========================================================================
    @Override
    public void updateUI() {

        new Thread(new Runnable() {

            public void run() {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {                        
                        FactoryTab.super.updateUI();
                    }
                });
            }
        }).start();


    } // end updateUI
    
} // end class

