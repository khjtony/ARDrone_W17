package com.pan_iac.ardrone.basic_server;

import java.lang.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class discover_client extends Thread {
	DatagramSocket c;
	
	public static void main(String[] args){
		final Thread t = new Thread(new discover_client());
		Runtime.getRuntime().addShutdownHook(new Thread() {
	         @Override
	         public void run() {
	        	 t.interrupt();
	        	 try{
	        		 t.join();
	        	 }catch(InterruptedException e){
	        		 System.out.println(this.getClass() + " has been interrupted.");
	        	 }
	            System.out.println("W: interrupt received, killing "+this.getClass());
	         }
	      });
		t.start();
		
	}
	
	@Override
	public void run(){
	// Find the server using UDP broadcast
	try {
	  //Open a random port to send the package
	  c = new DatagramSocket();
	  c.setBroadcast(true);

	  byte[] sendData = "DISCOVER_FUIFSERVER_REQUEST".getBytes();

	  //Try the 255.255.255.255 first
	  try {
	    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, InetAddress.getByName("255.255.255.255"), 8888);
	    c.send(sendPacket);
	    System.out.println(getClass().getName() + ">>> Request packet sent to: 255.255.255.255 (DEFAULT)");
	  } catch (Exception e) {
	  }

	  // Broadcast the message over all the network interfaces
	  Enumeration interfaces = NetworkInterface.getNetworkInterfaces();
	  while (interfaces.hasMoreElements()) {
	    NetworkInterface networkInterface = (NetworkInterface)interfaces.nextElement();
	    

	    if (networkInterface.isLoopback() || !networkInterface.isUp()) {
	      continue; // Don't want to broadcast to the loopback interface
	    }

	    for (InterfaceAddress interfaceAddress : networkInterface.getInterfaceAddresses()) {
	      InetAddress broadcast = interfaceAddress.getBroadcast();
	      if (broadcast == null) {
	        continue;
	      }

	      // Send the broadcast package!
	      try {
	        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, broadcast, 8888);
	        c.send(sendPacket);
	      } catch (Exception e) {
	      }

	      System.out.println(getClass().getName() + ">>> Request packet sent to: " + broadcast.getHostAddress() + "; Interface: " + networkInterface.getDisplayName());
	    }
	  }

	  System.out.println(getClass().getName() + ">>> Done looping over all network interfaces. Now waiting for a reply!");

	  //Wait for a response
	  byte[] recvBuf = new byte[15000];
	  DatagramPacket receivePacket = new DatagramPacket(recvBuf, recvBuf.length);
	  c.receive(receivePacket);

	  //We have a response
	  System.out.println(getClass().getName() + ">>> Broadcast response from server: " + receivePacket.getAddress().getHostAddress());

	  //Check if the message is correct
	  String message = new String(receivePacket.getData()).trim();
	  if (message.equals("DISCOVER_FUIFSERVER_RESPONSE")) {
	    //DO SOMETHING WITH THE SERVER'S IP (for example, store it in your controller)
	    System.out.println("Found new server with IP: " + receivePacket.getAddress());
	  }

	  //Close the port!
	  c.close();
	} catch (IOException ex) {
	  System.out.println("Something wrong happened in client");
	}
	}
}
