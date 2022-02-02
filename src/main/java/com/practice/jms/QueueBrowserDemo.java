package com.practice.jms;

import java.util.Enumeration;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class QueueBrowserDemo {

	public static void main(String[] args) {

		InitialContext initialContext = null;
		Connection connection = null;

		try {
			initialContext = new InitialContext();
			ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
			connection = cf.createConnection();

			Session session = connection.createSession();
			Queue queue = (Queue) initialContext.lookup("queue/myQueue");

			MessageProducer producer = session.createProducer(queue);// Creating message Producer

			TextMessage message1 = session.createTextMessage("Message1");// Creating message using producer
			TextMessage message2 = session.createTextMessage("Message2");

			producer.send(message1);// sending message using producer
			producer.send(message2);

			QueueBrowser browser = (QueueBrowser) session.createBrowser(queue);
			Enumeration messageEnum = browser.getEnumeration();

			while (messageEnum.hasMoreElements()) {
				TextMessage nextMessage = (TextMessage) messageEnum.nextElement();
				System.out.println("Browsing : " + nextMessage.getText());
			}

//			System.out.println("Message1 sent : " + message1.getText());
//			System.out.println("Message1 sent : " + message2.getText());

			MessageConsumer consumer = session.createConsumer(queue);// creating message consumer
			connection.start();

			TextMessage messageReceive = (TextMessage) consumer.receive();
			System.out.println("Messgae Received: " + messageReceive.getText());

			messageReceive = (TextMessage) consumer.receive();
			System.out.println("Messgae Received: " + messageReceive.getText());

		} catch (NamingException e) {
			e.printStackTrace();
		} catch (JMSException e) {
			e.printStackTrace();
		} finally {
			if (initialContext != null) {
				try {
					initialContext.close();
				} catch (NamingException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
