package com.pan_iac.ardrone.basic_server;
import java.lang.*;

import com.pan_iac.ardrone.basic_server.*;

public class discover_test {
	
	public static void main(String[] args){
		Thread myclient = new Thread(new discover_client());
		
		//discover_client myclient = new discover_client();
		
		myclient.start();
	}
}
