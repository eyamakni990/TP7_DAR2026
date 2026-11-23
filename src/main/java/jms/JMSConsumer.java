package jms;
import javax.jms.Connection; import javax.jms.ConnectionFactory; import javax.jms.Destination;
import javax.jms.JMSException; import javax.jms.Message; import javax.jms.MessageConsumer;
import javax.jms.MessageListener; import javax.jms.Session; import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;
public class JMSConsumer {
    public static void main(String[] args) throws Exception {
// Create a ConnectionFactory
        ConnectionFactory connectionFactory = new
                ActiveMQConnectionFactory("tcp://192.168.43.8:61616");
// Create a Connection
        Connection connection = connectionFactory.createConnection();
//connection.setExceptionListener(this);
// Create a Session
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
// Create the destination (Topic or Queue)
//Destination destination = session.createQueue("DQueue");
        Destination destination = session.createTopic("DTopic");
// Create a MessageConsumer from the Session to the Topic or Queue
        MessageConsumer consumer = session.createConsumer(destination);
// Start Connection
        connection.start();
// Create JMS Listener form messages
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("Received: " + text);
                    } else {
                        System.out.println("Received: " + message);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}