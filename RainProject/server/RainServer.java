package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * 1. client으로 부터 접속을 받음
 *  - 2ports (chatting / file)
 * 
 * @author jitaek
 *
 */
public class RainServer {

	int chatPortNum;
	int filePortNum;

	Map<String, ChatRoom> chatRooms;

	Properties properties;

	public RainServer() {

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

		chatRooms = Collections.synchronizedMap(new HashMap<String, ChatRoom>());
	}

	// 각 포트별로 serversocket을 생성하고,
	// accept를 기다려야 함.
	public void start() {
		ServerSocket chatSS, fileSS;
		Socket chatSocket, fileSocket;
		int count = 0;
		try {
			chatSS = new ServerSocket(chatPortNum);
			fileSS = new ServerSocket(filePortNum);

			while (true) {
				chatSocket = chatSS.accept();
				fileSocket = fileSS.accept();

				User user = new User();
				user.setMsgSocket(chatSocket);
				user.setFileSocket(fileSocket);
				user.setIp_port(chatSocket.getInetAddress().toString()
						+ ":" + String.valueOf(chatSocket.getPort()));
				count++;
				System.out.println("[" + chatSocket.getInetAddress() + ":" + chatSocket.getPort() + "]");
				System.out.println("[" + fileSocket.getInetAddress() + ":" + fileSocket.getPort() + "]");
				System.out.println(count + " client가 접속중...");
				// 각 클라이언트 별로 socket를 가지고 처리하는 무엇?
				ServerReceiver receiver = new ServerReceiver(user, chatRooms);
				receiver.start();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
