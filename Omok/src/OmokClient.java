import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

class OmokBoard extends Canvas {// 오목판을 구현하는 클래스

	public static final int BLACK = 1, WHITE = -1;
	private int[][] map;
	private int size;
	private int cell;
	private String info = "게임중지";// 게임의 진행 상황을 나타내는 문자열
	private int color = BLACK;

	// true이면 돌을 놓을 수 있는 상태
	// false이면 돌을 놓을 수 없는 상태
	private boolean enable = false;

	private boolean running = false;// 게임이 진행 중인가 나타내는 변수

	private PrintWriter writer; // 상대편에서 메시지를 전달하기 위한 스트림
	private Graphics gboard, gbuff;// 캔버스와 버퍼를 위한 그래픽스 객체
	private Image buff; // 더블 버퍼링을 위한 버퍼

	OmokBoard(int s, int c) {// 오목판의 생성자(s=15,c=30
		this.size = s;
		this.cell = c;

		map = new int[size + 2][]; // 맵의 크기를 정한다.
		for (int i = 0; i < map.length; i++) {
			map[i] = new int[size + 2];
		} // for

		setBackground(new Color(200, 200, 100));// 오목판의 배경색을 정한다.
		setSize(size * (cell + 1) + size, size * (cell + 1) + size);// 오목판의 크기를 계산한다.

		// 오목판의 마우스 이벤트 처리
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent me) {// 마우스 누르면
				if (!enable)
					return; // 사용자가 누를 수 없는 상태이면 빠져나온다.

				// 마우스의 좌표를 map좌표로 계산한다.
				int x = (int) Math.round(me.getX() / (double) cell);
				int y = (int) Math.round(me.getY() / (double) cell);

				// 돌이 놓일 수 있는 좌표가 아니면 빠져 나온다.
				if (x == 0 || y == 0 || x == size + 1 || y == size + 1)
					return;

				// 해당 좌표에 다른 돌이 놓여져 있으면 빠져 나온다.
				if (map[x][y] == BLACK || map[x][y] == WHITE)
					return;

				// 상대편에게 놓은 돌의 좌표를 전송한다.
				writer.println("[STONE]" + x + " " + y);

				map[x][y] = color;

				// 이겼는지 검사한다.
				if (check(new Point(x, y), color)) {
					info = "이겼습니다.";
					writer.println("[WIN]");
				}

				else
					info = "상대가 두기를 기다립니다.";
				repaint(); // 오목판을 그린다.

				// 사용자가 둘 수 없는 상태로 만든다.
				// 상재편이 두면 enable이 true기 되어 사용자가 둘수 있게 된다.
				enable = false;
			}

		});

	}// OmokBoard생성자

	public boolean isRunning() {// 게임의 진행 상태를 반환한다.
		return running;

	}

	public void startGame(String col) {// 게임을 시작한다.
		running = true;
		if (col.equals("BLACK")) {// 흑이 선택되었을때
			enable = true;
			color = BLACK;// BLACK=1
			info = "게임시작..두세요";
		} else { // 백이 선택되었을때
			enable = false;
			color = WHITE;// WHITE=1//
			info = "게임시작..두세요";
		}
	}

	public void stopGame() {// 게임을 멈춘다
		reset();// 오목판을 초기화한다
		writer.println("[STOPGAME]");// 상대편에게 메세지를 보낸다.
		enable = false;
		running = false;
	}

	public void putOpponent(int x, int y) {// 상대편의 돌을 놓는다.
		map[x][y] = -color;
		info = "상대가 두었습니다.두세요";
		repaint();

	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public void setWriter(PrintWriter writer) {
		this.writer = writer;
	}

	public void update(Graphics g) {// repaint를 호출하면 자동으로 호출된다.
		paint(g);// paint를 호출한다.
	}

	public void paint(Graphics g) {// 화면을 그린다.
		if (gbuff == null) {// 버퍼가 없으면 버퍼를 만든다.
			buff = createImage(getWidth(), getHeight());
			gbuff = buff.getGraphics();
		}
		drawBoard(g);
	}

	synchronized private void drawBoard(Graphics g) {// 오목판을 그린다.
		// 버퍼에 먼저 그리고 버초의 이미지를 오목판에 그린다.
		gbuff.clearRect(0, 0, getWidth(), getHeight());
		drawLine();
		drawStones();
		gbuff.setColor(Color.red);
		gbuff.drawString(info, 20, 15);
		g.drawImage(buff, 0, 0, this);

	}

	private void drawStones() {// map에 놓여진 돌들을 모두 그린다.
		for (int x = 1; x <= size; x++) {
			for (int y = 1; y <= size; y++) {
				if (map[x][y] == BLACK)
					drawBlack(x, y);
				else if (map[x][y] == WHITE)
					drawWhite(x, y);
			}
		}
	}

	private void drawWhite(int x, int y) {// 백돌을 (x,y)를 그린다.
		gbuff.setColor(Color.WHITE);
		gbuff.fillOval(x * cell - cell / 2, y * cell - cell / 2, cell, cell);
		gbuff.setColor(Color.black);
		gbuff.drawOval(x * cell - cell / 2, y * cell - cell / 2, cell, cell);
	}

	private void drawBlack(int x, int y) {// 흑돌을 (x,y)를 그린다.
		gbuff.setColor(Color.black);
		gbuff.fillOval(x * cell - cell / 2, y * cell - cell / 2, cell, cell);
		gbuff.setColor(Color.white);
		gbuff.fillOval(x * cell - cell / 2, y * cell - cell / 2, cell, cell);

	}

	private void drawLine() {// 오목판에 선을 긋는다.
		gbuff.setColor(Color.BLACK);
		for (int i = 1; i <= size; i++) {
			gbuff.drawLine(cell, i * cell, cell * size, i * cell);
			gbuff.drawLine(i * cell, cell, i * cell, cell * size);
		}

	}

	public void reset() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				map[i][j] = 0;
			}
		}
		info = "게임 중지";
		repaint();
	}

	private boolean check(Point p, int col) {
		if (count(p, 1, 0, col) + count(p, -1, 0, col) == 4)
			return true;
		if (count(p, 0, 1, col) + count(p, 0, -1, col) == 4)
			return true;
		if (count(p, -1, -1, col) + count(p, 1, 1, col) == 4)
			return true;
		if (count(p, 1, -1, col) + count(p, -1, 1, col) == 4)
			return true;
		return false;
	}

	private int count(Point p, int dx, int dy, int col) {
		int i = 0;
		for (; map[p.x + (i + 1) * dx][p.y + (i + 1) * dy] == col; i++)
			;
		return i;
	}
}// OmokBoard 구현 끝

