package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {

	String name;// ���̸�
	String owner;// ����
	String password;// ��й�ȣ
	ChatRoom currentRoom;// ���� ������ ��ġ�� ��

	Map<String, User> users;// User-> name, msgSocket, fileSocket ������ ����

	public ChatRoom(String name, String owner) {
		this(name, owner, null);
	}

	public ChatRoom(String name, String owner, String password) {// ���̸�, ����,���
		this.name = name;
		this.owner = owner;
		this.password = password;

		users = Collections.synchronizedMap(new HashMap<>());

	}

	public synchronized boolean addMember(User user) {
		ChatRoom prevRoom = user.currentRoom;
		prevRoom.removeMember(user.getIp_port());

		User out = users.put(user.getIp_port(), user);

		if (out == null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean removeMember(String key) {

		User out = users.remove(name);

		if (out == null) {
			return false;
		} else {
			return true;
		}

	}

	DataOutputStream[] getMembersMsgStream() {// ��� �޼��� ��Ʈ���� �� ����Ѵ�.BroadCasting
		// return chatOutputs.values().toArray(new DataOutputStream[0]);
		return null;
	}

	synchronized void SendMsg(String name, String msg) {

	}

	public BufferedOutputStream[] getMembersFileStream(String name) {// ��� file ��´�.

		// return fileOutputs.get(name);
		return null;
	}

	public void setPassword(String password) {
		this.password = password;

	}

}
