import java.util.ArrayList;
import java.util.List;

public class GameRoom {

	private int id;// 룸 아이디
	private List userList;
	private GameUser roomOwner;// 방장
	private String roomName;// 방이름

	public GameRoom(int roomid) {// 유저가 없는 방 생성시
		this.id = roomid;
		userList = new ArrayList();
	}

	public GameRoom(GameUser user) {// 유저가 방을 만들때
		userList = new ArrayList();
		user.enterRoom(this);
		userList.add(user);// 유저를 추가한다.
		this.roomOwner = user;// 방을 만든 유저가 방장이 된다.

	}

	public GameRoom(List users) {// 유저 리스트가 방을 생성할

		this.userList = users; // 유저리스트 복사
		// 룸 입장
		for (GameUser user : userList) {
			user.enterRoom(this);
		}

		this.roomOwner = userList.get(0);// 천번째 유저를 방장으로 설정

	}

	public void enterUser(GameUser gameUser) {
		// TODO Auto-generated method stub

	}

}
