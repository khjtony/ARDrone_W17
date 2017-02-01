package com.pan_iac.ardrone.basic_server;

import java.io.IOException;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class discover_server implements Runnable{
	DatagramSocket socket;
	
	public static void main(String[] args){
		final Thread t = new Thread(new discover_server());
		Runtime.getRuntime().addShutdownHook(new Thread() {
	         @Override
	         public void run() {
	        	 t.interrupt();
	        	 try{
	        		 t.join();
	        	 }catch(InterruptedException e){
	        		 System.out.println(this.getClass() + " has been interrupted.");
	        	 }
	            System.out.println("W: interrupt received, killing" + this.getClass());
	         }
	      });
		t.start();
	}
	
	
	
	  @Override
	  public void run() {
	    try {
	      //Keep a socket open to listen to all the UDP trafic that is destined for this port
	      socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
	      socket.setBroadcast(true);

	      while (true) {
	        System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
	        
	        //Receive a packet
	        byte[] recvBuf = new byte[15000];
	        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
	        socket.receive(packet);

	        //Packet received
	        System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
	        System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

	        //See if the packet holds the right command (message)
	        String message = new String(packet.getData()).trim();
	        if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
	          byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

	          //Send a response
	          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
	          socket.send(sendPacket);

	          System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
	        }
	      }
	    } catch (IOException ex) {
	      System.out.println("Something wrong in server happened");
	    }
	  }
}
