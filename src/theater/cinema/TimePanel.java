package theater.cinema;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.main.MainFrame;

public class TimePanel extends JPanel {
	JPanel p_title, p_cinema, p_time;
	JLabel la_title, la_cinema, la_time;

	MainFrame mainFrame;

	String cinema_num; // 극장 넘버와 층수
	ArrayList<String> timeList = new ArrayList<String>(); // 해당 영화의 상영시간들을 담는 배열

	public TimePanel(MainFrame mainFrame, String cinema, String movie, int num) {
		this.mainFrame = mainFrame;

		p_title = new JPanel(); // 영화 제목 패널
		p_cinema = new JPanel(); // 극장 넘버,층수,좌석수 패널
		p_time = new JPanel(); // 상영시간 패널

		la_title = new JLabel(movie, SwingConstants.LEFT);

		getCinemaNum(cinema, movie); // 해당 영화의 극장 넘버와 층수 가져오기
		la_cinema = new JLabel(cinema_num + " ｜ 총 120석", SwingConstants.LEFT);

		getTimeTable(movie); // 해당 영화의 상영 시간들 가져오기
		String time = "";
		for (int i = 0; i < timeList.size(); i++) {
			time += timeList.get(i) + "   ";
		}
		la_time = new JLabel(time, SwingConstants.LEFT);

		// 폰트 설정
		la_title.setFont(new Font("굴림", Font.BOLD, 16));
		la_cinema.setFont(new Font("굴림", Font.PLAIN, 15));
		la_time.setFont(new Font("굴림", Font.PLAIN, 17));

		// 사이즈 조절
		la_title.setPreferredSize(new Dimension(470, 25));
		p_title.setPreferredSize(new Dimension(480, 25));
		la_cinema.setPreferredSize(new Dimension(470, 25));
		p_cinema.setPreferredSize(new Dimension(480, 30));
		la_time.setPreferredSize(new Dimension(470, 30));
		p_time.setPreferredSize(new Dimension(480, 30));
		setPreferredSize(new Dimension(480, 90));

		// 테두리 설정
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		// 배경색 설정
		p_title.setBackground(Color.WHITE);
		p_cinema.setBackground(Color.WHITE);
		p_time.setBackground(Color.WHITE);
		setBackground(new Color(103, 156, 220));

		// 레이아웃 설정
		setLayout(new BorderLayout());

		// 부착
		p_title.add(la_title);
		p_cinema.add(la_cinema);
		p_time.add(la_time);

		add(p_title, BorderLayout.NORTH);
		add(p_cinema);
		add(p_time, BorderLayout.SOUTH);
	}

	// 해당 영화의 상영관과 층수를 가져오는 함수
	public void getCinemaNum(String cinema, String movie) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select cinema_num from movies where cinema='" + cinema + "' and movie='" + movie + "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			rs.next();
			cinema_num = rs.getString("cinema_num");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}

	// 해당 영화의 상영 시간들을 가져오는 함수
	public void getTimeTable(String movie) {
		timeList.clear();

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select time from timetable where movie='" + movie + "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String time = rs.getString("time");
				timeList.add(time);
			}
			// System.out.println(timeList);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}
}