public class OmokClient extends Frame implements Runnable, ActionListener {
	private TextArea msgView = new TextArea("", 1, 1, 1);// 메세지를 보여주는 영역
	private TextField sendBox = new TextField("");// 보낼 메시지를 적는 상자
	private TextField nameBox = new TextField();// 사용자 이름 상자
	private TextField roomBox = new TextField("0");// 방번호 상자

	// 방에 접속한 인원의 수를 보여주는 레이블
	private Label pInfo = new Label("대기실: 명");
	private java.awt.List pList = new java.awt.List();// 사용자 명단을 보여주는 리스트
	private Button startButton = new Button("대국시작");// 대국시작 버튼
	private Button stopButton = new Button("기권");// 기권버튼
	private Button enterButton = new Button("입장하기");// 입장하기 버튼
	private Button exitButton = new Button("대기실로");// 대기실로 가는 버튼

	// 각종 정보를 보여주는 레이블
	private Label infoView = new Label("<생각하는 자바>", 1);

	private OmokBoard board = new OmokBoard(15, 30);// 오목판 객체
	private BufferedReader reader;// 입력 스트림
	private PrintWriter writer;// 출력 스트림
	private Socket socket; // 소켓
	private int roomNumber = -1; // 방번호
	private String userName = null;// 사용자 이름

