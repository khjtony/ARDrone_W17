package com.pan_iac.ardrone.basic_server;
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class basic_server {
	public static void main(String[] args){
		// create new addresss and port
		
		mySocket mysocket = new mySocket();
		
		Scanner sc=new Scanner(System.in);
		System.out.print("Hostname: ");
		String hostname=sc.nextLine();
		System.out.print("Port: ");
		int port = sc.nextInt();
		
		System.out.println("New connection setup: \nhostname:"+hostname+"\nPort: "+port);
		try{
			ServerSocket serverSocket = new ServerSocket(port);
			Socket clientSocket=serverSocket.accept();
			PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Server is listening");
			String userInput;
			while((userInput = in.readLine())!=null){
				out.println("Echo from Server: "+userInput);
				System.out.println("From client: "+userInput);
			}
		}catch(IOException e){
			System.err.println("Unkown exceptions");
		}
	}
}
