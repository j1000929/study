package server;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {

	String name;
	String owner;
	String password;

	Map<String, User> users;

	public ChatRoom(String name, String owner) {
		this(name, owner, null);
	}

	public ChatRoom(String name, String owner, String password) {
		this.name = name;
		this.owner = owner;
		this.password = password;

		users = Collections.synchronizedMap(new HashMap<>());
	}

	public boolean addMember(User user) {
		User out = users.put(user.getIp_port(), user);

		if (out == null) {
			return true;
		} else {
			return false;
		}

	}

	public boolean removeMember(String key) {
		User out = users.remove(key);

		if (out == null) {
			return false;
		} else {
			return true;
		}
	}

	synchronized void SendMsg(String name, String msg) {

	}

	public void setPassword(String password) {
		this.password = password;
	}
}
