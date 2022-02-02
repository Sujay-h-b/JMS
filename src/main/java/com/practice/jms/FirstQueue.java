package com.practice.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstQueue {

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
			TextMessage message = session.createTextMessage("I am the creator of my destiny");// Creating message using producer
			producer.send(message);// sending message using producer
			System.out.println("Message sent : " + message.getText());

			MessageConsumer consumer = session.createConsumer(queue);// creating message consumer
			connection.start();
			TextMessage messageReceive = (TextMessage) consumer.receive();
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
