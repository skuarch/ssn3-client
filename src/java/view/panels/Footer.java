package view.panels;

import com.sun.java.swing.plaf.motif.MotifBorders;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import model.beans.SubPiece;
import view.notifications.Notifications;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class Footer {

    private Notifications notifications = null;
    private SubPiece subPiece = null;
    private JButton exportExcelButton = null;
    private JButton pdfButton = null;
    private JButton detailsButton = null;
    private JButton tableButton = null;
    private SwingUtilities su = null;
    private JList list = null;

    //==========================================================================
    public Footer(SubPiece subPiece) {
        notifications = new Notifications();
        su = new SwingUtilities();
        this.exportExcelButton = su.getExportExcelButton();
        this.pdfButton = su.getPdfButton();
        this.detailsButton = su.getDetailButton();
        this.tableButton = su.getTableButton();
    } // end Footer2

    //==========================================================================
    public JPanel getFooterBarChart(JList list) {

        if (list == null) {
            notifications.error("error list is null", new NullPointerException("list is null"));
        }

        this.list = list;
        JPanel panel = null;
        JPanel panelButtons = null;
        JScrollPane scrollPane = null;

        try {

            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(new MotifBorders.BevelBorder(true, Color.LIGHT_GRAY, Color.black));

            panelButtons = new JPanel();
            panelButtons.add(exportExcelButton);
            panelButtons.add(pdfButton);
            panelButtons.add(tableButton);
            panelButtons.add(detailsButton);
            panel.add(panelButtons, BorderLayout.EAST);

            scrollPane = new JScrollPane(this.list);
            panel.add(scrollPane, BorderLayout.CENTER);

        } catch (Exception e) {
            notifications.error("imposible to create footer", e);
        }

        return panel;
    } // end getFooterBarChart      

    //==========================================================================
    public JPanel getFooterLineChart(JList list) {

        this.list = list;
        JPanel panel = null;
        JPanel panelButtons = null;

        try {

            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(new MotifBorders.BevelBorder(true, Color.LIGHT_GRAY, Color.black));

            panelButtons = new JPanel();
            panelButtons.add(exportExcelButton);
            panelButtons.add(pdfButton);
            panelButtons.add(tableButton);
            panelButtons.add(detailsButton);
            panel.add(panelButtons, BorderLayout.LINE_END);
            panel.add(this.list, BorderLayout.CENTER);

        } catch (Exception e) {
            notifications.error("imposible to create footer", e);
        }

        return panel;
    }

    //==========================================================================
    public JPanel getFooterTable() {

        JPanel panel = null;
        JPanel panelButtons = null;

        try {

            panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.setBorder(new MotifBorders.BevelBorder(true, Color.LIGHT_GRAY, Color.black));
            panelButtons = new JPanel();
            panelButtons.add(exportExcelButton);
            panelButtons.add(detailsButton);
            panel.add(panelButtons, BorderLayout.EAST);

        } catch (Exception e) {
            notifications.error("imposible to create footer", e);
        }

        return panel;
    }

    //==========================================================================
    public void setEnableList(boolean flag) {
        if (list != null) {
            this.list.setEnabled(flag);
        }
    }
    
    //==========================================================================
    public void showList(boolean flag){
        if (list != null) {
            this.list.setVisible(flag);
        }
    }

    //==========================================================================
    public void setEnableTableButton(boolean flag) {
        this.tableButton.setEnabled(flag);
    }

    //==========================================================================
    public void setEnableExportButton(boolean flag) {
        this.exportExcelButton.setEnabled(flag);
    }

    //==========================================================================
    public void addActionListenerExportExcel(ActionListener actionListener) {
        this.exportExcelButton.addActionListener(actionListener);
    }

    //==========================================================================
    public void addActionListenerPdfButton(ActionListener actionListener) {
        this.pdfButton.addActionListener(actionListener);
    }
    //==========================================================================

    public void addActionListenerTableButton(ActionListener actionListener) {
        this.tableButton.addActionListener(actionListener);
    }

    //==========================================================================
    public void addActionListenerDetailsButton(ActionListener actionListener) {
        this.detailsButton.addActionListener(actionListener);
    }

    //==========================================================================
    public JButton getDetailsButton() {
        return detailsButton;
    }

    //==========================================================================
    public void setDetailsButton(JButton detailsButton) {
        this.detailsButton = detailsButton;
    }

    //==========================================================================
    public JButton getExportExcelButton() {
        return exportExcelButton;
    }

    //==========================================================================
    public void setExportExcelButton(JButton exportExcelButton) {
        this.exportExcelButton = exportExcelButton;
    }

    //==========================================================================
    public JButton getPdfButton() {
        return pdfButton;
    }

    //==========================================================================
    public void setPdfButton(JButton pdfButton) {
        this.pdfButton = pdfButton;
    }

    //==========================================================================
    public JButton getTableButton() {
        return tableButton;
    }

    //==========================================================================
    public void setTableButton(JButton tableButton) {
        this.tableButton = tableButton;
    }
} // end class
