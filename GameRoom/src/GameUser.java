import java.net.Socket;

//������ ������ �÷����ϴ� ���� Ŭ����

public class GameUser {

	// ���ӿ� ���õ� ���� ����
	private int id;// unique id
	private GameRoom room;// ������ ���� ���̴�.
	private Socket socket;// ���� Object
	private String nickName;// �г���

	private PlayerGameInfo.Location playerLocation;// ���� ����
	private PlayerGameInfo.Status playerStatus;// ��������

	public GameUser() {
		// �ƹ��� ������ ���� ���� ������ ���� ��
	}

	/*
	 * ���� ����
	 * 
	 * @param nickName
	 */
	public GameUser(String nickName) {// �г��� ������ ������ ����
		this.nickName = nickName;
	}

	/**
	 * ��������
	 * 
	 * @param id
	 * @param nickName
	 */
	public GameUser(int id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	/*
	 * �濡 ���� ��Ŵ
	 * 
	 * @param room - ������ ��
	 */
	public void enterRoom(GameRoom room) {
		room.enterUser(this);// �뿡 ���� ��Ų ��
		this.room = room;// ������ ���� ���� ������ �����Ѵ� !!important

	}

	/*
	 * �濡�� ����
	 * 
	 * @param room ->������ ��
	 */
	public void exitRoom(GameRoom room) {
		this.room = null;
		// ����ó��(ȭ�鿡 �޼����� �ش�.)
		System.out.println(this.getNickName() + "����" + room + "���� �����߽��ϴ�.");
	}

	public void setPlayerStatus(PlayerGameInfo.Status status) {// ������ ���¸� �˷���
		this.playerStatus = status;

	}

	public void setPlayerLocation(PlayerGameInfo.Location location) {// ������ ��ġ�� �˷���
		this.playerLocation = location;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public GameRoom getRoom() {
		return room;
	}

	public void setRoom(GameRoom room) {
		this.room = room;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	/*
	 * equals�� hashCode�� �������̵������ ���� ������ ���� �� �ִ�. ���� ��-> gameuser �� equals��,
	 * list���� find ��
	 */

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GameUser gameUser = (GameUser) o;

		return id == gameUser.id;// ������ true, ���� ������ false�� ����????

	}

	@Override
	public int hashCode() {
		return id;
	}

}
