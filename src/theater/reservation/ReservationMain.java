package theater.reservation;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.main.MainFrame;

public class ReservationMain extends JPanel {
	JPanel p_movie, p_cinema, p_date;
	JPanel p_movieTitle, p_cinemaTitle, p_dateTitle, p_timeTitle;
	public JPanel p_time;
	JLabel la_movie, la_cinema, la_date, la_time, la_cinemaNum;
	public JLabel la_view;
	public JLabel[] movieList = new JLabel[8]; // 영화 이름 배열
	public JLabel[] cinemaList = new JLabel[10]; // 극장 이름 배열
	JLabel[] dateList = new JLabel[6]; // 날짜 배열

	MainFrame mainFrame;
	public ReserveThread reserveThread;

	public boolean flag_movie = false; // 영화 선택 여부
	public boolean flag_cinema = false; // 극장 선택 여부
	boolean flag_date = false; // 날짜 선택 여부

	public String selectMovie; // 선택한 영화 이름
	public String selectCinema; // 선택한 극장 이름
	public String selectDate; // 선택한 날짜
	public String selectTime; // 선택한 상영 시간

	public boolean flag_thread = false; // 쓰레드 동작 여부
	int date = 22; // 오늘 날짜
	String cinema_num = null; // 선택한 영화의 극장 넘버와 층수
	ArrayList<JLabel> timeTable = new ArrayList<JLabel>(); // 상영 시간을 붙인 라벨들을 담은 배열
	ArrayList<String> timeList = new ArrayList<String>(); // 상영 시간들을 담은 배열
	ArrayList<Integer> selectSeatCnt = new ArrayList<Integer>(); // 이미 예매된 좌석의 수를 담은 배열

