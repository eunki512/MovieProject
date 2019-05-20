package theater.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import theater.utils.ImageLoad;

public class MyTicket extends JFrame {
	JPanel p_wrapper, p_title, p_poster, p_info, p_movie, p_time, p_seat;
	JLabel la_title, la_x, la_poster;
	JLabel la_cinema, la_movie, la_time, la_date, la_seat, la_seatInfo;
	Image poster;

	ImageLoad imageLoad;
	MainFrame mainFrame;

	String cinema_num; // 예매한 영화의 상영관과 층수

	public MyTicket(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		p_wrapper = new JPanel(); // 전체 패널
		p_title = new JPanel(); // 마이 티켓 패널
		p_poster = new JPanel(); // 포스터 패널
		p_info = new JPanel(); // 정보들을 담는 패널
		p_movie = new JPanel(); // 영화 이름 패널
		p_time = new JPanel(); // 상영 시간 패널
		p_seat = new JPanel(); // 좌석 정보 패널

		la_title = new JLabel("My Ticket");
		la_x = new JLabel("X");

		String movie = mainFrame.reserveInfo.get(0); // 예매한 영화
		String cinema = mainFrame.reserveInfo.get(1); // 예매한 극장
		String date = mainFrame.reserveInfo.get(2); // 예매한 날짜
		String time = mainFrame.reserveInfo.get(3); // 예매한 시간
		String seat = mainFrame.reserveInfo.get(4); // 예매한 좌석

		imageLoad = new ImageLoad();

		// 예매한 영화에 해당하는 포스터 가져오기
		for (int i = 0; i < mainFrame.movie.length; i++) {
			if (movie.equals(mainFrame.movie[i])) {
				poster = imageLoad.getImage("poster" + (i + 1) + ".jpg");
			}
		}
		poster = poster.getScaledInstance(215, 265, Image.SCALE_SMOOTH);
		la_poster = new JLabel(new ImageIcon(poster));

		getCinemaNum(cinema, movie);
		la_cinema = new JLabel(cinema + " / " + cinema_num);

		la_movie = new JLabel(movie);
		la_time = new JLabel("상영시간");

		// 상영시간은 2시간으로 설정
		String time_hour_str = time.substring(0, 2); // 시작 시간
		String time_min_str = time.substring(3, 5); // 시작 분
		int time_hour_int = Integer.parseInt(time_hour_str);
		int end_hour_int = time_hour_int + 2;
		String end_hour_str = ""; // 끝나는 시간
		if (end_hour_int < 10) {
			end_hour_str = "0" + Integer.toString(end_hour_int);
		} else if (end_hour_int >= 24) {
			end_hour_str = "0" + Integer.toString(end_hour_int - 24);
		} else {
			end_hour_str = Integer.toString(end_hour_int);
		}
		String endTime = end_hour_str + ":" + time_min_str; // 끝나는 시간과 분

		la_date = new JLabel("2019년 " + date + "  " + time + " - " + endTime);

		la_seat = new JLabel("좌석정보");
		la_seatInfo = new JLabel(seat);

		// 폰트 설정
		la_title.setFont(new Font("굴림", Font.BOLD, 22));
		la_x.setFont(new Font("굴림", Font.BOLD, 17));
		la_cinema.setFont(new Font("굴림", Font.PLAIN, 17));
		la_movie.setFont(new Font("굴림", Font.BOLD, 18));
		la_time.setFont(new Font("굴림", Font.PLAIN, 17));
		la_date.setFont(new Font("굴림", Font.BOLD, 18));
		la_seat.setFont(new Font("굴림", Font.PLAIN, 17));
		la_seatInfo.setFont(new Font("굴림", Font.BOLD, 18));
		la_cinema.setForeground(Color.BLACK);
		la_movie.setForeground(Color.BLACK);
		la_time.setForeground(Color.BLACK);
		la_date.setForeground(Color.BLACK);
		la_seat.setForeground(Color.BLACK);
		la_seatInfo.setForeground(Color.BLACK);

		// 사이즈 조절
		p_title.setPreferredSize(new Dimension(350, 50));
		p_poster.setPreferredSize(new Dimension(350, 300));
		Dimension d = new Dimension(350, 25);
		la_cinema.setPreferredSize(d);
		la_movie.setPreferredSize(d);
		la_time.setPreferredSize(d);
		la_date.setPreferredSize(d);
		la_seat.setPreferredSize(d);
		la_seatInfo.setPreferredSize(d);
		p_movie.setPreferredSize(new Dimension(350, 70));
		p_time.setPreferredSize(new Dimension(350, 70));
		p_seat.setPreferredSize(new Dimension(350, 70));
		p_info.setPreferredSize(new Dimension(350, 230));
		p_wrapper.setPreferredSize(new Dimension(350, 580));

		// 여백 설정
		la_title.setBorder(BorderFactory.createEmptyBorder(0, 125, 5, 0));
		la_x.setBorder(BorderFactory.createEmptyBorder(0, 90, 30, 0));
		p_poster.setBorder(BorderFactory.createEmptyBorder(15, 5, 0, 0));
		la_cinema.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		la_movie.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		la_time.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		la_date.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		la_seat.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		la_seatInfo.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

		// 테두리 설정
		p_title.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		la_poster.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p_wrapper.setBorder(BorderFactory.createLineBorder((Color.GRAY), 2));

		// 배경색 설정
		p_title.setBackground(Color.YELLOW);
		p_poster.setBackground(Color.WHITE);
		p_movie.setBackground(Color.WHITE);
		p_time.setBackground(Color.WHITE);
		p_seat.setBackground(Color.WHITE);
		p_info.setBackground(Color.WHITE);

		// 레이아웃 설정
		p_wrapper.setLayout(new BorderLayout());

		// 부착
		p_title.add(la_title);
		p_title.add(la_x);
		p_poster.add(la_poster);

		p_movie.add(la_cinema);
		p_movie.add(la_movie);
		p_time.add(la_time);
		p_time.add(la_date);
		p_seat.add(la_seat);
		p_seat.add(la_seatInfo);

		p_info.add(p_movie);
		p_info.add(p_time);
		p_info.add(p_seat);

		p_wrapper.add(p_title, BorderLayout.NORTH);
		p_wrapper.add(p_poster);
		p_wrapper.add(p_info, BorderLayout.SOUTH);
		add(p_wrapper);

		// 'x' 클릭시 마이티켓 창을 닫는다
		la_x.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		setSize(350, 580);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	// 상영관과 층수를 가져오는 함수
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

}
