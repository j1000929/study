package test;

import server.RainServer;

public class ServerApp {

	public static void main(String[] args) {
		RainServer server = new RainServer();
		server.start();
	}

}
