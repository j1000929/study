package test;

import client.RainClient;

public class ClientApp {

	public static void main(String[] args) {
		RainClient client = new RainClient(); // commandâ�� java test.clientApp 127.0.0.1 clientname ���� �����Ų��.

		client.setServerIp("127.0.0.1");
		client.start();
	}

}
