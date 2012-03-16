package view.util;

//import java.awt.Color;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

/**
 *
 * @author skuarch
 */
public class HighlightTreeCellRenderer extends DefaultTreeCellRenderer {

    //private static final Color rollOverRowColor = new Color(220, 240, 255);
    public String q;

    @Override
    public void updateUI() {
        setTextSelectionColor(null);
        setTextNonSelectionColor(null);
        setBackgroundSelectionColor(null);
        setBackgroundNonSelectionColor(null);
        super.updateUI();
    }

    //==========================================================================
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row, boolean hasFocus) {

        JComponent c = (JComponent) super.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, hasFocus);

        /*try {
        
        //DefaultMutableTreeNode node = (DefaultMutableTreeNode)value;
        if (isSelected) {
        c.setOpaque(false);
        c.setForeground(getTextSelectionColor());
        //c.setBackground(Color.BLUE); //getBackgroundSelectionColor());
        } else {
        c.setOpaque(true);
        if (q != null && !q.isEmpty() && value.toString().startsWith(q)) {
        c.setForeground(getTextNonSelectionColor());
        c.setBackground(rollOverRowColor);
        } else {
        c.setForeground(getTextNonSelectionColor());
        c.setBackground(getBackgroundNonSelectionColor());
        }
        }
        
        } catch (Exception e) {
        e.printStackTrace();
        }*/


        /*JTree.DropLocation dropLocation = tree.getDropLocation();
        if (dropLocation != null
                && dropLocation.getChildIndex() == -1
                && tree.getRowForPath(dropLocation.getPath()) == row) {
            // this row represents the current drop location
            // so render it specially, perhaps with a different color
        }*/

        return c;
    }
} // end HighlightTreeCellRenderer
