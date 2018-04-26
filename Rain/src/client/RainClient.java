package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class RainClient {

	// 서버ip와 portNum이 필요
	String serverIp;
	int chatPortNum = 7777;// 채팅용
	int filePortNum = 7888;// 파일용
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

			// 각 소켓 별로 받고 보내는 쓰레드를 각각 만든다.
			Thread sender = new ClientSender(clientName, chatSocket.getOutputStream(), fileSocket.getOutputStream());
			Thread receiver = new ClientReceiver(clientName, chatSocket.getInputStream(), fileSocket.getInputStream());

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
