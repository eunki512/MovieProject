package theater.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.main.MainFrame;
import theater.utils.ImageLoad;

public class MovieDetail extends JPanel {
	JPanel p_left, p_right, p_back, p_poster;
	JPanel p_title, p_name, p_reserveAll, p_empty, p_reserve, p_content;
	JLabel la_back, la_poster, la_name, la_reserve, la_content;
	Image poster, content;

	ImageLoad imageLoad;

	public MovieDetail(MainFrame mainFrame, int num) {
		p_left = new JPanel(); // 뒤로가기와 포스터를 붙일 패널
		p_right = new JPanel(); // 영화이름과 내용을 붙일 패널
		p_back = new JPanel(); // 뒤로가기 패널
		p_poster = new JPanel(); // 영화 포스터 패널
		p_title = new JPanel(); // 영화 이름과 예매하기를 붙일 패널
		p_name = new JPanel(); // 영화 이름 패널
		p_empty = new JPanel(); // 예매하기 위의 빈 패널
		p_reserve = new JPanel(); // 예매하기 패널
		p_reserveAll = new JPanel(); // 빈 패널과 예매하기를 붙일 패널
		p_content = new JPanel(); // 영화 내용 패널

		la_back = new JLabel("← 뒤로가기", SwingConstants.LEFT);
		la_poster = new JLabel();
		la_name = new JLabel(mainFrame.movie[num], SwingConstants.LEFT);
		la_reserve = new JLabel("예매하기");
		la_content = new JLabel();

		imageLoad = new ImageLoad();
		poster = imageLoad.getImage("poster" + (num + 1) + ".jpg");
		poster = poster.getScaledInstance(330, 440, Image.SCALE_SMOOTH);
		content = imageLoad.getImage("movie" + (num + 1) + ".png");
		content = content.getScaledInstance(550, 400, Image.SCALE_SMOOTH);

		la_poster.setIcon(new ImageIcon(poster));
		la_content.setIcon(new ImageIcon(content));

		// 폰트 설정
		la_back.setFont(new Font("굴림", Font.BOLD, 20));
		la_name.setFont(new Font("굴림", Font.BOLD, 24));
		la_reserve.setFont(new Font("굴림", Font.BOLD, 18));
		la_reserve.setForeground(Color.WHITE);

		// 사이즈 조절
		la_back.setPreferredSize(new Dimension(400, 50));
		la_name.setPreferredSize(new Dimension(450, 90));
		la_reserve.setPreferredSize(new Dimension(100, 20));
		p_back.setPreferredSize(new Dimension(400, 50));
		p_poster.setPreferredSize(new Dimension(400, 570));
		p_name.setPreferredSize(new Dimension(450, 85));
		p_empty.setPreferredSize(new Dimension(100, 55));
		p_reserve.setPreferredSize(new Dimension(100, 30));
		p_reserveAll.setPreferredSize(new Dimension(100, 85));
		p_title.setPreferredSize(new Dimension(600, 85));
		p_content.setPreferredSize(new Dimension(600, 535));
		p_left.setPreferredSize(new Dimension(400, 620));
		p_right.setPreferredSize(new Dimension(600, 620));

		// 여백 설정
		la_back.setBorder(BorderFactory.createEmptyBorder(0, 40, 20, 0));
		p_poster.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		p_title.setBorder(BorderFactory.createEmptyBorder(20, 25, 0, 25));
		la_reserve.setBorder(BorderFactory.createEmptyBorder(0, 12, 2, 0));
		p_content.setBorder(BorderFactory.createEmptyBorder(10, 0, 35, 0));
		p_left.setBorder(BorderFactory.createEmptyBorder(40, 0, 0, 0));
		p_right.setBorder(BorderFactory.createEmptyBorder(35, 0, 0, 0));

		// 테두리 설정
		la_poster.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		p_reserve.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		la_content.setBorder(BorderFactory.createLineBorder(Color.BLACK));

		// 배경색 설정
		Color c = new Color(239, 185, 84);
		p_back.setBackground(c);
		p_poster.setBackground(c);
		p_title.setBackground(c);
		p_name.setBackground(c);
		p_empty.setBackground(c);
		p_reserve.setBackground(Color.RED);
		p_content.setBackground(c);
		p_left.setBackground(c);
		p_right.setBackground(c);
		setBackground(c);

		// 레이아웃 설정
		p_title.setLayout(new BorderLayout());
		p_reserveAll.setLayout(new BorderLayout());
		p_left.setLayout(new BorderLayout());
		p_right.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		// 부착
		p_back.add(la_back);
		p_poster.add(la_poster);
		p_left.add(p_back, BorderLayout.NORTH);
		p_left.add(p_poster);

		p_name.add(la_name);
		p_reserve.add(la_reserve);
		p_title.add(p_name);
		p_title.add(p_reserveAll, BorderLayout.EAST);
		p_reserveAll.add(p_empty);
		p_reserveAll.add(p_reserve, BorderLayout.SOUTH);
		p_content.add(la_content);
		p_right.add(p_title, BorderLayout.NORTH);
		p_right.add(p_content);

		add(p_left, BorderLayout.WEST);
		add(p_right);

		// 뒤로가기 클릭시 영화 페이지로 돌아가도록
		la_back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				mainFrame.pages[0].setVisible(true);
			}
		});

		// 예매하기 클릭시 예매 페이지로 전환
		la_reserve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
				mainFrame.pages[2].setVisible(true);
				for (int i = 0; i < mainFrame.movie.length; i++) {
					// 선택한 포스터의 영화를 선택한채로 시작
					mainFrame.reservationMain.movieList[i].setForeground(Color.BLACK);
					mainFrame.reservationMain.flag_movie = true;
					mainFrame.reservationMain.selectMovie = mainFrame.movie[num];
					mainFrame.reservationMain.movieList[num].setForeground(Color.RED);
				}
			}
		});

		setPreferredSize(new Dimension(1000, 620));
		setVisible(false);
	}
}
