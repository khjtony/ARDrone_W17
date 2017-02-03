package com.pan_iac.ardrone.basic_server;

import java.util.*;

public class PaniacIPC {
	    boolean lock = false;
	    private static Deque<Map<String, String>> pool= new ArrayDeque();

	    public synchronized void push(Map<String, String> msg) {
	        if (lock) {
	            try {
	                wait();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        lock=true;
	        pool.push(msg);
	        System.out.println("PaniacIPC: Now i have "+pool.size()+" inside");
	        lock = false;
	        notify();
	    }

	    public synchronized Map<String, String> get() {
	        if (lock) {
	            try {
	                wait();
	            } catch (InterruptedException e) {
	                e.printStackTrace();
	            }
	        }
	        lock = true;
	        notify();
	        lock = false;
	        return pool.removeLast();
	    }
	    
	    public boolean isEmpty(){
	    	return pool.isEmpty();
	    }
	}

