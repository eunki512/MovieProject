package theater.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.cinema.CinemaMain;
import theater.db.ConnectionManager;
import theater.event.EventMain;
import theater.movie.MovieDetail;
import theater.movie.MovieMain;
import theater.reservation.ReservationMain;
import theater.utils.ImageLoad;

public class MainFrame extends JFrame {
	JPanel p_north, p_title, p_menu, p_content;
	JPanel p_logo, p_myTicket, p_movie, p_cinema, p_reserve, p_event;
	public JPanel[] pages = new JPanel[4]; // 영화,극장,예매,이벤트 페이지
	public JPanel[] pages_detail = new JPanel[8]; // 영화 세부 페이지
	JLabel la_logo, la_myTicket, la_movie, la_cinema, la_reserve, la_event;
	Image titleIcon, logo;

	ImageLoad imageLoad;
	public ConnectionManager connectionManager;
	public Connection con;
	MovieMain movieMain; // 영화 페이지
	MovieDetail movieDetail; // 영화 세부 페이지
	CinemaMain cinemaMain; // 극장 페이지
	public ReservationMain reservationMain; // 예매 페이지
	EventMain eventMain; // 이벤트 페이지

	public String[] movie = { "해리포터와 비밀의 방", "미션 임파서블", "어벤져스", "쥬라기 월드 : 폴른 킹덤", "곡성", "센과 치히로의 행방불명", "인사이드 아웃",
			"몬스터 주식회사 3D" }; // 상영중인 영화 제목 배열
	public String[] cinema = { "KGV 강남", "KGV 명동", "KGV 구로", "KGV 용인", "KGV 수원", "KGV 춘천", "KGV 광주", "KGV 대전", "KGV 대구",
			"KGV 부산" }; // 극장 이름 배열
	public ArrayList<String> reserveInfo = new ArrayList<String>(); // 예매 정보를 담은 배열
	public boolean flag_reserve = false; // 예매 여부

