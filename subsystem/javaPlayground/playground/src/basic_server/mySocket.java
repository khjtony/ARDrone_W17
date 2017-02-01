package basic_server;
import java.util.Scanner;

public class mySocket {
	private String hostname;
	private int port;
	
	mySocket(){
		this.hostname="localhost";
		this.port=9337;
	}
	
	mySocket(String host, int port){
		this.hostname=host;
		this.port=port;
	}
	
	public boolean init(){
		// create new addresss and port
		try{
			Scanner sc=new Scanner(System.in);
			System.out.print("Hostname: ");
			this.hostname=sc.nextLine();
			System.out.print("Port: ");
			this.port = sc.nextInt();
			System.out.println("New connection setup: \nhostname:"+hostname+"\nPort: "+port);
		}catch(Exception e){
			System.out.println("Something wrong.");
			return false;
		}
		return true;
	}
}
