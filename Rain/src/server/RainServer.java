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
 * 1.client으로 부터 접속을 받음
 * 	- 2ports (chatting / file)->채팅 port, file port
 * list(채팅방 리스트), create(방생성), join(방입장), exit(방퇴장), destory(방없애기), 
 * users(사용자리스트), set password(비번 바꾸기),call(초대)
 * */

public class RainServer {

	int chatPortNum;
	int filePortNum;

//	Map<String, ChatRoom> chatRooms;// 룸정보를 가지고 있는 맵!!very important
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

		// ChatRoom을 만든다.
//		ChatRoom chatRoom = new ChatRoom("_waiting_", "_admin_");
//		chatRooms = Collections.synchronizedMap(new HashMap<String, ChatRoom>());//
		rooms = new Rooms();
	}

	// 각 포트별로 serversocket을 생성하고,
	// accept를 기다려야함.
	public void start() {

		ServerSocket chatSS, fileSS = null;
		Socket chatSocket, fileSocket = null;

		int count = 0;

		try {
			chatSS = new ServerSocket(chatPortNum);
			fileSS = new ServerSocket(filePortNum);

			System.out.println("서버가 시작되었습니다.");

			while (true) {
				chatSocket = chatSS.accept();
				fileSocket = fileSS.accept();
				
				User user = new User();
				user.setMsgSocket(chatSocket);
				user.setFileSocket(fileSocket);
				user.setIp_port(chatSocket.getInetAddress().toString() + String.valueOf(chatSocket.getPort()));
				count++;

				System.out.println("[" + chatSocket.getInetAddress() + ":" + chatSocket.getPort() + "]" + "에서 접속");
				System.out.println("[" + fileSocket.getInetAddress() + ":" + fileSocket.getPort() + "]" + "에서 접속");
				System.out.println(count + " client가 접속중...");

				// 접속 유저를 대기방에 조인시킨다.

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
