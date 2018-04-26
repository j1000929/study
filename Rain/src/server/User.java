package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

	private String name;//유저이름
	private Socket msgSocket;//채팅소켓
	private Socket fileSocket;//파일소켓
	private ChatRoom currentRoom;// 현재 유저가 위치한 룸
	String ip_port;
	private String invite;
	private String invitepw;
	
	
	public String getInvite() {
		return invite;
	}

	public void setInvite(String invite) {
		this.invite = invite;
	}

	public String getInvitepw() {
		return invitepw;
	}

	public void setInvitepw(String invitepw) {
		this.invitepw = invitepw;
	}

	public ChatRoom getCurrentRoom() {
		return currentRoom;
	}

	public void setCurrentRoom(ChatRoom currentRoom) {
		this.currentRoom = currentRoom;
	}

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
	
	public DataInputStream getChatIn() throws IOException {//채팅 메세지 읽어오기
		DataInputStream in = new DataInputStream(msgSocket.getInputStream());
		return in;
	}
	
	public DataOutputStream getChatOut() throws IOException {//채팅메세지 보내기
		DataOutputStream out = new DataOutputStream(msgSocket.getOutputStream());
		return out;
	}
	
	public BufferedInputStream getFileIn() throws IOException {
		BufferedInputStream in = new BufferedInputStream(fileSocket.getInputStream());
		return in;
	}
	
	public BufferedOutputStream getFileOut() throws IOException {//파일읽어오기
		BufferedOutputStream out = new BufferedOutputStream(fileSocket.getOutputStream());
		return out;
	}

}	
