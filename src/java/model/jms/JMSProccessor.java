package model.jms;

import java.io.Serializable;
import java.net.InetAddress;
import javax.jms.*;
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

    //==========================================================================
    public JMSProccessor() throws Exception {
        super();

        this.topicSubscriber = getTopicSubscriber();
        this.messageProducer = getMessageProducer();
    } // end JMSProcessor

    //==========================================================================    
    public void send(String select, String sendTo, String tagTo, String type, String key, Object object) throws Exception {

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

            objectMessage = getTopicSession().createObjectMessage();
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

        if (topicSubscriber == null) {
            throw new NullPointerException("topicSubscriber is null or empty");
        }

        Message message = null;
        long end = 0;

        try {

            EventViewer.getInstance().appendInfoTextConsole("waiting for response ");
            end = System.currentTimeMillis() + time;

            while (end > System.currentTimeMillis()) {

                message = topicSubscriber.receive(time);

                if (message != null) {

                    // the message is for me ? 
                    if (!validateMessage(message, key)) {
                        System.out.println("the message is not for me " + message.getStringProperty("tagTo"));
                        continue;
                    }

                    if (message instanceof StreamMessage) {
                        return receiveStreamMessage((StreamMessage) message);
                    } else if (message instanceof BytesMessage) {
                        return receiveByteMessage((BytesMessage) message);
                    } else if (message instanceof ObjectMessage) {
                        return receiveObjectMessage((ObjectMessage) message);
                    }
                }

            } // end while

            //the time expired or message isn't from here
            return null;

        } catch (Exception e) {
            throw e;
        }

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

    //==========================================================================
    private boolean validateMessage(Message message, String key) throws Exception {

        if (message == null) {
            throw new NullPointerException("message is null");
        }

        if (key == null || key.length() < 1) {
            throw new NullPointerException("key is null");
        }

        boolean flag = false;
        String tagTo = null;

        try {

            tagTo = message.getStringProperty("tagTo");
            if (tagTo.equalsIgnoreCase("ssn") && key.equalsIgnoreCase(message.getStringProperty("key"))) {
                flag = true;
            }

        } catch (Exception e) {
            throw e;
        }

        return flag;

    } // end validateMessage

    //==========================================================================
    private Object receiveObjectMessage(ObjectMessage objectMessage) throws Exception {

        Object data = null;

        try {

            EventViewer.getInstance().appendInfoTextConsole("receiving message from " + objectMessage.getStringProperty("sendBy") + " " + objectMessage.getStringProperty("type") + " " + objectMessage.getStringProperty("select"));
            data = objectMessage.getObject();

        } catch (Exception e) {
            throw e;
        }

        return data;

    } // end receiveObjectMessage

    //==========================================================================
    private byte receiveByteMessage(BytesMessage bytesMessage) throws Exception {
        throw new UnsupportedOperationException();
    } // end receiveByteMessage

    //==========================================================================
    private byte receiveStreamMessage(StreamMessage streamMessage) throws Exception {
        throw new UnsupportedOperationException();
    } // end receiveStreamMessage
} // end class
