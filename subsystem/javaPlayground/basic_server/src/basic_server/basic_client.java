package basic_server;
import java.net.*;
import java.io.*;
import java.util.*;

public class basic_client {
	public static void main(String[] args){
		// create new addresss and port
		Scanner sc=new Scanner(System.in);
		System.out.print("Hostname: ");
		String hostname=sc.nextLine();
		System.out.print("Port: ");
		int port = sc.nextInt();
		
		try{
			Socket echoSocket = new Socket(hostname, port);
			PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
			BufferedReader stdIn=new BufferedReader(new InputStreamReader(System.in));
			System.out.println("Client is starting");
			String userInput;
			while((userInput = stdIn.readLine())!=null){
				out.println(userInput);
				System.out.println("Echo: "+userInput);
			}
		}catch(IOException e){
			System.err.println("Unkown exceptions");
		}
	}
}
