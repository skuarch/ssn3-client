package model.mail;

import controllers.ControllerConfiguration;
import model.util.PropertieWrapper;

/**
 * this is a wrapper for sendMailGmail
 *
 * @author skuarch
 */
public class SendMail {

    //==========================================================================
    public SendMail() {
    }

    //==========================================================================
    public void sendEventViewer(String message) throws Exception {

        if (message == null) {
            throw new Exception("the message is null");
        }

        String from = null;
        String to = null;
        String subject = null;
        String gMailUser = null;
        String gMailPassword = null;

        try {

            from = PropertieWrapper.getStringValue("mail");
            to = PropertieWrapper.getStringValue("mail.to");
            subject = "event viewer " + new ControllerConfiguration().getInitialConfiguration().getProjectName();
            gMailUser = PropertieWrapper.getStringValue("gmail.user");
            gMailPassword = PropertieWrapper.getStringValue("gmail.password");

            new Thread(new MailGmail(from, to, subject, message, gMailUser, gMailPassword)).start();

        } catch (Exception e) {
            throw e;
        }

    }
} // end class
