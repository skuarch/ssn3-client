package model.jms;

import javax.jms.*;
import model.ssn.Main;
import model.util.JMSUtilities;

/**
 *
 * @author skuarch
 */
public class JMS {

    private TopicConnectionFactory topicConnectionFactory = null;
    private Topic topic = null;
    private TopicSession topicSession = null;
    private TopicConnection topicConnection = null;
    private MessageProducer messageProducer = null;
    private TopicSubscriber topicSubscriber = null;    

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

            //for receive
            topicSubscriber = topicSession.createSubscriber(topic);

        } catch (NullPointerException npe) {
            System.out.println("imposible create connection JMS.initComponents() " + npe);
            npe.printStackTrace();            
        } catch (Exception e) {
            throw e;
        }

    } // end initComponents

    //==========================================================================
    public MessageProducer getMessageProducer() {
        return messageProducer;
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
        System.out.println("shutdown connection");
        JMSUtilities.closeTopicConnection(topicConnection);
        JMSUtilities.closeTopicSession(topicSession);
        JMSUtilities.closeTopicSubscriber(topicSubscriber);
        JMSUtilities.closeMessageProducer(messageProducer);        
    } // end shutDownConnection
} // end class

