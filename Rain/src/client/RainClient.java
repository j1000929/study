package client;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;

public class RainClient {

	// 서버ip와 portNum이 필요
	String serverIp;
	int chatPortNum;
	int filePortNum;

	Properties properties;
	private Scanner sc;
	
	public RainClient() {//생성자
		
		properties = new Properties();
		
		try {
			properties.load(new FileInputStream("./src/common/port.prop"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.chatPortNum = Integer.parseInt(properties.getProperty("chat"));
		this.filePortNum = Integer.parseInt(properties.getProperty("file"));
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
			System.out.println("서버에 연결되었습니다.");

			// 각 소켓 별로 받고 보내는 쓰레드를 각각 만든다.
			Thread sender = new Thread(new ClientSender(chatSocket, fileSocket));
			Thread receiver = new Thread(new ClientReceiver(chatSocket, fileSocket));

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
