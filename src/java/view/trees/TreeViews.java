package view.trees;

import controllers.ControllerTrees;
import java.util.Enumeration;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;
import view.frames.MainFrame;
import view.notifications.Notifications;
import view.util.HighlightTreeCellRenderer;
import view.util.TreeUtilities;

/**
 *
 * @author skuarch
 */
public class TreeViews extends FactoryTree {

    private final HighlightTreeCellRenderer renderer = new HighlightTreeCellRenderer();
    private static TreeViews INSTANCE = null;
    private Notifications notifications = null;
    private DefaultMutableTreeNode rootNode = null;
    private DefaultTreeModel model = null;

    //==========================================================================
    private TreeViews() {

        initComponents();
        notifications = new Notifications();
        onLoad();

    } // end TreeViews

    //==========================================================================
    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TreeViews();
        }
    }

    //==========================================================================
    public synchronized static TreeViews getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //==========================================================================
    private synchronized void onLoad() {

        setName("views");
        
        Thread t = new Thread(new Runnable() {

            public void run() {

                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        try {
                            
                            rootNode = new ControllerTrees().getViewRootNode();
                            model = new DefaultTreeModel(rootNode);
                            jTreeViews.setCellRenderer(renderer);
                            jTreeViews.setModel(model);
                            addListeners();

                        } catch (Exception e) {
                            notifications.error("error creating tree views", e);
                        }
                    }
                });
            }
        });
        t.start();

    } // end onLoad

    //==========================================================================
    private void addListeners() {

        jTextFieldSearch.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                fireDocumentChangeEvent();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                fireDocumentChangeEvent();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
            }
        });

    }

    //==========================================================================
    @Override
    public String getSelected() {
        TreePath treePath = jTreeViews.getSelectionPath();
        if (treePath == null) {
            return null;
        }
        return TreeUtilities.getSelect(treePath);
    } // end getSelected

    //==========================================================================
    private void fireDocumentChangeEvent() {

        String q = jTextFieldSearch.getText();
        renderer.q = q;
        TreePath root = jTreeViews.getPathForRow(0);
        collapseAll(jTreeViews, root);
        if (!q.isEmpty()) {
            //searchTree(jTreeViews, root, q);
            searchTree();
        } else {
            //collapseAll(jTreeViews, root);
            collapseAll();
        }
        //tree.repaint();
    }

    //==========================================================================
    private void collapseAll(JTree tree, TreePath parent) {
        TreeNode node = (TreeNode) parent.getLastPathComponent();
        if (!node.isLeaf() && node.getChildCount() >= 0) {
            Enumeration e = node.children();
            while (e.hasMoreElements()) {
                TreeNode n = (TreeNode) e.nextElement();
                TreePath path = parent.pathByAddingChild(n);
                collapseAll(tree, path);
            }
        }
        tree.collapsePath(parent);
    }

    //==========================================================================
    private void searchTree() {

        DefaultMutableTreeNode node = searchNode(jTextFieldSearch.getText());
        if (node != null) {
            TreeNode[] nodes = model.getPathToRoot(node);
            TreePath path = new TreePath(nodes);
            jTreeViews.scrollPathToVisible(path);
            jTreeViews.setSelectionPath(path);
        }

    }

    //==========================================================================
    private DefaultMutableTreeNode searchNode(String nodeStr) {
        DefaultMutableTreeNode node = null;
        Enumeration e = rootNode.breadthFirstEnumeration();
        while (e.hasMoreElements()) {
            node = (DefaultMutableTreeNode) e.nextElement();
            //if (nodeStr.equalsIgnoreCase(node.getUserObject().toString())) {
            //if (node.getUserObject().toString().contains(nodeStr)) {
            if (node.getUserObject().toString().toLowerCase().contains(nodeStr.toLowerCase())) {
                return node;
            }
        }
        return null;
    }

    //==========================================================================
    private void collapseAll() {
        int row = jTreeViews.getRowCount() - 1;
        while (row >= 0) {
            jTreeViews.collapseRow(row);
            row--;
        }
    }

    //==========================================================================
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTreeViews = new javax.swing.JTree();
        jTextFieldSearch = new javax.swing.JTextField();

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        jTreeViews.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        jTreeViews.setDragEnabled(true);
        jTreeViews.setRootVisible(false);
        jTreeViews.setShowsRootHandles(false);
        jTreeViews.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTreeViewsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTreeViews);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    //==========================================================================
    private void jTreeViewsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTreeViewsMouseClicked
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                MainFrame.getInstance().control();
            }
        });
    }//GEN-LAST:event_jTreeViewsMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTree jTreeViews;
    // End of variables declaration//GEN-END:variables
} // end class
