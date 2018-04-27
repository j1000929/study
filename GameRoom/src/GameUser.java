import java.net.Socket;

//실제로 게임을 플레이하는 유저 클래스

public class GameUser {

	// 게임에 관련된 변수 설정
	private int id;// unique id
	private GameRoom room;// 유저가 속한 룸이다.
	private Socket socket;// 소켓 Object
	private String nickName;// 닉네임

	private PlayerGameInfo.Location playerLocation;// 게임 정보
	private PlayerGameInfo.Status playerStatus;// 게임정보

	public GameUser() {
		// 아무런 정보가 없는 깡통 유저를 만들 때
	}

	/*
	 * 유저 생성
	 * 
	 * @param nickName
	 */
	public GameUser(String nickName) {// 닉네임 정보만 가지고 생성
		this.nickName = nickName;
	}

	/**
	 * 유저생성
	 * 
	 * @param id
	 * @param nickName
	 */
	public GameUser(int id, String nickName) {
		this.id = id;
		this.nickName = nickName;
	}

	/*
	 * 방에 입장 시킴
	 * 
	 * @param room - 입장할 방
	 */
	public void enterRoom(GameRoom room) {
		room.enterUser(this);// 룸에 입장 시킨 후
		this.room = room;// 유저가 속한 방을 룸으로 변경한다 !!important

	}

	/*
	 * 방에서 퇴장
	 * 
	 * @param room ->퇴장할 방
	 */
	public void exitRoom(GameRoom room) {
		this.room = null;
		// 퇴장처리(화면에 메세지를 준다.)
		System.out.println(this.getNickName() + "님이" + room + "에서 퇴장했습니다.");
	}

	public void setPlayerStatus(PlayerGameInfo.Status status) {// 유저의 상태를 알려줌
		this.playerStatus = status;

	}

	public void setPlayerLocation(PlayerGameInfo.Location location) {// 유저의 위치를 알려줌
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
	 * equals와 hashCode를 오버라이드해줘야 동일 유저를 비교할 수 있다. 비교할 때-> gameuser 간 equals비교,
	 * list에서 find 등
	 */

	@Override
	public boolean equals(Object o) {

		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		GameUser gameUser = (GameUser) o;

		return id == gameUser.id;// 같으면 true, 같지 않으면 false를 리턴????

	}

	@Override
	public int hashCode() {
		return id;
	}

}
