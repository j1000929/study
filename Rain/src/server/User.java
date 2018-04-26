package server;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class User {

	private String name;//�����̸�
	private Socket msgSocket;//ä�ü���
	private Socket fileSocket;//���ϼ���
	private ChatRoom currentRoom;// ���� ������ ��ġ�� ��
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
	
	public DataInputStream getChatIn() throws IOException {//ä�� �޼��� �о����
		DataInputStream in = new DataInputStream(msgSocket.getInputStream());
		return in;
	}
	
	public DataOutputStream getChatOut() throws IOException {//ä�ø޼��� ������
		DataOutputStream out = new DataOutputStream(msgSocket.getOutputStream());
		return out;
	}
	
	public BufferedInputStream getFileIn() throws IOException {
		BufferedInputStream in = new BufferedInputStream(fileSocket.getInputStream());
		return in;
	}
	
	public BufferedOutputStream getFileOut() throws IOException {//�����о����
		BufferedOutputStream out = new BufferedOutputStream(fileSocket.getOutputStream());
		return out;
	}

}	
