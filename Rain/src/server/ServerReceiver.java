package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import common.ConsoleColor;

public class ServerReceiver extends Thread {

	User user;
	DataInputStream msgInput;
	BufferedInputStream fileInput;
	ChatRoom currentRoom;// 현재 유저가 위치한 룸

	Map<String, ChatRoom> chatRooms;

	public ServerReceiver(User user, Map<String, ChatRoom> chatRooms) {
		this.user = user;
		this.chatRooms = chatRooms;
		currentRoom = chatRooms.get("_waiting_");

		try {
			msgInput = new DataInputStream(user.getMsgSocket().getInputStream());
			fileInput = new BufferedInputStream(user.getFileSocket().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		try {
			user.setName(msgInput.readUTF());// 유저의 이름

			String msg;

			while (msgInput != null) {
				msg = msgInput.readUTF();
				System.out.println(ConsoleColor.ANSI_CYAN + msg + ConsoleColor.ANSI_RESET);

				msg = msg.substring(msg.indexOf("]") + 1);
				if (msg.startsWith("/")) {// msg가 커맨드인지...
					processCmd(msg.substring(1));// 1에서 부터 끝까지~
				} else {// message
					// 현재 방에 있는 유저들에게 메세지 전송
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {//
			try {
				msgInput.close();
				fileInput.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 방생성하는 함수

	boolean processCmd(String cmd) {
		String[] tokens = cmd.split("\\s+");// 공백을 정의함

		if (tokens[0].equals("create")) {// 방생성
			// 대기방에 있는지 검증

			if (currentRoom.name.equals("_waiting_")) {// 대기방에 있는지 확인
				ChatRoom room = null;
				if (tokens.length == 2)
					room = new ChatRoom(tokens[1], user.getIp_port());
				else if (tokens.length == 3)
					room = new ChatRoom(tokens[1], user.getIp_port(), tokens[2]);

				chatRooms.put(tokens[1], room);
			} else {
				return false;
				
				room.addMember(user);
				rainRooms.addRoom(room);
				
				DataOutputStream out = new DataOutputStream(user.getMsgSocket().getOutputStream());
				out.writeUTF(room.name + );
				
			}
		}
		return true;
	}

}