	public MainFrame() {
		connectionManager = new ConnectionManager();
		con = connectionManager.getConnection();

		p_north = new JPanel(); // title과 menu 패널을 붙일 패널
		p_title = new JPanel(); // 제목과 마이티켓을 붙일 패널
		p_menu = new JPanel(); // 메뉴들을 붙일 패널
		p_content = new JPanel(); // 내용을 붙일 패널

		p_logo = new JPanel(); // 로고를 붙일 패널
		p_myTicket = new JPanel(); // 마이티켓 버튼을 붙일 패널
		p_movie = new JPanel(); // 메뉴 '영화'를 붙일 패널
		p_cinema = new JPanel();// 메뉴 '극장'를 붙일 패널
		p_reserve = new JPanel();// 메뉴 '예매'를 붙일 패널
		p_event = new JPanel();// 메뉴 '이벤트'를 붙일 패널

		imageLoad = new ImageLoad();
		titleIcon = imageLoad.getImage("titleLogo.png");
		titleIcon = titleIcon.getScaledInstance(350, 200, Image.SCALE_SMOOTH);
		logo = imageLoad.getImage("logo.png");
		logo = logo.getScaledInstance(250, 80, Image.SCALE_SMOOTH);

		la_logo = new JLabel(new ImageIcon(logo));
		la_myTicket = new JLabel("myTicket", SwingConstants.CENTER);
		la_movie = new JLabel("영화", SwingConstants.CENTER);
		la_cinema = new JLabel("극장", SwingConstants.CENTER);
		la_reserve = new JLabel("예매", SwingConstants.CENTER);
		la_event = new JLabel("이벤트", SwingConstants.CENTER);

		pages[0] = new MovieMain(this);
		pages[1] = new CinemaMain(this);
		reservationMain = new ReservationMain(this);
		pages[2] = reservationMain;
		pages[3] = new EventMain();

		for (int i = 0; i < pages_detail.length; i++) {
			pages_detail[i] = new MovieDetail(this, i);
		}

		// 폰트 설정
		la_myTicket.setFont(new Font("굴림", Font.BOLD, 20));
		Font f = new Font("굴림", Font.BOLD, 30);
		la_movie.setFont(f);
		la_cinema.setFont(f);
		la_reserve.setFont(f);
		la_event.setFont(f);

		// 사이즈 조절
		p_logo.setPreferredSize(new Dimension(770, 100));
		p_myTicket.setPreferredSize(new Dimension(130, 40));
		p_title.setPreferredSize(new Dimension(1000, 100));
		p_menu.setPreferredSize(new Dimension(1000, 80));
		p_north.setPreferredSize(new Dimension(1000, 180));
		p_content.setPreferredSize(new Dimension(1000, 620));

		Dimension d = new Dimension(250, 80);
		la_movie.setPreferredSize(d);
		la_cinema.setPreferredSize(d);
		la_reserve.setPreferredSize(d);
		la_event.setPreferredSize(d);

		// 여백 설정
		la_logo.setBorder(BorderFactory.createEmptyBorder(0, 180, 30, 0));
		la_movie.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		la_cinema.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		la_reserve.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		la_event.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		// 테두리 설정
		p_myTicket.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, true));

		// 배경색 설정
		p_logo.setBackground(Color.WHITE);
		p_myTicket.setBackground(Color.YELLOW);
		p_title.setBackground(Color.WHITE);
		p_north.setBackground(Color.WHITE);
		p_movie.setBackground(new Color(239, 185, 84));
		p_cinema.setBackground(new Color(103, 156, 220));
		p_reserve.setBackground(new Color(125, 226, 97));
		p_event.setBackground(new Color(239, 126, 84));
		p_content.setBackground(Color.WHITE);

		// 레이아웃 설정
		p_menu.setLayout(new GridLayout(1, 4));

		// 부착
		p_logo.add(la_logo);
		p_myTicket.add(la_myTicket, BorderLayout.EAST);
		p_title.add(p_logo);
		p_title.add(p_myTicket);

		p_movie.add(la_movie);
		p_cinema.add(la_cinema);
		p_reserve.add(la_reserve);
		p_event.add(la_event);
		p_menu.add(p_movie);
		p_menu.add(p_cinema);
		p_menu.add(p_reserve);
		p_menu.add(p_event);

		for (int i = 0; i < pages.length; i++) {
			p_content.add(pages[i]);
		}
		for (int i = 0; i < pages_detail.length; i++) {
			p_content.add(pages_detail[i]);
		}

		p_north.add(p_title, BorderLayout.NORTH);
		p_north.add(p_menu);
		add(p_north, BorderLayout.NORTH);
		add(p_content);

		// 마이 티켓 라벨과 리스너 연결
		la_myTicket.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();

				if (flag_reserve) { // 예매 내역이 있을 경우
					MyTicket myTicket = new MyTicket(MainFrame.this);
					myTicket.setBounds(x + 190, y, 350, 580);
				} else { // 예매 내역이 없을 경우
					MyTicketEmpty myTicketEmpty = new MyTicketEmpty(MainFrame.this);
					myTicketEmpty.setBounds(x + 190, y, 350, 580);
				}
			}
		});

		// 영화 페이지 보는 리스너
		la_movie.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(0);
			}
		});

		// 극장 페이지 보는 리스너
		la_cinema.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(1);
			}
		});

		// 예매 페이지 보는 리스너
		la_reserve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(2);
			}
		});

		// 이벤트 페이지 보는 리스너
		la_event.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(3);
			}
		});

		setIconImage(titleIcon); // 타이틀 아이콘
		setBounds(500, 150, 1000, 800);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// 페이지 변경 함수
	public void chageChannel(int page) {
		for (int i = 0; i < pages.length; i++) {
			if (i == page) { // 선택한 페이지만 보이도록
				pages[i].setVisible(true);
			} else {
				pages[i].setVisible(false);
			}
		}

		// 영화 세부 페이지는 모두 안보이도록
		for (int i = 0; i < pages_detail.length; i++) {
			pages_detail[i].setVisible(false);
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
