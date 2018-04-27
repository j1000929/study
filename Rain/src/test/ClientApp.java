package test;

import client.RainClient;

public class ClientApp {

	public static void main(String[] args) {
		RainClient client = new RainClient(); // command창에 java test.clientApp 127.0.0.1 clientname 으로 실행시킨다.

		client.setServerIp("127.0.0.1");
		client.start();
	}

}
