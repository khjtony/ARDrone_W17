package playground;
import java.lang.*;
import java.util.LinkedList;
import java.util.Queue;


class Mywriter implements Runnable{
	Queue<String> q;
	public Mywriter(Queue<String> in_t){
		this.q=in_t;
	}
	
	public void run(){
		int count=5;
		while(count>0){
			q.add("Now is "+count);
			System.out.println(q.toString());
			count=count-1;
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

public class ThreadQueue {
	public static void main(String[] args){
		Queue<String> q = new LinkedList();
		q.add("A");
		System.out.println(q.toString());
		Thread mywriter = new Thread(new Mywriter(q));
		mywriter.start();
		try {
			mywriter.join(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(q.toString());
	}
}
