package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {

	String name;// 방이름
	String owner;// 방장
	String password;// 비밀번호
	ChatRoom currentRoom;// 현재 유저가 위치한 룸

	Map<String, User> users;// User-> name, msgSocket, fileSocket 가지고 있음

	public ChatRoom(String name, String owner) {
		this(name, owner, null);
	}

	public ChatRoom(String name, String owner, String password) {// 룸이름, 방장,비번
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

	DataOutputStream[] getMembersMsgStream() {// 모든 메세지 스트림을 얻어서 출력한다.BroadCasting
		// return chatOutputs.values().toArray(new DataOutputStream[0]);
		return null;
	}

	synchronized void SendMsg(String name, String msg) {

	}

	public BufferedOutputStream[] getMembersFileStream(String name) {// 모든 file 얻는다.

		// return fileOutputs.get(name);
		return null;
	}

	public void setPassword(String password) {
		this.password = password;

	}

}
