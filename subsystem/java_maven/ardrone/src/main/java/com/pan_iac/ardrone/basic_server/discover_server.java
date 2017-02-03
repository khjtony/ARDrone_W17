package com.pan_iac.ardrone.basic_server;

import java.io.IOException;
import java.util.Date;
import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.*;

public class discover_server implements Runnable{
	DatagramSocket socket;
	private volatile boolean running_flag=true;
	private Map<String, String> status = new HashMap();
	private long request_count=0;
	PaniacIPC ipc;
	private String myname="";
	
	discover_server(PaniacIPC ipc,String name){
		this.ipc=ipc;
		this.myname=name;
		updateStatus();
	}
	
	private void updateStatus(){
		status.put("NAME", myname);
		status.put("TYPE", this.getClass().toString());
		status.put("TIMESTAMP", String.valueOf(System.currentTimeMillis()));
		status.put("REQUEST_COUNT", String.valueOf(request_count));
		ipc.push(status);
	}
	
	public Map<String, String> getStatus(){
		return status;
	}
	
	@Override
	  public void run() {
		System.out.println("DEBUGGING");
	    try {
	      //Keep a socket open to listen to all the UDP trafic that is destined for this port
	      socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
	      socket.setBroadcast(true);
	      socket.setSoTimeout(500);

	      while (running_flag && !Thread.currentThread().interrupted()) {
	    	updateStatus();
	        System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");
	        
	        //Receive a packet
	        byte[] recvBuf = new byte[15000];
	        DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
	        try{
	        	socket.receive(packet);
	        }catch(IOException ex){
	        	continue;
	        }

	        //Packet received
	        System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
	        System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

	        //See if the packet holds the right command (message)
	        String message = new String(packet.getData()).trim();
	        if (message.equals("DISCOVER_FUIFSERVER_REQUEST")) {
	        	request_count++;
	          byte[] sendData = "DISCOVER_FUIFSERVER_RESPONSE".getBytes();

	          //Send a response
	          DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
	          socket.send(sendPacket);

	          System.out.println(getClass().getName() + ">>> "+ request_count+ " Sent packet to: " + sendPacket.getAddress().getHostAddress());
	        }
	      }
	    }catch(Exception ex){
	    	System.out.println("Unkown error");
	    }
    	socket.close();
    	return;
	  }
	
	public void terminate(){
		running_flag = false;
	}
}
 
