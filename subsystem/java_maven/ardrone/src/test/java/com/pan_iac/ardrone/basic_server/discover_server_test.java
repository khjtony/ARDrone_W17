package com.pan_iac.ardrone.basic_server;
import com.pan_iac.ardrone.basic_server.*;
import java.lang.*;
import java.util.Map;
import java.util.*;

public class discover_server_test {
	public static void main(String[] args){
		System.out.println("Start");
		PaniacIPC ipc = new PaniacIPC();
		Thread myserver = new Thread(new discover_server(ipc, "DISCOVER_SERVER"));
	
		try{
			myserver.start();
			while(true){
				Thread.sleep(1000);
				while (!ipc.isEmpty()){
					Map<String, String> status=ipc.get();
					System.out.println(status.toString());
					if (Integer.parseInt(status.get("REQUEST_COUNT"))>=2){
						// its time to stop myserver
						System.out.println("Interrup myserver!");
						myserver.interrupt();
					}
				}
			}
		}catch(Exception e){
			System.out.println("Unexpected error happened");
			myserver.interrupt();
		}
	}
}
