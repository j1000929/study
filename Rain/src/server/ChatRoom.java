package server;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ChatRoom {

	private String name;// 방이름
	private String owner;// 방장
	private String password=null;// 비밀번호
	
	private Map<String, User> users;// User-> name, msgSocket, fileSocket 가지고 있음
	private Map<String, File> files;
	
	public Map<String, User> getUsers() {
		return users;
	}

	public void setUsers(Map<String, User> users) {
		this.users = users;
	}

	public Map<String, File> getFiles() {
		return files;
	}

	public void setFiles(Map<String, File> files) {
		this.files = files;
	}
	
	public ChatRoom(String name, String owner) {
		this(name, owner, null);
	}
	
	public ChatRoom(String name, String owner, String password) {// 룸이름, 방장,비번
		this.name = name;
		this.owner = owner;
		this.password = password;
		
		users = Collections.synchronizedMap(new HashMap<>());
		files= new HashMap<>();

	}

	public boolean addMember(User user) {
		
		User out = users.put(user.getIp_port()+user.getName(), user);

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

	public void setPassword(String password) {
		this.password = password;

	}
	
	public String getPassword() {
		return password;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}
	
	synchronized void SendMsg(String name, String msg) {

	}

	public BufferedOutputStream[] getMembersFileStream(String name) {// 모든 file 얻는다.

		// return fileOutputs.get(name);
		return null;
	}

	DataOutputStream[] getMembersMsgStream() {// 모든 메세지 스트림을 얻어서 출력한다.BroadCasting
		// return chatOutputs.values().toArray(new DataOutputStream[0]);
		return null;
	}

}
