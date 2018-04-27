package test;

import client.RainClient;

public class ClientApp {

	public static void main(String[] args) {
		RainClient client = new RainClient(args[1]);

		client.setServerIp(args[0]);
		client.start();

	}

}
