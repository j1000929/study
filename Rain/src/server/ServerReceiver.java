package server;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Map;

import common.ConsoleColor;

public class ServerReceiver extends Thread {

	User user;
	DataInputStream msgInput;
	BufferedInputStream fileInput;
	ChatRoom currentRoom;// ���� ������ ��ġ�� ��

	//Map<String, ChatRoom> chatRooms;
	Rooms rooms;

	public ServerReceiver(User user, Rooms rooms) {
		this.user = user;
		this.rooms = rooms;
		rooms.setCurrentRoom("__waiting__", user);//�������� ����

		try {
			msgInput = new DataInputStream(user.getMsgSocket().getInputStream());
			fileInput = new BufferedInputStream(user.getFileSocket().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void send(String msg) throws IOException{// ����ڿ��� �����ϴ� �޼����� ���� �޼ҵ� ���ΰ�
		user.getChatOut().writeUTF(msg);
	}
	@Override
	public void run() {
		try {
			user.setName(user.getIp_port());// ������ ip
			rooms.checkName("__waiting__", user);
			
			if(!rooms.addMember("__waiting__", user)) {
				System.out.println("error01:addMember ����");
			}
			
			System.out.println("[" + user.getName() + "]��["
					+user.getCurrentRoom().getName()+ "]�� ��");
			System.out.println("["+user.getCurrentRoom().getName()
					+ "]�� ������ ���: "+user.getCurrentRoom().getUsers().keySet().toString());
			send("["+ user.getName()+"]�� �ݰ����ϴ�."+"\n");
			String msg;
			
			while(msgInput != null) {
				msg = msgInput.readUTF();
				
				if (msg.startsWith("/")) {// msg�� Ŀ�ǵ�����...
					String cmsg =  processCmd(msg.substring(1));// 1���� ���� ������~
					if(cmsg != null) {
						
					}
				} else if(msg.startsWith("#")) {// ���� ������
					// ���� �濡 �ִ� �����鿡�� �޼�������
					String[] cmd = msg.split("#");
					FileReceiver fr = new FileReceiver(cmd[1],user.getCurrentRoom().getName(),fileInput);
					
					fr.start();
					sleep(1000);
					user.getCurrentRoom().getFiles().put(cmd[1], new File("D://" + user.getCurrentRoom() + "//" + cmd[1]));
					rooms.sendToAll("	* " + user.getName() + "���� " + cmd[1] + "������ ���½��ϴ�. ������ �������� /���Ϲޱ� �����̸� �� �Է��ϼ���.", user);
				} else {// massage
					rooms.sendToAll(msg, user);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO �ڵ� ������ catch ���
			e.printStackTrace();
		}  finally {//
			try {
				msgInput.close();
				fileInput.close();
				user.getMsgSocket().close();
				user.getFileSocket().close();
				ChatRoom a = user.getCurrentRoom();
				a.removeMember(user.getName());
				rooms.sendToAll("	* [" + user.getName() + "]���� ���α׷��� �����Ͽ����ϴ�.", user);
				System.out.println(" [" + a.getName() + "]���� [" + user.getName() + "]���� ������ �����Ͽ����ϴ�.");
				System.out.println("���� [" + a.getName() + "] �� ������ ���� " + a.getUsers().size() + "�Դϴ�.");
				System.out.println("[" + a.getName() + "]�� ������ ���:" + a.getUsers().keySet().toString());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ������ϴ� �Լ�

	String processCmd(String cmd) throws IOException {
		return rooms.processCmd(cmd, user);
	}

}
