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
	ChatRoom currentRoom;// 현재 유저가 위치한 룸

	//Map<String, ChatRoom> chatRooms;
	Rooms rooms;

	public ServerReceiver(User user, Rooms rooms) {
		this.user = user;
		this.rooms = rooms;
		rooms.setCurrentRoom("__waiting__", user);//대기방으로 세팅

		try {
			msgInput = new DataInputStream(user.getMsgSocket().getInputStream());
			fileInput = new BufferedInputStream(user.getFileSocket().getInputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void send(String msg) throws IOException{// 사용자에게 전달하는 메세지에 쓰는 메소드 줄인거
		user.getChatOut().writeUTF(msg);
	}
	@Override
	public void run() {
		try {
			user.setName(user.getIp_port());// 유저의 ip
			rooms.checkName("__waiting__", user);
			
			if(!rooms.addMember("__waiting__", user)) {
				System.out.println("error01:addMember 실패");
			}
			
			System.out.println("[" + user.getName() + "]이["
					+user.getCurrentRoom().getName()+ "]에 들어감");
			System.out.println("["+user.getCurrentRoom().getName()
					+ "]의 접속자 목록: "+user.getCurrentRoom().getUsers().keySet().toString());
			send("["+ user.getName()+"]님 반갑습니다."+"\n");
			String msg;
			
			while(msgInput != null) {
				msg = msgInput.readUTF();
				
				if (msg.startsWith("/")) {// msg가 커맨드인지...
					String cmsg =  processCmd(msg.substring(1));// 1에서 부터 끝까지~
					if(cmsg != null) {
						
					}
				} else if(msg.startsWith("#")) {// 파일 받을때
					// 현재 방에 있는 유저들에게 메세지전송
					String[] cmd = msg.split("#");
					FileReceiver fr = new FileReceiver(cmd[1],user.getCurrentRoom().getName(),fileInput);
					
					fr.start();
					sleep(1000);
					user.getCurrentRoom().getFiles().put(cmd[1], new File("D://" + user.getCurrentRoom() + "//" + cmd[1]));
					rooms.sendToAll("	* " + user.getName() + "님이 " + cmd[1] + "파일을 보냈습니다. 파일을 받으려면 /파일받기 파일이름 을 입력하세요.", user);
				} else {// massage
					rooms.sendToAll(msg, user);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO 자동 생성된 catch 블록
			e.printStackTrace();
		}  finally {//
			try {
				msgInput.close();
				fileInput.close();
				user.getMsgSocket().close();
				user.getFileSocket().close();
				ChatRoom a = user.getCurrentRoom();
				a.removeMember(user.getName());
				rooms.sendToAll("	* [" + user.getName() + "]님이 프로그램을 종료하였습니다.", user);
				System.out.println(" [" + a.getName() + "]에서 [" + user.getName() + "]님이 접속을 종료하였습니다.");
				System.out.println("현재 [" + a.getName() + "] 방 접속자 수는 " + a.getUsers().size() + "입니다.");
				System.out.println("[" + a.getName() + "]의 접속자 목록:" + a.getUsers().keySet().toString());

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 방생성하는 함수

	String processCmd(String cmd) throws IOException {
		return rooms.processCmd(cmd, user);
	}

}
