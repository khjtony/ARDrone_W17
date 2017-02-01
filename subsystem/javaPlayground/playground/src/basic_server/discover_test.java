package basic_server;
import java.lang.*;
import basic_server.*;

public class discover_test {
	
	public static void main(String[] args){
		Thread myserver = new Thread(new discover_server());
		Thread myclient = new Thread(new discover_client());
		
		//discover_client myclient = new discover_client();
		
		myserver.start();
		myclient.start();
	}
}
