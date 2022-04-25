package cn.hitoplife.DDEAmq.Amq;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import com.pretty_tools.dde.server.DDEServer;

//import cn.hitoplife.DDEAmq.DDE.SqDDEServer;
import cn.hitoplife.DDEAmq.config.SqConfig;

public class SqAmqListen {

    private static final String BROKER_URL = "tcp://192.168.137.11:61616";
    private static final Boolean NON_TRANSACTED = false;
    private static final long TIMEOUT = 20000;

    public void listen(DDEServer ddeServer,SqConfig num,String[] args) {
        String url = BROKER_URL;
        if (args.length > 0) {
            url = args[0].trim();
        }
        System.out.println("\nWaiting to receive messages... will timeout after " + TIMEOUT / 10 +"s");
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("admin", "password", url);
        Connection connection = null;
      //  SqDDEServer sqDDEServer = new SqDDEServer();
     //   sqDDEServer.DDE(args);
    //    final DDEServer server = new DDEServer("oo")
        try {

            connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(NON_TRANSACTED, Session.AUTO_ACKNOWLEDGE);
            Destination destination = session.createQueue("test-queue");
     ///       Destination destinationFoo = session.createQueue("test-queue-foo");
      ////      Destination destinationBar = session.createQueue("test-queue-bar");
      //      Destination destinationTopicFoo = session.createTopic("test-topic-foo");

            MessageConsumer consumer = session.createConsumer(destination);
      ////      MessageConsumer consumerFoo = session.createConsumer(destinationFoo);
      //      MessageConsumer consumerBar = session.createConsumer(destinationBar);
      //      MessageConsumer consumerTopicFoo = session.createConsumer(destinationTopicFoo);

            int i = 0;
            while (true) {
                Message message = consumer.receive(TIMEOUT);

                if (message != null) {
                    if (message instanceof TextMessage) {
                    	String s = ((TextMessage) message).getText();
                        num.setNum(s);
                        ddeServer.notifyClients("MyTopic", "MyAdvise");
                        System.out.println("Got " + i++ + ". message on test-queue: " + s);
                    }
                } else {
                 //  break;
                }
/*
                message = consumerFoo.receive(TIMEOUT);

                if (message != null) {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Got " + i++ + ". message on test-queue-foo: " + text);
                    }
                } else {
                    break;
                }

                message = consumerBar.receive(TIMEOUT);

                if (message != null) {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Got " + i++ + ". message on test-queue-bar: " + text);
                    }
                } else {
                    break;
                }

                message = consumerTopicFoo.receive(TIMEOUT);

                if (message != null) {
                    if (message instanceof TextMessage) {
                        String text = ((TextMessage) message).getText();
                        System.out.println("Got " + i++ + ". message on test-topic-bar: " + text);
                    }
                } else {
                    break;
                }
*/
            }

 //           consumer.close();
 //           session.close();

        } catch (Exception e) {
            System.out.println("Caught exception!");
        }
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    System.out.println("Could not close an open connection...");
                }
            }
        }
    }	
}