	public ReservationMain(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		p_movie = new JPanel(); // 영화 패널
		p_cinema = new JPanel(); // 극장 패널
		p_date = new JPanel(); // 날짜 패널
		p_time = new JPanel(); // 시간 패널
		p_movieTitle = new JPanel(); // 영화 타이틀 패널
		p_cinemaTitle = new JPanel(); // 극장 타이틀 패널
		p_dateTitle = new JPanel(); // 날짜 타이틀 패널
		p_timeTitle = new JPanel(); // 시간 타이틀 패널

		la_movie = new JLabel("영화", SwingConstants.CENTER);
		la_cinema = new JLabel("극장", SwingConstants.CENTER);
		la_date = new JLabel("날짜", SwingConstants.CENTER);
		la_time = new JLabel("시간", SwingConstants.CENTER);
		la_view = new JLabel("상영 시간표 보기", SwingConstants.CENTER);
		la_cinemaNum = new JLabel(cinema_num, SwingConstants.CENTER);

		for (int i = 0; i < movieList.length; i++) {
			movieList[i] = new JLabel(mainFrame.movie[i]);
		}
		for (int i = 0; i < cinemaList.length; i++) {
			cinemaList[i] = new JLabel(mainFrame.cinema[i], SwingConstants.CENTER);
		}
		for (int i = 0; i < dateList.length; i++) {
			dateList[i] = new JLabel("2월 " + date + "일", SwingConstants.CENTER);
			date++;
		}
		for (int i = 0; i < timeList.size(); i++) {
			JLabel la_timeTable = new JLabel(timeList.get(i), SwingConstants.CENTER);
			timeTable.add(la_timeTable);
		}

		reserveThread = new ReserveThread(this);

		// 폰트 설정
		Font f1 = new Font("굴림", Font.BOLD, 20);
		la_movie.setFont(f1);
		la_cinema.setFont(f1);
		la_date.setFont(f1);
		la_time.setFont(f1);
		la_view.setFont(new Font("굴림", Font.BOLD, 18));
		la_cinemaNum.setFont(new Font("굴림", Font.BOLD, 16));

		la_movie.setForeground(Color.BLACK);
		la_cinema.setForeground(Color.BLACK);
		la_date.setForeground(Color.BLACK);
		la_time.setForeground(Color.BLACK);
		la_view.setForeground(Color.BLACK);
		la_cinemaNum.setForeground(Color.BLACK);

		Font f2 = new Font("굴림", Font.BOLD, 16);
		for (int i = 0; i < movieList.length; i++) {
			movieList[i].setFont(f2);
			movieList[i].setForeground(Color.BLACK);
		}
		for (int i = 0; i < cinemaList.length; i++) {
			cinemaList[i].setFont(f2);
			cinemaList[i].setForeground(Color.BLACK);
		}
		for (int i = 0; i < dateList.length; i++) {
			dateList[i].setFont(f2);
			dateList[i].setForeground(Color.BLACK);
		}
		for (int i = 0; i < timeTable.size(); i++) {
			timeTable.get(i).setFont(f2);
			timeTable.get(i).setForeground(Color.BLACK);
		}

		// 사이즈 조절
		la_movie.setPreferredSize(new Dimension(286, 45));
		la_cinema.setPreferredSize(new Dimension(156, 45));
		la_date.setPreferredSize(new Dimension(156, 45));
		la_time.setPreferredSize(new Dimension(276, 45));
		la_view.setPreferredSize(new Dimension(260, 40));
		la_cinemaNum.setPreferredSize(new Dimension(260, 40));

		for (int i = 0; i < movieList.length; i++) {
			movieList[i].setPreferredSize(new Dimension(250, 40));
		}
		for (int i = 0; i < cinemaList.length; i++) {
			cinemaList[i].setPreferredSize(new Dimension(160, 40));
		}
		for (int i = 0; i < dateList.length; i++) {
			dateList[i].setPreferredSize(new Dimension(160, 40));
		}
		for (int i = 0; i < timeTable.size(); i++) {
			timeTable.get(i).setPreferredSize(new Dimension(260, 40));
		}

		p_movie.setPreferredSize(new Dimension(300, 560));
		p_cinema.setPreferredSize(new Dimension(170, 560));
		p_date.setPreferredSize(new Dimension(170, 560));
		p_time.setPreferredSize(new Dimension(290, 560));

		// 테두리 설정
		p_movie.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		p_cinema.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		p_date.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		p_time.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		// 배경색 설정
		Color c1 = new Color(54, 148, 27);
		p_movieTitle.setBackground(c1);
		p_cinemaTitle.setBackground(c1);
		p_dateTitle.setBackground(c1);
		p_timeTitle.setBackground(c1);

		Color c2 = new Color(125, 226, 97);
		p_movie.setBackground(c2);
		p_cinema.setBackground(c2);
		p_date.setBackground(c2);
		p_time.setBackground(c2);

		// 부착
		p_movieTitle.add(la_movie);
		p_cinemaTitle.add(la_cinema);
		p_dateTitle.add(la_date);
		p_timeTitle.add(la_time);

		p_movie.add(p_movieTitle);
		for (int i = 0; i < movieList.length; i++) {
			p_movie.add(movieList[i]);
		}
		p_cinema.add(p_cinemaTitle);
		for (int i = 0; i < cinemaList.length; i++) {
			p_cinema.add(cinemaList[i]);
		}
		p_date.add(p_dateTitle);
		for (int i = 0; i < dateList.length; i++) {
			p_date.add(dateList[i]);
		}
		p_time.add(p_timeTitle);
		p_time.add(la_view);
		p_time.add(la_cinemaNum);
		for (int i = 0; i < timeTable.size(); i++) {
			p_time.add(timeTable.get(i));
		}

		add(p_movie);
		add(p_cinema);
		add(p_date);
		add(p_time);

		// 영화 이름 라벨과 리스너 연결
		for (int a = 0; a < movieList.length; a++) {
			movieList[a].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < movieList.length; i++) {
						if (e.getSource().equals(movieList[i])) { // 선택한 영화 이름의 라벨일 경우
							flag_movie = true;
							selectMovie = mainFrame.movie[i];
							movieList[i].setForeground(Color.RED);
						} else {
							movieList[i].setForeground(Color.BLACK);
						}
					}
				}
			});
		}

		// 극장 이름 라벨과 리스너 연결
		for (int a = 0; a < cinemaList.length; a++) {
			cinemaList[a].addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < cinemaList.length; i++) {
						if (e.getSource().equals(cinemaList[i])) { // 선택한 극장 이름의 라벨일 경우
							flag_cinema = true;
							selectCinema = mainFrame.cinema[i];
							cinemaList[i].setForeground(Color.RED);
						} else {
							cinemaList[i].setForeground(Color.BLACK);
						}
					}
				}
			});
		}

		// 날짜 라벨과 리스너 연결
		for (int a = 0; a < dateList.length; a++) {
			dateList[a].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < dateList.length; i++) {
						if (e.getSource().equals(dateList[i])) { // 선택한 날짜의 라벨일 경우
							flag_date = true;
							selectDate = "2월 " + (i + 22) + "일";
							dateList[i].setForeground(Color.RED);
						} else {
							dateList[i].setForeground(Color.BLACK);
						}
					}
				}
			});
		}

		// 상영 시간표 보기 라벨과 리스너 연결
		la_view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!flag_movie) { // 영화 선택을 안했을 경우
					JOptionPane.showMessageDialog(mainFrame, "영화를 선택하세요.");
				} else if (!flag_cinema) { // 극장 선택을 안했을 경우
					JOptionPane.showMessageDialog(mainFrame, "극장을 선택하세요.");
				} else if (!flag_date) { // 날짜 선택을 안했을 경우
					JOptionPane.showMessageDialog(mainFrame, "날짜를 선택하세요.");
				} else {
					flag_thread = true;
					reserveThread.start(); // 쓰레드 이용하여 상영시간표를 계속 가져옴
					p_time.remove(la_view);
				}
			}
		});

		setBackground(new Color(125, 226, 97));
		setPreferredSize(new Dimension(1000, 620));
		setVisible(false);
	}

	// 극장 넘버와 층수를 가져오는 함수
	public void getCinemaNum() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select cinema_num from movies where cinema='" + selectCinema + "' and movie='" + selectMovie
				+ "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) { // 검색 결과가 존재하지 않을 경우
				cinema_num = null;
			} else {
				rs.next();
				cinema_num = rs.getString("cinema_num");
			}

			if (cinema_num == null) { // 해당하는 극장 넘버가 존재하지 않을 경우
				// 극장 넘버 대신 안내 문구 부착
				p_time.remove(la_cinemaNum);
				la_cinemaNum = new JLabel("원하시는 상영스케줄이 없습니다", SwingConstants.CENTER);
				la_cinemaNum.setFont(new Font("굴림", Font.BOLD, 16));
				la_cinemaNum.setForeground(Color.BLACK);
				la_cinemaNum.setPreferredSize(new Dimension(260, 40));
				p_time.add(la_cinemaNum);

				// 상영시간표가 존재한다면 화면과 배열에서 제거
				for (int i = 0; i < timeTable.size(); i++) {
					p_time.remove(timeTable.get(i));
				}
				timeTable.clear();
				p_time.updateUI();

			} else {
				getTimeTable(); // 상영 시간표 가져오기
				getTimeLabel(); // 극장 넘버 라벨과 상영 시간표 라벨 부착
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}

	// 상영 시간표를 가져오는 함수
	public void getTimeTable() {
		timeList.clear();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select time from movies join timetable on movies.movie = timetable.movie " + "where cinema='"
				+ selectCinema + "' and timetable.movie='" + selectMovie + "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String time = rs.getString("time");
				timeList.add(time);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}

	// 이미 예매한 좌석의 수를 가져오는 함수
	public Integer getReserve(String time) {
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select s_seat from selectInfo where s_movie='" + selectMovie + "' and s_cinema='" + selectCinema
				+ "' and s_date='" + selectDate + "' and s_time='" + time + "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) { // 검색 결과가 존재하지 않을 경우

			} else {
				rs.last();
				count = rs.getRow();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
		return count;
	}

	// 극장 넘버와 상영 시간표 라벨을 부착하는 함수
	public void getTimeLabel() {
		// 극장 넘버와 층수 라벨이 존재한다면 제거 후 부착
		p_time.remove(la_cinemaNum);
		la_cinemaNum = new JLabel(cinema_num, SwingConstants.CENTER);
		la_cinemaNum.setFont(new Font("굴림", Font.BOLD, 16));
		la_cinemaNum.setForeground(Color.BLACK);
		la_cinemaNum.setPreferredSize(new Dimension(260, 40));
		p_time.add(la_cinemaNum);

		// 상영 시간표가 이미 존재한다면 화면과 배열에서 제거
		for (int i = 0; i < timeTable.size(); i++) {
			p_time.remove(timeTable.get(i));
		}
		timeTable.clear();

		// 상영 시간들을 가져와서 배열에 담기
		for (int i = 0; i < timeList.size(); i++) {
			JLabel la_timeTable = new JLabel(timeList.get(i) + "  " + (120 - getReserve(timeList.get(i))) + "석",
					SwingConstants.CENTER);
			timeTable.add(la_timeTable);
		}

		// 배열에 담은 라벨들을 화면에 부착
		for (int i = 0; i < timeTable.size(); i++) {
			timeTable.get(i).setFont(new Font("굴림", Font.BOLD, 16));
			timeTable.get(i).setForeground(Color.BLACK);
			timeTable.get(i).setPreferredSize(new Dimension(260, 40));
			p_time.add(timeTable.get(i));
		}
		p_time.updateUI();

		// 상영 시간 클릭시 인원 선택 페이지 창 띄우기
		for (int i = 0; i < timeTable.size(); i++) {
			timeTable.get(i).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int a = 0; a < timeTable.size(); a++) {
						if (e.getSource() == timeTable.get(a)) {
							selectTime = timeList.get(a);
							timeTable.get(a).setForeground(Color.RED);

							ReserveMember member = new ReserveMember(ReservationMain.this);
							member.setBounds(840, 420, 350, 220);
							System.out.println("영화: " + selectMovie + ", 극장: " + selectCinema + ", 날짜: " + selectDate
									+ ", 시간: " + selectTime);
						} else {
							timeTable.get(a).setForeground(Color.BLACK);
						}
					}
				};
			});
		}
	}
}
