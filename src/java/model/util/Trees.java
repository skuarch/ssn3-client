package model.util;

import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.beans.Collectors;
import model.net.ConnectPool;

/**
 *
 * @author skuarch
 */
public class Trees {

    //==========================================================================
    public Trees() {
    } // end Trees

    //==========================================================================
    public DefaultMutableTreeNode getViewRootNode() throws Exception {

        ResultSet resultSetCategories = null;
        ResultSet resultSetSubCategories = null;
        DefaultMutableTreeNode rootNode = null;
        ConnectPool connectPool = null;
        DefaultMutableTreeNode categorie = null;

        try {

            connectPool = new ConnectPool();
            rootNode = new DefaultMutableTreeNode("Views");

            resultSetCategories = connectPool.executeQuery("select * from categories where categorie_active = 1 order by categorie_order asc");
            resultSetSubCategories = connectPool.executeQuery("select * from subcategories where subcategorie_active = 1 order by subcategorie_categorie_id,subcategorie_order  asc");

            while (resultSetCategories.next()) {
                
                categorie = new DefaultMutableTreeNode(resultSetCategories.getString("categorie_name"));
                rootNode.add(categorie);
                resultSetSubCategories.beforeFirst();

                while (resultSetSubCategories.next()) {
                    if (resultSetSubCategories.getInt("subcategorie_categorie_id") == resultSetCategories.getInt("categorie_id")) {
                        //subcategorie                        
                        categorie.add(new DefaultMutableTreeNode(resultSetSubCategories.getString("subcategorie_name")));
                    }
                } // while subcategories
                
            } // while categories            

        } catch (Exception e) {
            throw e;
        } finally {
            DatabaseUtilities.closeResultSet(resultSetCategories);
            DatabaseUtilities.closeResultSet(resultSetSubCategories);
            connectPool = null;
        }

        return rootNode;

    } // end getViewRootNode

    //==========================================================================
    public DefaultTreeModel getCollectorsModel() throws Exception {

        Collectors[] collectors = null;
        DefaultMutableTreeNode rootNode = null;
        DefaultTreeModel model = null;
        Thread[] threadCollectors = null;
        ArrayList<DefaultMutableTreeNode> arrayNodes = null;

        try {

            rootNode = new DefaultMutableTreeNode("collectors");
            model = new DefaultTreeModel(rootNode);
            collectors = new ModelCollectors().getActivesCollectors();

            if (collectors == null || collectors.length < 1) {
                rootNode = new DefaultMutableTreeNode("without collectors");
                model = new DefaultTreeModel(rootNode);
                return model;
            }

            threadCollectors = new Thread[collectors.length];
            arrayNodes = new ArrayList<DefaultMutableTreeNode>();

            //run each thread and save
            for (int i = 0; i < collectors.length; i++) {
                threadCollectors[i] = new ThreadNode(collectors[i].getHost(), arrayNodes);
                threadCollectors[i].start();
            }

            //check if all the threads ended
            for (int i = 0; i < threadCollectors.length; i++) {
                if (threadCollectors[i].isAlive()) {
                    threadCollectors[i].join();
                } else {
                    continue;
                }
            }

            //change node wait to servers
            rootNode = new DefaultMutableTreeNode("servers");
            model = new DefaultTreeModel(rootNode);

            //put nodes into the jtree
            for (int i = 0; i < arrayNodes.size(); i++) {
                model.insertNodeInto(arrayNodes.get(i), rootNode, i);
            }

        } catch (Exception e) {
            throw e;
        } finally {
            collectors = null;
        }

        return model;

    } // end getModel
} // end class
