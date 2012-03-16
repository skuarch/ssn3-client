package view.notifications;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author skuarch
 */
public class Alert extends JDialog {

    private JButton button = null;
    private JLabel label = null;

    //==========================================================================
    public Alert(JFrame frame, boolean isModal) {
        super(frame, isModal);
        setPreferredSize(new Dimension(500, 100));
        setLayout(new BorderLayout());
        button = new JButton("OK");
        label = new JLabel();
        setResizable(false);
        pack();
        setLocationRelativeTo(getRootPane());
        onLoad();
    } // end Alert

    //==========================================================================
    private void onLoad() {

        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                setVisible(false);
            }
        });

        //adding Label
        JPanel p0 = new JPanel();
        p0.add(label);
        add(p0, BorderLayout.CENTER);

        //adding button
        JPanel p1 = new JPanel();
        //p1.add(new JButton("SEND REPORT"));
        p1.add(button);
        add(p1, BorderLayout.PAGE_END);

    } // end onLoad

    //==========================================================================
    public void setLabel(String message) {
        label.setText(message);
    } // end setLabel
    
} // end class
