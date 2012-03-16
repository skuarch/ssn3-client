package model.util;

import javax.jms.TopicConnection;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import view.notifications.Notifications;

/**
 *
 * @author skuarch
 */
public class JMSUtilities {

    //=========================================================================
    public static void closeTopicSession(TopicSession topicSession) {

        try {

            if (topicSession != null) {
                topicSession.close();
            }

        } catch (Exception e) {
            new Notifications().error("error closing topic session", e);
        } finally {
            topicSession = null;
        }

    } // end closeTopic

    //=========================================================================
    public static void closeTopicConnection(TopicConnection topicConnection) {

        try {

            if (topicConnection != null) {
                topicConnection.close();
            }

        } catch (Exception e) {
            new Notifications().error("error closing topic connection", e);
        } finally {
            topicConnection = null;
        }

    } // end closeTopicConnection

    //=========================================================================
    public static void closeTopicSubscriber(TopicSubscriber topicSubscriber) {

        try {

            if (topicSubscriber != null) {
                topicSubscriber.close();
            }

        } catch (Exception e) {
            new Notifications().error("error closing topic subscriber", e);
        } finally {
            topicSubscriber = null;
        }

    } // end closeTopicSubscriber
} // end class

