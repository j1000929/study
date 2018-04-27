package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RainClient {

	String serverIp;
	int chatPortNum = 7777;
	int filePortNum = 7888;
	String clientName;

	public RainClient(String clientName) {
		this.clientName = clientName;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public void start() {
		Socket chatSocket = null;
		Socket fileSocket = null;

		try {
			chatSocket = new Socket(serverIp, chatPortNum);
			fileSocket = new Socket(serverIp, filePortNum);

			Thread sender = new ClientSender(clientName, chatSocket.getOutputStream(),
					fileSocket.getOutputStream());
			Thread receiver = new ClientReceiver(clientName, chatSocket.getInputStream(),
					fileSocket.getInputStream());

			sender.start();
			receiver.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

		}
	}
}
