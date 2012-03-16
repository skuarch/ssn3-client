package model.jms;

import java.io.Serializable;
import java.net.InetAddress;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.TopicSubscriber;
import model.util.KeyGenerator;
import model.util.SerializableObject;
import view.dialogs.EventViewer;

/**
 *
 * @author skuarch
 */
public class JMSProccessor extends JMS {

    private ObjectMessage objectMessage = null;
    private MessageProducer messageProducer = null;
    private TopicSubscriber topicSubscriber = null;
    private Message message = null;

    //==========================================================================
    public JMSProccessor() throws Exception {
        super();
        this.objectMessage = getObjectMessage();
        this.topicSubscriber = getTopicSubscriber();
        this.messageProducer = getMessageProducer();
    } // end JMSProcessor

    //==========================================================================    
    private void send(String select, String sendTo, String tagTo, String type, String key, Object object) throws Exception {

        if (select == null || select.length() < 1) {
            throw new NullPointerException("select is null or empty");
        }

        if (sendTo == null || sendTo.length() < 1) {
            throw new NullPointerException("sendTo is null or empty");
        }

        if (tagTo == null || tagTo.length() < 1) {
            throw new NullPointerException("tagTo is null or empty");
        }

        if (type == null || type.length() < 1) {
            throw new NullPointerException("type is null or empty");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null or empty");
        }

        String sendBy = null;
        String tagBy = null;

        try {

            sendBy = InetAddress.getLocalHost().getHostName();
            tagBy = "ssn";

            EventViewer.getInstance().appendInfoTextConsole("publishing message to " + sendTo + " " + type + " " + select);

            objectMessage.setStringProperty("select", select);
            objectMessage.setStringProperty("sendTo", sendTo);
            objectMessage.setStringProperty("sendBy", sendBy);
            objectMessage.setStringProperty("tagTo", tagTo);
            objectMessage.setStringProperty("tagBy", tagBy);
            objectMessage.setStringProperty("type", type);
            objectMessage.setStringProperty("key", key);
            objectMessage.setObject((Serializable) new SerializableObject().getSerializableObject(object));

            messageProducer.send(objectMessage);

        } catch (Exception e) {
            throw e;
        }

    } // end send

    //=========================================================================
    private Object receive(String key, int time) throws Exception {

        if (key == null || key.equalsIgnoreCase("")) {
            throw new NullPointerException("key is null or empty");
        }

        if (time <= 0) {
            throw new NullPointerException("time is 0 or less than 0");
        }

        Object data = null;
        String tagTo = null;
        long end = 0;

        try {

            EventViewer.getInstance().appendInfoTextConsole("waiting for response ");
            end = System.currentTimeMillis() + time;

            while (end > System.currentTimeMillis()) {

                message = topicSubscriber.receive(time);

                if (message != null) {

                    tagTo = message.getStringProperty("tagTo");

                    if (tagTo.equalsIgnoreCase("ssn") && key.equalsIgnoreCase(message.getStringProperty("key"))) {

                        //on error
                        if (message.getStringProperty("error") != null) {
                            throw new Exception("error recivieng data", new Exception(message.getStringProperty("error")));
                        }

                        EventViewer.getInstance().appendInfoTextConsole("receiving message from " + message.getStringProperty("sendBy") + " " + message.getStringProperty("type") + " " + message.getStringProperty("select"));
                        objectMessage = (ObjectMessage) message;
                        data = objectMessage.getObject();

                        break;

                    }

                    message = null;

                }

            } // end while

        } catch (Exception e) {
            throw e;
        }
        
        return data;

    } // end receive

    //=========================================================================
    public synchronized Object sendReceive(String select, String sendTo, String tagTo, int time, Object object) throws Exception {

        if (time <= 0) {
            throw new Exception("time is 0 or less than 0");
        }

        if (select == null || select.length() < 1) {
            throw new Exception("select is null or empty");
        }

        if (sendTo == null || sendTo.length() < 1) {
            throw new Exception("sendTo is null or empty");
        }

        if (tagTo == null || tagTo.length() < 1) {
            throw new Exception("tagTo is null or empty");
        }

        if (object == null) {
            throw new Exception("object is null");
        }

        String key = null;
        Object data = null;

        try {

            key = new KeyGenerator().generateKey();
            send(select, sendTo, tagTo, "request", key, object);
            data = receive(key, time);

        } catch (Exception e) {
            throw e;
        } finally {
            shutdownConnection();
        }

        return data;
    } // end sendReceive
} // end class
