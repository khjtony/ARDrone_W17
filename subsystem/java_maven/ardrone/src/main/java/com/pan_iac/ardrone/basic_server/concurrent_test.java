package com.pan_iac.ardrone.basic_server;

import java.util.*;
import java.util.concurrent.TimeoutException;
import java.lang.*;

import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class concurrent_test {
	// this class will simulate a MQ, and several threads work
	// together to deal with the input messages
	private final static String QUEUE_NAME = "hello";
	//flow chart:
	// message generator -> 
	public static void main(String[] args){
		Channel channel;
		Connection connection;
		int count=10;
		try{
			ConnectionFactory factory = new ConnectionFactory();
		    factory.setHost("localhost");
		    connection = factory.newConnection();
		    channel = connection.createChannel();
			while(count>0){
				count--;
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			    String message = "Hello World!";
			    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			    System.out.println(" ["+String.valueOf(10-count)+"] Sent '" + message + "'");
				Thread.sleep(1000);
			}
			channel.close();
		    connection.close();
		}catch(Exception e){
			System.out.println("Error");
			return;
		}

	}
}
