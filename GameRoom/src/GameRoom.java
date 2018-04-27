import java.util.ArrayList;
import java.util.List;

public class GameRoom {

	private int id;// �� ���̵�
	private List userList;
	private GameUser roomOwner;// ����
	private String roomName;// ���̸�

	public GameRoom(int roomid) {// ������ ���� �� ������
		this.id = roomid;
		userList = new ArrayList();
	}

	public GameRoom(GameUser user) {// ������ ���� ���鶧
		userList = new ArrayList();
		user.enterRoom(this);
		userList.add(user);// ������ �߰��Ѵ�.
		this.roomOwner = user;// ���� ���� ������ ������ �ȴ�.

	}

	public GameRoom(List users) {// ���� ����Ʈ�� ���� ������

		this.userList = users; // ��������Ʈ ����
		// �� ����
		for (GameUser user : userList) {
			user.enterRoom(this);
		}

		this.roomOwner = userList.get(0);// õ��° ������ �������� ����

	}

	public void enterUser(GameUser gameUser) {
		// TODO Auto-generated method stub

	}

}
