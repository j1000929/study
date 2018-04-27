package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Map;

public class ServerReceiver extends Thread {

	User user;
	DataInputStream msgInput;
	BufferedInputStream fileInput;
	ChatRoom currentRoom;
	Map<String, ChatRoom> chatRooms;

	public ServerReceiver(User user, Map<String, ChatRoom> chatRooms) {
		this.user = user;
		this.chatRooms = chatRooms;
		currentRoom = chatRooms.get("__waiting__");

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
			user.setName(msgInput.readUTF());

			String msg;
			while (msgInput != null) {
				msg = msgInput.readUTF();
				System.out.println(msg);

				if (msg.startsWith("/")) { // command
					processCmd(msg.substring(1));
				} else { // message
					// 현재 방에 있는 유저들에게 메세지 전송
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				msgInput.close();
				fileInput.close();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	boolean processCmd(String cmd) {
		String[] tokens = cmd.split(" ");
		if (tokens[0].equals("create")) { // 방생성
			// 대기방에 있는지 검증

			if (currentRoom.name.equals("__waiting__")) {
				ChatRoom room = null;
				if (tokens.length == 2)
					room = new ChatRoom(tokens[1], user.getIp_port());
				else if (tokens.length == 3)
					room = new ChatRoom(tokens[1], user.getIp_port(), tokens[2]);
				else
					return false;

				chatRooms.put(tokens[1], room);
			} else {
				return false;
			}
		}

		return true;
	}
}
