package model.util;

import controllers.ControllerJobs;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import view.dialogs.EventViewer;

/**
 *
 * @author skuarch
 */
public class ThreadNode extends Thread {

    private String server = null;
    private ArrayList<DefaultMutableTreeNode> arrayNodes = null;

    //==========================================================================
    public ThreadNode(String server, ArrayList<DefaultMutableTreeNode> arrayNodes) {
        this.server = server;
        this.arrayNodes = arrayNodes;
    } // end ThreadNode

    //==========================================================================
    @Override
    public void run() {

        if (server == null) {
            new Thrower().exception(new NullPointerException("server is null"));
            return;
        }

        DefaultMutableTreeNode node = null;
        boolean connectivity = false;
        DefaultMutableTreeNode noConnection = null;
        String[] jobs = null;

        try {

            connectivity = new Connectivity().requestConnectivity(server);

            synchronized (arrayNodes) {

                if (connectivity) {

                    //requesting jobs
                    jobs = new ControllerJobs().getJobs(server);
                    node = new DefaultMutableTreeNode(server);

                    if (jobs == null || jobs.length < 1) {
                        noConnection = new DefaultMutableTreeNode("without jobs");
                        node.insert(noConnection, 0);
                        arrayNodes.add(node);
                        return;
                    }

                    for (int i = 0; i < jobs.length; i++) {
                        node.insert(new DefaultMutableTreeNode(jobs[i]), i);
                    }

                    arrayNodes.add(node);

                } else {

                    //does not exist connectivity
                    EventViewer.getInstance().appendWarmTextConsole("no response from server " + server);
                    node = new DefaultMutableTreeNode(server);
                    noConnection = new DefaultMutableTreeNode("without connection");
                    node.insert(noConnection, 0);
                    arrayNodes.add(node);
                }

            }

        } catch (Exception e) {
            new Thrower().exception(e);
        }

    } // end run
} // end class