	public OmokClient(String title) {// 생성자
		super(title);
		setLayout(null); // 레이아웃을 사용하지 않는다.

		// 각종 컴포넌트를 생성하고 배치한다.
		msgView.setEditable(false);
		infoView.setBounds(10, 30, 480, 30);// 왼쪽 위 모서리, width, height
		board.setLocation(10, 70);
		add(infoView);// Component java.awt.Container.add(Component comp)
		add(board);
		Panel p = new Panel();
		p.setBackground(new Color(200, 255, 255));
		p.setLayout(new GridLayout(3, 3));
		p.add(new Label("이    름:", 2));
		p.add(nameBox);
		p.add(new Label("방 번호:", 2));
		p.add(roomBox);
		p.add(enterButton);
		p.add(exitButton);
		enterButton.setEnabled(false);
		p.setBounds(500, 30, 250, 70);

		Panel p2 = new Panel();
		p2.setBackground(new Color(255, 255, 100));
		p2.setLayout(new BorderLayout());
		Panel p2_1 = new Panel();
		p2_1.add(startButton);
		p2_1.add(stopButton);
		p2.add(pInfo, "North");// 대기실 정보(대기인원: **명)
		p2.add(pList, "Center");// 사용자 명단을 보여주는 리스트
		p2.add(p2_1, "South");
		startButton.setEnabled(false);
		stopButton.setEnabled(false);
		p2.setBounds(500, 110, 250, 180);

		Panel p3 = new Panel();
		p3.setLayout(new BorderLayout());
		p3.add(msgView, "Center");
		p3.add(sendBox, "South");
		p3.setBounds(500, 300, 250, 250);

		add(p);
		add(p2);
		add(p3);

		// 이벤트 리스너를 등록한다.
		sendBox.addActionListener(this);
		enterButton.addActionListener(this);
		exitButton.addActionListener(this);
		startButton.addActionListener(this);
		stopButton.addActionListener(this);

		// 윈도우 닫기 처리
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	// 컴포넌트들의 액션 이벤트 처리
	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == sendBox) {// 메시지 입력 상자이면//getSource()-이벤트가 처음 발생한 객체
			String msg = sendBox.getText();
			if (msg.length() == 0)
				return;
			if (msg.length() >= 30)
				msg = msg.substring(0, 30);
			try {
				writer.println("[MSG]" + msg);
				sendBox.setText("");
			} catch (Exception ie) {
			}

		} else if (ae.getSource() == enterButton) {// 입장하기 버튼이면
			try {
				if (Integer.parseInt(roomBox.getText()) < 1) {
					infoView.setText("방번호 오류/1이상 입력");// infoView - 각종 정보를 보여주는 레이블
					return;
				}
				writer.println("[ROOM]" + Integer.parseInt(roomBox.getText()));
				msgView.setText("");
			} catch (Exception ie) {
				infoView.setText("입력하신 사항에 오류가 있습니다");
			}

		} else if (ae.getSource() == exitButton) {// 대기실로 버튼이면
			try {
				goToWaitRoom();//
				startButton.setEnabled(false);
				stopButton.setEnabled(false);
			} catch (Exception e) {
			}

		} else if (ae.getSource() == startButton) {// 대국 시작 버튼이면
			try {
				writer.println("[START]");
				infoView.setText("상대의 결정을 기다립니다.");
				startButton.setEnabled(false);
			} catch (Exception ie) {
			}

		} else if (ae.getSource() == stopButton) {// 기권 버튼이면
			try {
				writer.println("[DROPGAME]");
				endGame("기권하였습니다.");
			} catch (Exception ie) {
			}

		}

	}

	private void endGame(String string) {
		// TODO Auto-generated method stub

	}

	private void goToWaitRoom() {// 대기실로 버튼을 누르면 호출
		if (userName == null) {
			String name = nameBox.getText().trim();
			if (name.length() <= 2 || name.length() > 10) {
				infoView.setText("이름이 잘못되었습니다.3~10자");
				nameBox.requestFocus();
				return;
			}
			userName = name;
			writer.println("[NAME]" + userName);
			nameBox.setText(userName);
			nameBox.setEnabled(false);
		}
		msgView.setText("");
		writer.println("[ROOM]0;");
		infoView.setText("대기실에 입장하셨습니다.");
		roomBox.setText("0");// 방이름 Box
		enterButton.setEnabled(true);
		exitButton.setEnabled(false);

	}

	@Override
	public void run() {
		String msg; // 서버로부터의 메세지

		try {
			while ((msg = reader.readLine()) != null) {

				if (msg.startsWith("[STONE]")) {// 상대편이 놓은 돌의 좌표
					String temp = msg.substring(7);
					int x = Integer.parseInt(temp.substring(0, temp.indexOf(" ")));
					int y = Integer.parseInt(temp.substring(temp.indexOf(" ") + 1));
					board.putOpponent(x, y);// 상대평의 돌을 그린다.
					board.setEnable(true);// 사용자가 돌을 놓을 수 있도록 한다.

				} else if (msg.startsWith("[ROOM]")) {// 방에 입장이면
					if (!msg.equals("[ROOM]0")) {// 대기실이 아닌 방이면
						enterButton.setEnabled(false);
						exitButton.setEnabled(true);
						infoView.setText(msg.substring(6) + "번 방에 입장하셨습니다.");

					} else
						infoView.setText("대기실에 입장하셨습니다.");

					roomNumber = Integer.parseInt(msg.substring(6));// 방 번호 지정

					if (board.isRunning()) {// 게임이 진행중인 상태이면
						board.stopGame(); // 게임을 중지시킨다.
					}

				} else if (msg.startsWith("[FULL]")) {// 방이 찬 상태이면
					infoView.setText("방이 차서 입장할 수 없습니다.");

				} else if (msg.startsWith("[PLAYER]")) {// 방에 있는 사용자 명단
					nameList(msg.substring(9));

				} else if (msg.startsWith("[ENTER]")) {// 손님 입장
					pList.add(msg.substring(7));
					playInfo();
					msgView.append("[" + msg.substring(7) + "]님이 입장하였습니다.\n");

				}

			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 사용자 리스트에서 사용자들을 추출하여 pList에 추가한다.
	private void nameList(String msg) {

		pList.removeAll();
		StringTokenizer st = new StringTokenizer(msg, "\t");
		while (st.hasMoreElements())
			pList.add(st.nextToken());
		playersInfo();

	}

	private void playersInfo() { // 방에 있는 접속자의 수를 보여준다.

		int count = pList.getItemCount();

		if (roomNumber == 0)
			pInfo.setText("대기실:" + count + "명");
		else
			pInfo.setText(roomNumber + "번 방:" + count + "명");

		// 대국 시작 버튼의 활성화 상태를 점검한다.
		if (count == 2 && roomNumber != 0) {
			startButton.setEnabled(true);
		} else
			startButton.setEnabled(false);

	}

	public static void main(String[] args) {
		// OmokClient client = new OmokClient("네트워크 오목 게임");
		// client.setSize(760, 560);
		// client.setVisible(true);
		// client.connect();

	}// main

}// OmokClient
