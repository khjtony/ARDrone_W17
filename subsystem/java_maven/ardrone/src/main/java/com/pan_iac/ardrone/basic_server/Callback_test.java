package com.pan_iac.ardrone.basic_server;

class MyCallbackImpl implements MyCallback{
	public void sayHello(){
		System.out.println("Hello");
	}
}


class Callback_test_help{
	MyCallback cb;
	Callback_test_help(MyCallback cb){
		this.cb=cb;
		this.cb.sayHello();
	}
	
	public void run(){
		this.cb.sayHello();
	}
}


public class Callback_test {
	public static void main(String[] args){
		Callback_test_help haha= new Callback_test_help(new MyCallbackImpl());
		haha.run();
	}
}
