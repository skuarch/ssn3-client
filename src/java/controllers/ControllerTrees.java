package controllers;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.util.Trees;

/**
 *
 * @author skuarch
 */
public class ControllerTrees {

    //==========================================================================
    public ControllerTrees(){

    } // end ControllerTrees
    
    //==========================================================================
    public DefaultMutableTreeNode getViewRootNode() throws Exception{
        return new Trees().getViewRootNode();
    }
    
    //==========================================================================
    public DefaultTreeModel getCollectorModel() throws Exception{
        return new Trees().getCollectorsModel();
    }
    
    //==========================================================================
    public DefaultMutableTreeNode getRootNodeCollectors() throws Exception{
        return new Trees().rootNodeCollectors();
    }

} // end class
