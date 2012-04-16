package model.mail;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import model.util.Thrower;

/**
 *
 * @author skuarch
 */
public class MailGmail implements Runnable {

    private Thrower thrower = null;
    private String from;
    private String to;
    private String subject;
    private String message;
    private String gmailUser;
    private String gmailPassword;

    //=========================================================================
    /**
     * Construct.
     *
     * @param from String
     * @param to String
     * @param subject String
     * @param message String
     * @param gmailUser String
     * @param gmailPassword String
     */
    public MailGmail(String from, String to, String subject, String message, String gmailUser, String gmailPassword) {
        super();
        thrower = new Thrower();
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.message = message;
        this.gmailUser = gmailUser;
        this.gmailPassword = gmailPassword;
    } //end MailSmtp

    //=========================================================================
    @Override
    public void run() {

        if (from == null || to == null || subject == null || message == null) {
            thrower.nullPointerException("some parameters are null or empty");
            return;
        }

        Properties props = null;
        Session session = null;
        MimeMessage msg = null;
        Transport t = null;

        try {

            props = new Properties();
            props.setProperty("mail.smtp.host", "smtp.gmail.com");
            props.setProperty("mail.smtp.starttls.enable", "true");
            props.setProperty("mail.smtp.port", "587");
            props.setProperty("mail.smtp.user", this.gmailUser);
            props.setProperty("mail.smtp.auth", "true");

            session = Session.getDefaultInstance(props);

            msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(this.from));
            msg.addRecipient(
                    Message.RecipientType.TO,
                    new InternetAddress(this.to));
            msg.setSubject(this.subject);
            msg.setText(this.message);

            t = session.getTransport("smtp");
            t.connect(this.gmailUser, this.gmailPassword);
            t.sendMessage(msg, msg.getAllRecipients());

            t.close();            

        } catch (Exception e) {
            thrower.exception(e);
        } finally {
            t = null;
            props = null;
            session = null;
            msg = null;
        }

    } //end run
} //end class

