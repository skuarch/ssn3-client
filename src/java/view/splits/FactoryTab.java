package view.splits;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

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
} // end class

