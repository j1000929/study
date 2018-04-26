package server;

import java.net.Socket;

public class User {

	String name;
	Socket msgSocket;
	Socket fileSocket;
	ChatRoom currentRoom;// 현재 유저가 위치한 룸

	public ChatRoom getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(ChatRoom currentRoom) {
		this.currentRoom = currentRoom;
	}

	String ip_port;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Socket getMsgSocket() {
		return msgSocket;
	}

	public void setMsgSocket(Socket msgSocket) {
		this.msgSocket = msgSocket;
	}

	public Socket getFileSocket() {
		return fileSocket;
	}

	public void setFileSocket(Socket fileSocket) {
		this.fileSocket = fileSocket;
	}

	public String getIp_port() {
		return ip_port;
	}

	public void setIp_port(String ip_port) {
		this.ip_port = ip_port;
	}

}
