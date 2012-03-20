package view.dialogs;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import model.ssn.Main;
import model.util.DateUtilities;
import view.frames.MainFrame;
import view.notifications.Notifications;
import view.util.SwingUtilities;

/**
 *
 * @author skuarch
 */
public class EventViewer extends JDialog {

    private static EventViewer INSTANCE = null;
    private String textConsole = "";
    private Notifications notifications = null;
    private JScrollPane scrollTextArea = null;
    private JTextArea textArea = null;
    private JButton refreshButton = null;
    private JButton cleanButton = null;
    private JButton mailButton = null;
    private JButton onTopButton = null;
    private JButton shutdownButton = null;

    //==========================================================================
    private EventViewer(JFrame frame, boolean isModal) {
        super(frame, isModal);
        notifications = new Notifications();
        textArea = new JTextArea();
        scrollTextArea = new JScrollPane(textArea);
        refreshButton = SwingUtilities.getRefreshButton();
        cleanButton = SwingUtilities.getCleanButton();
        mailButton = SwingUtilities.getMailButton();
        onTopButton = SwingUtilities.getOnTopButton();
        shutdownButton = SwingUtilities.getShutdownButton();

        initComponents();
        setLocationRelativeTo(getRootPane());
    } // end EventViewer
//==========================================================================

    private synchronized static void createInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EventViewer(MainFrame.getInstance(), false);
        }
    }

    //==========================================================================
    public static EventViewer getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    //==========================================================================
    private void initComponents() {

        try {

            setTitle("Event viewer");
            setLayout(new BorderLayout());
            setPreferredSize(new Dimension(600, 500));
            JToolBar toolBar = new JToolBar();
            toolBar.add(refreshButton);
            toolBar.add(cleanButton);
            toolBar.add(mailButton);
            toolBar.add(onTopButton);
            toolBar.add(shutdownButton);
            toolBar.setFloatable(false);
            add(toolBar, BorderLayout.NORTH);

            //textArea             
            textArea.setEditable(false);
            add(scrollTextArea, BorderLayout.CENTER);

            pack();
            addListeners();

        } catch (Exception e) {
            notifications.error("error creating event viewer", e);
        }
    }

    //==========================================================================
    public void appendInfoTextConsole(String text) {
        try {
            System.out.println(DateUtilities.getCurrentDate() + " INFO: " + text);
            textArea.append(DateUtilities.getCurrentDate() + " INFO: " + text + "\n");
            textConsole += DateUtilities.getCurrentDate() + " INFO: " + text + "\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end appendInfoTextConsole

    //==========================================================================
    public void appendWarmTextConsole(String text) {
        try {
            System.out.println(DateUtilities.getCurrentDate() + " WARNING: " + text);
            textArea.append(DateUtilities.getCurrentDate() + " WARNING: " + text + "\n");
            textConsole += DateUtilities.getCurrentDate() + " WARNING: " + text + "\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end appendWarmTextConsole

    //==========================================================================
    public String getText() {
        return textConsole;
    } // end getText

    //==========================================================================
    public void appendErrorTextConsole(String text) {
        try {
            System.out.println(DateUtilities.getCurrentDate() + " ERROR: " + text);
            textArea.append(DateUtilities.getCurrentDate() + " ERROR: " + text + "\n");
            textConsole += DateUtilities.getCurrentDate() + " ERROR: " + text + "\n";
        } catch (Exception e) {
            e.printStackTrace();
        }
    } // end appendErrorTextConsole

    //==========================================================================
    private void addListeners() {

        Thread t = new Thread(new Runnable() {

            public void run() {

                javax.swing.SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        try {

                            refreshButton.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent ae) {
                                    textArea.setText("refreshing");
                                    textArea.setText(textConsole);
                                }
                            });

                            cleanButton.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent ae) {
                                    textConsole = "";
                                    textArea.setText(textConsole);
                                }
                            });

                            onTopButton.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent ae) {
                                    if (isAlwaysOnTop()) {
                                        setAlwaysOnTop(false);
                                    } else {
                                        setAlwaysOnTop(true);
                                    }
                                }
                            });


                            mailButton.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent ae) {
                                    SendConsoleReport.getInstance().setVisible(true);
                                }
                            });


                            shutdownButton.addActionListener(new ActionListener() {

                                public void actionPerformed(ActionEvent ae) {
                                    setVisible(false);
                                }
                            });

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
        t.setName("EventViewer.addListeners");
        t.start();

    } // end addListeners
} // end class
