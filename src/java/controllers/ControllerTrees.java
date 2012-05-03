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
    public DefaultMutableTreeNode getRootNodeCollectorThreshold() throws Exception{
        return new Trees().getRootNodeCollectorThreshold();
    }
    
    //==========================================================================
    public DefaultMutableTreeNode getRootNodeDaysThreshold(String collector) throws Exception{
        return new Trees().getRootNodeDaysThreshold(collector);
    }
    
    
    //==========================================================================
    public DefaultMutableTreeNode getRootNodeCapturesThreshold(String collector, String day) throws Exception{
        return new Trees().getRootNodeCapturesThreshold(collector,day);
    }

} // end class
