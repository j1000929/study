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

/*
 * 1.client���� ���� ������ ����
 * 	- 2ports (chatting / file)->ä�� port, file port
 * list(ä�ù� ����Ʈ), create(�����), join(������), exit(������), destory(����ֱ�), 
 * users(����ڸ���Ʈ), set password(��� �ٲٱ�),call(�ʴ�)
 * */

public class RainServer {

	int chatPortNum;
	int filePortNum;

//	Map<String, ChatRoom> chatRooms;// �������� ������ �ִ� ��!!very important
	Rooms rooms;
	
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

		// ChatRoom�� �����.
//		ChatRoom chatRoom = new ChatRoom("_waiting_", "_admin_");
//		chatRooms = Collections.synchronizedMap(new HashMap<String, ChatRoom>());//
		rooms = new Rooms();
	}

	// �� ��Ʈ���� serversocket�� �����ϰ�,
	// accept�� ��ٷ�����.
	public void start() {

		ServerSocket chatSS, fileSS = null;
		Socket chatSocket, fileSocket = null;

		int count = 0;

		try {
			chatSS = new ServerSocket(chatPortNum);
			fileSS = new ServerSocket(filePortNum);

			System.out.println("������ ���۵Ǿ����ϴ�.");

			while (true) {
				chatSocket = chatSS.accept();
				fileSocket = fileSS.accept();
				
				User user = new User();
				user.setMsgSocket(chatSocket);
				user.setFileSocket(fileSocket);
				user.setIp_port(chatSocket.getInetAddress().toString() + String.valueOf(chatSocket.getPort()));
				count++;

				System.out.println("[" + chatSocket.getInetAddress() + ":" + chatSocket.getPort() + "]" + "���� ����");
				System.out.println("[" + fileSocket.getInetAddress() + ":" + fileSocket.getPort() + "]" + "���� ����");
				System.out.println(count + " client�� ������...");

				// ���� ������ ���濡 ���ν�Ų��.

				//chatRooms.get("_waiting_").addMember(user);

				ServerReceiver receiver = new ServerReceiver(user, rooms);
				receiver.start();

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
