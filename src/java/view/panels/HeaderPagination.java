package view.panels;

import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import view.notifications.Notifications;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class HeaderPagination {

    private Notifications notifications = null;
    private SwingUtilities su = null;
    private JButton nextButton = null;
    private JButton backButton = null;
    private JLabel label = null;

    //==========================================================================
    public HeaderPagination() {
        notifications = new Notifications();
        su = new SwingUtilities();
        backButton = su.getBackButton();
        label = new JLabel();
        nextButton = su.getNextButton();
    } // end HeaderPagination            

    //==========================================================================
    public JPanel getHeaderBarChart() {

        JPanel panel = null;

        try {

            panel = new JPanel();
            panel.setBackground(Color.WHITE);
            panel.add(backButton);
            panel.add(label);
            panel.add(nextButton);

        } catch (Exception e) {
            notifications.error("error creating header", e);
        }
        return panel;
    }

    //==========================================================================
    public void enableAllComponents(boolean flag) {
        backButton.setEnabled(flag);
        label.setEnabled(flag);
        nextButton.setEnabled(flag);
    }

    //==========================================================================
    public void enableBackButton(boolean flag) {
        backButton.setEnabled(flag);
    }

    //==========================================================================
    public void enableNextButton(boolean flag) {
        nextButton.setEnabled(flag);
    }

    //==========================================================================
    public void setLabelText(String text) {
        label.setText(text);
    }

    //==========================================================================
    public void addActionListenerBackButton(ActionListener actionListener) {
        backButton.addActionListener(actionListener);
    }

    //==========================================================================
    public void addActionListenerNextButton(ActionListener actionListener) {        
        nextButton.addActionListener(actionListener);
    }

    //==========================================================================
    public JButton getBackButton() {
        return backButton;
    }

    //==========================================================================
    public void setBackButton(JButton backButton) {
        this.backButton = backButton;
    }

    //==========================================================================
    public JLabel getLabel() {
        return label;
    }

    //==========================================================================
    public void setLabel(JLabel label) {
        this.label = label;
    }

    //==========================================================================
    public JButton getNextButton() {
        return nextButton;
    }

    //==========================================================================
    public void setNextButton(JButton nextButton) {
        this.nextButton = nextButton;
    }
} // end class
