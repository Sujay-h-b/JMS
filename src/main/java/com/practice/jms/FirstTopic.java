package com.practice.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class FirstTopic {

	public static void main(String[] args) throws Exception {

		InitialContext initialContext = new InitialContext();
		Topic topic = (Topic) initialContext.lookup("topic/myTopic");
		
		ConnectionFactory cf = (ConnectionFactory) initialContext.lookup("ConnectionFactory");
		Connection connection = cf.createConnection();
		
		Session session = connection.createSession();
		MessageProducer producer = session.createProducer(topic);
		
		MessageConsumer consumer1 = session.createConsumer(topic);
		MessageConsumer consumer2 = session.createConsumer(topic);
		
		TextMessage textMessage = session.createTextMessage("Hiiiiii.... Message sent by producer....");
		
		producer.send(textMessage);
		
		connection.start();
		
		TextMessage message1 = (TextMessage) consumer1.receive();
		System.out.println("Consumer1 messgae received : "+message1.getText());
		
		TextMessage message2 = (TextMessage) consumer2.receive(10000);
		System.out.println("Consumer2 messgae received : "+message2.getText());
		
		connection.close();
		initialContext.close();
	}

}
