package model.util;

import controllers.ControllerConfiguration;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import model.beans.Collectors;
import model.beans.SubPiece;
import model.jms.JMSProccessor;
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

            // run each thread and save
            for (int i = 0; i < collectors.length; i++) {
                threadCollectors[i] = new ThreadNode(collectors[i].getHost(), arrayNodes);
                threadCollectors[i].start();
            }

            // check if all the threads ended
            for (int i = 0; i < threadCollectors.length; i++) {
                if (threadCollectors[i].isAlive()) {
                    threadCollectors[i].join();
                } else {
                    continue;
                }
            }

            // change node wait to servers
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

    //==========================================================================
    public DefaultMutableTreeNode getRootNodeCollectorThreshold() throws Exception {

        Collectors[] collectors = null;

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("wait...");
        DefaultMutableTreeNode node = null;

        try {

            collectors = new ModelThresholdsCaptures().getCollectorsThresholdsCaptures();

            // change node wait to collectors
            rootNode = new DefaultMutableTreeNode("collectors");

            if (collectors.length < 1) {
                rootNode = new DefaultMutableTreeNode("no collectors");
                return rootNode;
            }

            //put nodes into the model
            for (int i = 0; i < collectors.length; i++) {
                node = new DefaultMutableTreeNode(collectors[i].getHost());
                rootNode.add(node);
            }

        } catch (Exception e) {
            throw e;
        }

        return rootNode;

    } // end getCollectorsThresholds   

    //==========================================================================
    public DefaultMutableTreeNode getRootNodeDaysThreshold(String collector) throws Exception {

        String[] days = null;
        SubPiece subPiece = new SubPiece();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("wait...");
        DefaultMutableTreeNode node = null;
        int time = 0;

        try {

            time = new ControllerConfiguration().getInitialConfiguration().getJmsTimeWaitConnectivity();
            subPiece.setView("threshold day");
            days = (String[]) new JMSProccessor().sendReceive("threshold day", collector, "srs", time, new PieceUtilities().subPieceToHashMap(subPiece));


            // change node wait to collectors
            rootNode = new DefaultMutableTreeNode("days");

            if (days == null || days.length < 1) {
                rootNode = new DefaultMutableTreeNode("no days");
                return rootNode;
            }

            //put nodes into the model
            for (int i = 0; i < days.length; i++) {
                node = new DefaultMutableTreeNode(days[i]);
                rootNode.add(node);
            }

        } catch (Exception e) {
            throw e;
        }

        return rootNode;

    } // end getCollectorsThresholds   
    
    
    //==========================================================================
    public DefaultMutableTreeNode getRootNodeCapturesThreshold(String collector, String day) throws Exception {

        String[] captures = null;
        SubPiece subPiece = new SubPiece();
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("wait...");
        DefaultMutableTreeNode node = null;
        int time = 0;

        try {

            time = new ControllerConfiguration().getInitialConfiguration().getJmsTimeWaitConnectivity();
            subPiece.setView("threshold captures");
            //setear threshold days
            captures = (String[]) new JMSProccessor().sendReceive("threshold captures", collector, "srs", time, new PieceUtilities().subPieceToHashMap(subPiece));


            // change node wait to collectors
            rootNode = new DefaultMutableTreeNode("captures");

            if (captures == null || captures.length < 1) {
                rootNode = new DefaultMutableTreeNode("no captures");
                return rootNode;
            }

            //put nodes into the model
            for (int i = 0; i < captures.length; i++) {
                node = new DefaultMutableTreeNode(captures[i]);
                rootNode.add(node);
            }

        } catch (Exception e) {
            throw e;
        }

        return rootNode;

    } // end getCollectorsThresholds   
} // end class

