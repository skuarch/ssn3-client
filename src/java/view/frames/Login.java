package view.frames;

import controllers.ControllerAuthentication;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import model.beans.User;
import model.beans.Users;
import model.dao.DAO;
import org.jdesktop.swingx.JXLoginPane;
import view.dialogs.EventViewer;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class Login extends JFrame {

    private Notifications notifications = null;
    private JXLoginPane jxlp = null;
    private JButton button = null;
    private JPanel panel = null;

    //==========================================================================
    public Login() {

        notifications = new Notifications();
        jxlp = new JXLoginPane();
        panel = new JPanel();
        button = new JButton("Login");
        onLoad();

    } // end Login

    //==========================================================================
    private void onLoad() {

        setTitle("Sispro Sniffer Network");
        setLayout(new BorderLayout());
        setResizable(false);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/view/images/ssnIcon.png")));
        add(jxlp, BorderLayout.CENTER);
        button.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent ae) {
                Thread t = new Thread(new Runnable() {

                    public void run() {

                        try {

                            button.setEnabled(false);
                            jxlp.setMessage("wait...");

                            if (!validateLogin()) {
                                return;
                            }

                            if (!checkPassword()) {
                                notifications.error("the password must be only letters", new Exception("the password must be only letters"));
                                return;
                            }

                            setVisible(false);
                            openMainFrame();

                        } catch (Exception e) {
                            notifications.error("error loging user", e);
                        } finally {
                            button.setEnabled(true);
                        }

                    }
                });
                t.start();
                t.setName("actionPerform");
            }
        });
        panel.add(button);
        add(panel, BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(getRootPane());
    } // end onLoad

    //==========================================================================
    private boolean checkPassword() {

        boolean flag = true;

        try {

            char password[] = jxlp.getPassword();

            for (int i = 0; i < password.length; i++) {
                char c = password[i];
                // Si no es letra o numero entonces no es valido
                if (!Character.isLetterOrDigit(c)) {
                    flag = false;
                    break;
                }
            }

        } catch (Exception e) {
            notifications.error("An error has occurred in the application", e);
        }

        return flag;

    } // end checkPassword

    //==========================================================================
    private boolean validateLogin() {

        boolean flag = false;
        String user = null;
        String password = null;

        try {

            user = jxlp.getUserName();
            password = new String(jxlp.getPassword());

            if (user == null || user.length() < 1) {
                jxlp.setMessage("user name is empty");
                return false;
            }


            if (password == null || password.length() < 1) {
                jxlp.setMessage("password is empty");
                return false;
            }

            flag = new ControllerAuthentication().login(user, password);

            if (!flag) {
                jxlp.setMessage("user or password are incorrect");
                return false;
            }

        } catch (Exception e) {
            notifications.error("error while autenticate the user", e);
        }

        createUser(user);
        return flag;

    } // end validateLogin

    //==========================================================================
    private void createUser(String userName) {

        if (userName == null || userName.length() < 1) {
            throw new NullPointerException("userName is null");
        }

        User user = null;
        Users usrs = null;

        try {

            user = User.getInstance();
            usrs = (Users) new DAO().hsql("from Users where user_name = '" + userName + "'").get(0);
            user.setLevel(usrs.getLevel());
            user.setName(usrs.getName());

        } catch (Exception e) {
            notifications.error("error creating user", e);
        }

    }

    //==========================================================================
    private void openMainFrame() {

        Thread t = new Thread(new Runnable() {

            public void run() {
                SwingUtilities.invokeLater(new Runnable() {

                    public void run() {
                        EventViewer.getInstance().appendInfoTextConsole("opening main frame");
                        setName("openMainFrame");
                        MainFrame.getInstance().setVisible(true);
                    }
                });
            }
        });
        t.start();
    }
} // end class
