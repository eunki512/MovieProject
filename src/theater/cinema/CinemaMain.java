package theater.cinema;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

import theater.main.MainFrame;
import theater.reservation.ReservationMain;
import theater.utils.ImageLoad;

public class CinemaMain extends JPanel {
	JPanel p_search, p_list1, p_list2, p_north, p_cinema, p_time, p_south;
	JLabel[] la_list = new JLabel[10]; // 극장 이름 라벨 배열
	JLabel la_cinema, la_time;
	JTextField t_search;
	JButton bt_search;
	JScrollPane scroll;
	Image cinema;

	ImageLoad imageLoad;
	MainFrame mainFrame;

	String cinema_name = "KGV 강남"; // 선택한 극장 이름
	String search_cinema; // 검색한 극장 이름
	ArrayList<JPanel> p_timeTable = new ArrayList<JPanel>(); // timePanel을 담는 배열
	ArrayList<String> movieList = new ArrayList<String>(); // 선택한 극장에서 상영중인 영화들

	public CinemaMain(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		p_search = new JPanel(); // 검색 패널
		p_list1 = new JPanel(); // 극장 이름들을 붙일 패널1
		p_list2 = new JPanel(); // 극장 이름들을 붙일 패널2
		p_north = new JPanel(); // 검색패널과 극장이름패널을 붙일 패널
		p_cinema = new JPanel(); // 극장 사진 패널
		p_time = new JPanel(); // 상영 시간표 패널
		p_south = new JPanel(); // 극장사진패널과 상영시간표패널을 붙일 패널

		getMovieList(mainFrame.cinema[0]); // 처음에는 첫번째 극장이 나오도록
		for (int i = 0; i < movieList.size(); i++) {
			TimePanel timePanel = new TimePanel(mainFrame, cinema_name, movieList.get(i), i);
			p_timeTable.add(timePanel);
		}

		for (int i = 0; i < la_list.length; i++) {
			la_list[i] = new JLabel(mainFrame.cinema[i]);
		}

		imageLoad = new ImageLoad();
		cinema = imageLoad.getImage("cinema1.png");
		cinema = cinema.getScaledInstance(400, 350, Image.SCALE_SMOOTH);

		la_cinema = new JLabel(new ImageIcon(cinema));
		la_time = new JLabel("상영 시간표");

		t_search = new JTextField("극장 이름을 검색하세요.") { // JTextField 테두리 없애기
			public void setBorder(Border border) {
			}
		};
		bt_search = new JButton("검색");
		scroll = new JScrollPane(p_south);

		// 폰트 설정
		t_search.setFont(new Font("굴림", Font.PLAIN, 14));
		for (int i = 0; i < la_list.length; i++) {
			la_list[i].setFont(new Font("굴림", Font.BOLD, 16));
			if (i == 0) {
				la_list[i].setForeground(Color.RED);
			} else {
				la_list[i].setForeground(Color.BLACK);
			}
		}
		la_time.setFont(new Font("굴림", Font.BOLD, 22));
		la_time.setForeground(Color.BLACK);

		// 사이즈 조절
		t_search.setPreferredSize(new Dimension(400, 40));
		p_search.setPreferredSize(new Dimension(1000, 60));
		p_list1.setPreferredSize(new Dimension(1000, 40));
		p_list2.setPreferredSize(new Dimension(1000, 40));
		p_north.setPreferredSize(new Dimension(1000, 140));
		p_cinema.setPreferredSize(new Dimension(450, 480));
		p_time.setPreferredSize(new Dimension(500, 480));
		p_south.setPreferredSize(new Dimension(1000, 650));

		// 여백 설정
		p_search.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
		for (int i = 0; i < la_list.length; i++) {
			la_list[i].setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		}
		p_list1.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		p_cinema.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 0));
		la_time.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p_time.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		// 테두리 설정
		scroll.setBorder(BorderFactory.createLineBorder(new Color(103, 156, 220), 1));

		// 배경색 설정
		Color c = new Color(103, 156, 220);
		bt_search.setBackground(Color.WHITE);
		p_search.setBackground(c);
		p_list1.setBackground(c);
		p_list2.setBackground(c);
		p_cinema.setBackground(c);
		p_time.setBackground(c);

		// 레이아웃 설정
		p_north.setLayout(new BorderLayout());
		p_south.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		// 부착
		p_search.add(t_search);
		p_search.add(bt_search);
		for (int i = 0; i < la_list.length; i++) {
			if (i < la_list.length / 2) {
				p_list1.add(la_list[i]);
			} else {
				p_list2.add(la_list[i]);
			}
		}
		p_north.add(p_search, BorderLayout.NORTH);
		p_north.add(p_list1);
		p_north.add(p_list2, BorderLayout.SOUTH);

		p_cinema.add(la_cinema);
		p_time.add(la_time);
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_time.add(p_timeTable.get(i));
		}
		p_south.add(p_cinema, BorderLayout.WEST);
		p_south.add(p_time);

		add(p_north, BorderLayout.NORTH);
		add(scroll);

		// 검색창 클릭시 안내문구 삭제
		t_search.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t_search.setText("");
				t_search.setFocusable(true);
			}
		});

		// 검색창과 리스너 연결
		t_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				search_cinema = t_search.getText();
				if (key == KeyEvent.VK_ENTER) {
					for (int i = 0; i < la_list.length; i++) {
						if (search_cinema.equals(mainFrame.cinema[i])) { // 검색한 내용과 극장 이름이 같을 경우
							la_list[i].setForeground(Color.RED);
							cinema_name = mainFrame.cinema[i];
							getCinema(i); // 극장 사진 가져오기
							getMovieList(mainFrame.cinema[i]); // 상영중인 영화들 가져오기
							getTimePanel(); // 상영시간표 부착
						} else {
							la_list[i].setForeground(Color.BLACK);
						}
					}
					t_search.setText("");
				}
			}
		});

		// 검색 버튼과 리스너 연결
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < la_list.length; i++) {
					if (search_cinema.equals(mainFrame.cinema[i])) { // 검색한 내용과 극장 이름이 같을 경우
						la_list[i].setForeground(Color.RED);
						cinema_name = mainFrame.cinema[i];
						getCinema(i); // 극장 사진 가져오기
						getMovieList(mainFrame.cinema[i]); // 상영중인 영화들 가져오기
						getTimePanel(); // 상영시간표 부착
					} else {
						la_list[i].setForeground(Color.BLACK);
					}
				}
				t_search.setText("");
			}
		});

		// 극장 이름 라벨과 리스너 연결
		for (int a = 0; a < la_list.length; a++) {
			la_list[a].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < la_list.length; i++) {
						if (e.getSource().equals(la_list[i])) { // 선택한 극장과 같을 경우
							la_list[i].setForeground(Color.RED);
							cinema_name = mainFrame.cinema[i];
							getCinema(i); // 극장 사진 가져오기
							getMovieList(mainFrame.cinema[i]); // 상영중인 영화들 가져오기
							getTimePanel(); // 상영시간표 부착

						} else {
							la_list[i].setForeground(Color.BLACK);
						}
					}
				}
			});
		}

		sendReserve(); // 영화 정보 클릭시 예매화면으로 전환

		setBackground(new Color(103, 156, 220));
		setPreferredSize(new Dimension(1000, 620));
		setVisible(false);
	}

	// 극장 사진을 바꾸는 함수
	public void getCinema(int i) {
		cinema = imageLoad.getImage("cinema" + (i + 1) + ".png");
		cinema = cinema.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
		la_cinema.setIcon(new ImageIcon(cinema));
	}

	// 선택한 극장에서 상영중인 영화 목록을 가져오는 함수
	public void getMovieList(String cinema) {
		movieList.clear();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select movie from movies where cinema='" + cinema + "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String movie = rs.getString("movie");
				movieList.add(movie);
			}
			// System.out.println(movieList);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}

	// 상영시간표를 생성하는 함수
	public void getTimePanel() {
		// 이미 상영시간표가 존재한다면 화면에서 제거
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_time.remove(p_timeTable.get(i));
		}
		p_timeTable.clear();

		// 상영시간표 패널 생성
		for (int i = 0; i < movieList.size(); i++) {
			TimePanel timePanel = new TimePanel(mainFrame, cinema_name, movieList.get(i), i);
			p_timeTable.add(timePanel);
		}

		// 상영시간표 패널 부착
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_time.add(p_timeTable.get(i));
		}
		p_time.updateUI();
		sendReserve();
	}

	// 영화 정보 클릭시 예매화면으로 전환
	public void sendReserve() {
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_timeTable.get(i).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// 먼저 모든 극장과 영화의 이름을 검정색으로 바꾼다
					for (int i = 0; i < mainFrame.reservationMain.cinemaList.length; i++) {
						mainFrame.reservationMain.cinemaList[i].setForeground(Color.BLACK);
					}
					for (int i = 0; i < mainFrame.reservationMain.movieList.length; i++) {
						mainFrame.reservationMain.movieList[i].setForeground(Color.BLACK);
					}

					for (int a = 0; a < p_timeTable.size(); a++) {
						if (e.getSource() == p_timeTable.get(a)) {
							// 선택한 극장을 선택한채로 시작
							mainFrame.reservationMain.flag_cinema = true;
							mainFrame.reservationMain.selectCinema = cinema_name;
							for (int i = 0; i < mainFrame.reservationMain.cinemaList.length; i++) {
								if (cinema_name.equals(mainFrame.reservationMain.cinemaList[i].getText())) {
									mainFrame.reservationMain.cinemaList[i].setForeground(Color.RED);
								}
							}

							// 선택한 영화를 선택한채로 시작
							mainFrame.reservationMain.flag_movie = true;
							mainFrame.reservationMain.selectMovie = movieList.get(a);
							for (int i = 0; i < mainFrame.reservationMain.movieList.length; i++) {
								if (movieList.get(a).equals(mainFrame.reservationMain.movieList[i].getText())) {
									mainFrame.reservationMain.movieList[i].setForeground(Color.RED);
								}
							}
						}
					}

					// 예매 페이지만 보이도록 설정
					for (int i = 0; i < mainFrame.pages.length; i++) {
						mainFrame.pages[i].setVisible(false);
					}
					mainFrame.pages[2].setVisible(true);
				}
			});
		}
	}
}
