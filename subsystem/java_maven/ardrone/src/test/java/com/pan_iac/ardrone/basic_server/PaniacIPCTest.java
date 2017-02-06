package com.pan_iac.ardrone.basic_server;

import static org.junit.Assert.*;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

import org.junit.*;

import org.junit.Before;
import org.junit.Test;

public class PaniacIPCTest {
	Map<String, String> msg = new HashMap();
	PaniacIPC ipc = new PaniacIPC();
	@Before
	public void setUp() throws Exception {

	}

	@Test
	public void testPush() {
		msg.put("NAME", "HELLO");
		ipc.push(msg);
	}

	@Test
	public void testGet() {
		testPush();
		msg=ipc.get();
		assertEquals("HELLO", msg.get("NAME"));
	}

}
