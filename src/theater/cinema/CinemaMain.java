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
	JLabel[] la_list = new JLabel[10]; // ���� �̸� �� �迭
	JLabel la_cinema, la_time;
	JTextField t_search;
	JButton bt_search;
	JScrollPane scroll;
	Image cinema;

	ImageLoad imageLoad;
	MainFrame mainFrame;

	String cinema_name = "KGV ����"; // ������ ���� �̸�
	String search_cinema; // �˻��� ���� �̸�
	ArrayList<JPanel> p_timeTable = new ArrayList<JPanel>(); // timePanel�� ��� �迭
	ArrayList<String> movieList = new ArrayList<String>(); // ������ ���忡�� ������ ��ȭ��

	public CinemaMain(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		p_search = new JPanel(); // �˻� �г�
		p_list1 = new JPanel(); // ���� �̸����� ���� �г�1
		p_list2 = new JPanel(); // ���� �̸����� ���� �г�2
		p_north = new JPanel(); // �˻��гΰ� �����̸��г��� ���� �г�
		p_cinema = new JPanel(); // ���� ���� �г�
		p_time = new JPanel(); // �� �ð�ǥ �г�
		p_south = new JPanel(); // ��������гΰ� �󿵽ð�ǥ�г��� ���� �г�

		getMovieList(mainFrame.cinema[0]); // ó������ ù��° ������ ��������
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
		la_time = new JLabel("�� �ð�ǥ");

		t_search = new JTextField("���� �̸��� �˻��ϼ���.") { // JTextField �׵θ� ���ֱ�
			public void setBorder(Border border) {
			}
		};
		bt_search = new JButton("�˻�");
		scroll = new JScrollPane(p_south);

		// ��Ʈ ����
		t_search.setFont(new Font("����", Font.PLAIN, 14));
		for (int i = 0; i < la_list.length; i++) {
			la_list[i].setFont(new Font("����", Font.BOLD, 16));
			if (i == 0) {
				la_list[i].setForeground(Color.RED);
			} else {
				la_list[i].setForeground(Color.BLACK);
			}
		}
		la_time.setFont(new Font("����", Font.BOLD, 22));
		la_time.setForeground(Color.BLACK);

		// ������ ����
		t_search.setPreferredSize(new Dimension(400, 40));
		p_search.setPreferredSize(new Dimension(1000, 60));
		p_list1.setPreferredSize(new Dimension(1000, 40));
		p_list2.setPreferredSize(new Dimension(1000, 40));
		p_north.setPreferredSize(new Dimension(1000, 140));
		p_cinema.setPreferredSize(new Dimension(450, 480));
		p_time.setPreferredSize(new Dimension(500, 480));
		p_south.setPreferredSize(new Dimension(1000, 650));

		// ���� ����
		p_search.setBorder(BorderFactory.createEmptyBorder(10, 20, 0, 0));
		for (int i = 0; i < la_list.length; i++) {
			la_list[i].setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
		}
		p_list1.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
		p_cinema.setBorder(BorderFactory.createEmptyBorder(50, 20, 0, 0));
		la_time.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		p_time.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		// �׵θ� ����
		scroll.setBorder(BorderFactory.createLineBorder(new Color(103, 156, 220), 1));

		// ���� ����
		Color c = new Color(103, 156, 220);
		bt_search.setBackground(Color.WHITE);
		p_search.setBackground(c);
		p_list1.setBackground(c);
		p_list2.setBackground(c);
		p_cinema.setBackground(c);
		p_time.setBackground(c);

		// ���̾ƿ� ����
		p_north.setLayout(new BorderLayout());
		p_south.setLayout(new BorderLayout());
		setLayout(new BorderLayout());

		// ����
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

		// �˻�â Ŭ���� �ȳ����� ����
		t_search.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				t_search.setText("");
				t_search.setFocusable(true);
			}
		});

		// �˻�â�� ������ ����
		t_search.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				int key = e.getKeyCode();
				search_cinema = t_search.getText();
				if (key == KeyEvent.VK_ENTER) {
					for (int i = 0; i < la_list.length; i++) {
						if (search_cinema.equals(mainFrame.cinema[i])) { // �˻��� ����� ���� �̸��� ���� ���
							la_list[i].setForeground(Color.RED);
							cinema_name = mainFrame.cinema[i];
							getCinema(i); // ���� ���� ��������
							getMovieList(mainFrame.cinema[i]); // ������ ��ȭ�� ��������
							getTimePanel(); // �󿵽ð�ǥ ����
						} else {
							la_list[i].setForeground(Color.BLACK);
						}
					}
					t_search.setText("");
				}
			}
		});

		// �˻� ��ư�� ������ ����
		bt_search.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < la_list.length; i++) {
					if (search_cinema.equals(mainFrame.cinema[i])) { // �˻��� ����� ���� �̸��� ���� ���
						la_list[i].setForeground(Color.RED);
						cinema_name = mainFrame.cinema[i];
						getCinema(i); // ���� ���� ��������
						getMovieList(mainFrame.cinema[i]); // ������ ��ȭ�� ��������
						getTimePanel(); // �󿵽ð�ǥ ����
					} else {
						la_list[i].setForeground(Color.BLACK);
					}
				}
				t_search.setText("");
			}
		});

		// ���� �̸� �󺧰� ������ ����
		for (int a = 0; a < la_list.length; a++) {
			la_list[a].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < la_list.length; i++) {
						if (e.getSource().equals(la_list[i])) { // ������ ����� ���� ���
							la_list[i].setForeground(Color.RED);
							cinema_name = mainFrame.cinema[i];
							getCinema(i); // ���� ���� ��������
							getMovieList(mainFrame.cinema[i]); // ������ ��ȭ�� ��������
							getTimePanel(); // �󿵽ð�ǥ ����

						} else {
							la_list[i].setForeground(Color.BLACK);
						}
					}
				}
			});
		}

		sendReserve(); // ��ȭ ���� Ŭ���� ����ȭ������ ��ȯ

		setBackground(new Color(103, 156, 220));
		setPreferredSize(new Dimension(1000, 620));
		setVisible(false);
	}

	// ���� ������ �ٲٴ� �Լ�
	public void getCinema(int i) {
		cinema = imageLoad.getImage("cinema" + (i + 1) + ".png");
		cinema = cinema.getScaledInstance(400, 350, Image.SCALE_SMOOTH);
		la_cinema.setIcon(new ImageIcon(cinema));
	}

	// ������ ���忡�� ������ ��ȭ ����� �������� �Լ�
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

	// �󿵽ð�ǥ�� �����ϴ� �Լ�
	public void getTimePanel() {
		// �̹� �󿵽ð�ǥ�� �����Ѵٸ� ȭ�鿡�� ����
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_time.remove(p_timeTable.get(i));
		}
		p_timeTable.clear();

		// �󿵽ð�ǥ �г� ����
		for (int i = 0; i < movieList.size(); i++) {
			TimePanel timePanel = new TimePanel(mainFrame, cinema_name, movieList.get(i), i);
			p_timeTable.add(timePanel);
		}

		// �󿵽ð�ǥ �г� ����
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_time.add(p_timeTable.get(i));
		}
		p_time.updateUI();
		sendReserve();
	}

	// ��ȭ ���� Ŭ���� ����ȭ������ ��ȯ
	public void sendReserve() {
		for (int i = 0; i < p_timeTable.size(); i++) {
			p_timeTable.get(i).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					// ���� ��� ����� ��ȭ�� �̸��� ���������� �ٲ۴�
					for (int i = 0; i < mainFrame.reservationMain.cinemaList.length; i++) {
						mainFrame.reservationMain.cinemaList[i].setForeground(Color.BLACK);
					}
					for (int i = 0; i < mainFrame.reservationMain.movieList.length; i++) {
						mainFrame.reservationMain.movieList[i].setForeground(Color.BLACK);
					}

					for (int a = 0; a < p_timeTable.size(); a++) {
						if (e.getSource() == p_timeTable.get(a)) {
							// ������ ������ ������ä�� ����
							mainFrame.reservationMain.flag_cinema = true;
							mainFrame.reservationMain.selectCinema = cinema_name;
							for (int i = 0; i < mainFrame.reservationMain.cinemaList.length; i++) {
								if (cinema_name.equals(mainFrame.reservationMain.cinemaList[i].getText())) {
									mainFrame.reservationMain.cinemaList[i].setForeground(Color.RED);
								}
							}

							// ������ ��ȭ�� ������ä�� ����
							mainFrame.reservationMain.flag_movie = true;
							mainFrame.reservationMain.selectMovie = movieList.get(a);
							for (int i = 0; i < mainFrame.reservationMain.movieList.length; i++) {
								if (movieList.get(a).equals(mainFrame.reservationMain.movieList[i].getText())) {
									mainFrame.reservationMain.movieList[i].setForeground(Color.RED);
								}
							}
						}
					}

					// ���� �������� ���̵��� ����
					for (int i = 0; i < mainFrame.pages.length; i++) {
						mainFrame.pages[i].setVisible(false);
					}
					mainFrame.pages[2].setVisible(true);
				}
			});
		}
	}
}
