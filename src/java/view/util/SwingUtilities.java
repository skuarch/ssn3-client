package view.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.beans.SubPiece;
import view.dialogs.Detail;
import view.frames.MainFrame;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class SwingUtilities {

    //==========================================================================
    public static JButton getShutdownButton() {

        JButton button = null;

        try {

            button = new JButton();
            button.setIcon(new ImageIcon(SwingUtilities.class.getResource("/view/images/shutdown.png")));
            button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        } catch (Exception e) {
            new Notifications().error("error creating button shutdown", e);
        }

        return button;
    } // end getShutdownButton

    //==========================================================================
    public static JButton getCleanButton() {

        JButton button = null;

        try {

            button = new JButton();
            button.setIcon(new ImageIcon(SwingUtilities.class.getResource("/view/images/clear.png")));
            button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        } catch (Exception e) {
            new Notifications().error("error creating button clear", e);
        }

        return button;
    } // end getClearButton

    //==========================================================================
    public static JButton getOnTopButton() {

        JButton button = null;

        try {

            button = new JButton();
            button.setIcon(new ImageIcon(SwingUtilities.class.getResource("/view/images/onTop.png")));
            button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        } catch (Exception e) {
            new Notifications().error("error creating button on top", e);
        }

        return button;
    } // end getOnTopButton

    //==========================================================================
    public static JButton getMailButton() {

        JButton button = null;

        try {

            button = new JButton();
            button.setIcon(new ImageIcon(SwingUtilities.class.getResource("/view/images/forward.png")));
            button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        } catch (Exception e) {
            new Notifications().error("error creating button mail", e);
        }

        return button;
    } // end getMailButton

    //==========================================================================
    public static JButton getRefreshButton() {

        JButton button = null;

        try {

            button = new JButton();
            button.setIcon(new ImageIcon(SwingUtilities.class.getResource("/view/images/refresh.png")));
            button.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
            button.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        } catch (Exception e) {
            new Notifications().error("error creating button refresh", e);
        }

        return button;
    } // end getRefreshButton

    //==========================================================================
    public static JButton getFloatButton() {
        JButton buttonFloat = new JButton();
        buttonFloat.setPreferredSize(new Dimension(10, 10));
        return buttonFloat;
    } // end getFloatButton

    //==========================================================================
    public JButton getNextButton() {
        JButton next = new JButton();
        next.setPreferredSize(new Dimension(25, 25));
        next.setIcon(new ImageIcon(getClass().getResource("/view/images/next.png")));
        return next;
    } // end getNextButton

    //==========================================================================
    public JButton getBackButton() {
        JButton back = new JButton();
        back.setPreferredSize(new Dimension(25, 25));
        back.setIcon(new ImageIcon(getClass().getResource("/view/images/back.png")));
        return back;
    } // end getBackButton

    //==========================================================================
    public JButton getOpenButton() {
        JButton open = new JButton();
        open.setPreferredSize(new Dimension(18, 18));
        open.setIcon(new ImageIcon(getClass().getResource("/view/images/newWindow.png")));
        return open;
    } // end getOpenButton

    //==========================================================================
    public static JLabel getWhiteLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.BLUE);
        return label;
    } // end getWhiteLabel

    //==========================================================================
    public JButton getExportExcelButton() {
        JButton export = new JButton("");
        export.setToolTipText("export to excel");
        export.setIcon(new ImageIcon(getClass().getResource("/view/images/table-export.png")));
        return export;
    }

    //==========================================================================
    public JButton getTableButton() {
        JButton table = new JButton("");
        table.setToolTipText("show table");
        table.setIcon(new ImageIcon(getClass().getResource("/view/images/table.png")));
        return table;
    }

    //==========================================================================
    public JButton getDetailButton() {

        JButton detail = new JButton("");
        detail.setToolTipText("details");
        detail.setIcon(new ImageIcon(getClass().getResource("/view/images/detail.png")));

        return detail;
    }

    //==========================================================================
    public JButton getPdfButton() {

        JButton pdf = new JButton("");
        pdf.setToolTipText("pdf");
        pdf.setIcon(new ImageIcon(getClass().getResource("/view/images/pdf.png")));
        return pdf;
    } // end getPdfButton

    //==========================================================================
    public static JList getListLineChart() {
        JList list = new JList();
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(-1);
        list.setBackground(Color.WHITE);
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        return list;
    } // end getListVertical

    //==========================================================================
    public static JList getListBarChart() {
        JList list = new JList();
        list.setVisibleRowCount(-1);
        list.setBackground(Color.WHITE);
        return list;
    } // end getListHorinzontal
} // end class
