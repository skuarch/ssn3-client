package model.jms;

import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import model.util.JMSUtilities;
import model.ssn.Main;

/**
 *
 * @author skuarch
 */
public class JMS {

    //private Notifications notifications = null;
    private TopicConnectionFactory topicConnectionFactory = null;
    private Topic topic = null;
    private TopicSession topicSession = null;
    private TopicConnection topicConnection = null;
    private MessageProducer messageProducer = null;
    private TopicSubscriber topicSubscriber = null;
    private ObjectMessage objectMessage = null;

    //==========================================================================
    public JMS() throws Exception {

        this.topicConnectionFactory = Main.getTopicConnectionFactory();
        this.topic = Main.getTopic();

        initComponents();

    } // end JMS()

    //==========================================================================
    private void initComponents() throws Exception {

        try {

            topicConnection = topicConnectionFactory.createTopicConnection();
            topicConnection.start();
            topicSession = topicConnection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);

            //for send
            messageProducer = topicSession.createProducer(topic);
            objectMessage = topicSession.createObjectMessage();

            //for receive
            topicSubscriber = topicSession.createSubscriber(topic);

        } catch (Exception e) {
            throw e;
        }

    } // end initComponents

    //==========================================================================
    public MessageProducer getMessageProducer() {
        return messageProducer;
    }

    //==========================================================================
    public ObjectMessage getObjectMessage() {
        return objectMessage;
    }

    //==========================================================================
    public Topic getTopic() {
        return topic;
    }

    //==========================================================================
    public TopicConnection getTopicConnection() {
        return topicConnection;
    }

    //==========================================================================
    public TopicConnectionFactory getTopicConnectionFactory() {
        return topicConnectionFactory;
    }

    //==========================================================================
    public TopicSession getTopicSession() {
        return topicSession;
    }

    //==========================================================================
    public TopicSubscriber getTopicSubscriber() {
        return topicSubscriber;
    }

    //=========================================================================
    public void shutdownConnection() {
        JMSUtilities.closeTopicConnection(topicConnection);
        JMSUtilities.closeTopicSession(topicSession);
        JMSUtilities.closeTopicSubscriber(topicSubscriber);
    } // end shutDownConnection
} // end class

