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
	ChatRoom currentRoom;// ���� ������ ��ġ�� ��

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
			user.setName(msgInput.readUTF());// ������ �̸�

			String msg;

			while (msgInput != null) {
				msg = msgInput.readUTF();
				System.out.println(ConsoleColor.ANSI_CYAN + msg + ConsoleColor.ANSI_RESET);

				msg = msg.substring(msg.indexOf("]") + 1);
				if (msg.startsWith("/")) {// msg�� Ŀ�ǵ�����...
					processCmd(msg.substring(1));// 1���� ���� ������~
				} else {// message
					// ���� �濡 �ִ� �����鿡�� �޼��� ����
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

	// ������ϴ� �Լ�

	boolean processCmd(String cmd) {
		String[] tokens = cmd.split("\\s+");// ������ ������

		if (tokens[0].equals("create")) {// �����
			// ���濡 �ִ��� ����

			if (currentRoom.name.equals("_waiting_")) {// ���濡 �ִ��� Ȯ��
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
