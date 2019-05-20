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
	public JLabel[] movieList = new JLabel[8]; // ��ȭ �̸� �迭
	public JLabel[] cinemaList = new JLabel[10]; // ���� �̸� �迭
	JLabel[] dateList = new JLabel[6]; // ��¥ �迭

	MainFrame mainFrame;
	public ReserveThread reserveThread;

	public boolean flag_movie = false; // ��ȭ ���� ����
	public boolean flag_cinema = false; // ���� ���� ����
	boolean flag_date = false; // ��¥ ���� ����

	public String selectMovie; // ������ ��ȭ �̸�
	public String selectCinema; // ������ ���� �̸�
	public String selectDate; // ������ ��¥
	public String selectTime; // ������ �� �ð�

	public boolean flag_thread = false; // ������ ���� ����
	int date = 22; // ���� ��¥
	String cinema_num = null; // ������ ��ȭ�� ���� �ѹ��� ����
	ArrayList<JLabel> timeTable = new ArrayList<JLabel>(); // �� �ð��� ���� �󺧵��� ���� �迭
	ArrayList<String> timeList = new ArrayList<String>(); // �� �ð����� ���� �迭
	ArrayList<Integer> selectSeatCnt = new ArrayList<Integer>(); // �̹� ���ŵ� �¼��� ���� ���� �迭

	public ReservationMain(MainFrame mainFrame) {
		this.mainFrame = mainFrame;

		p_movie = new JPanel(); // ��ȭ �г�
		p_cinema = new JPanel(); // ���� �г�
		p_date = new JPanel(); // ��¥ �г�
		p_time = new JPanel(); // �ð� �г�
		p_movieTitle = new JPanel(); // ��ȭ Ÿ��Ʋ �г�
		p_cinemaTitle = new JPanel(); // ���� Ÿ��Ʋ �г�
		p_dateTitle = new JPanel(); // ��¥ Ÿ��Ʋ �г�
		p_timeTitle = new JPanel(); // �ð� Ÿ��Ʋ �г�

		la_movie = new JLabel("��ȭ", SwingConstants.CENTER);
		la_cinema = new JLabel("����", SwingConstants.CENTER);
		la_date = new JLabel("��¥", SwingConstants.CENTER);
		la_time = new JLabel("�ð�", SwingConstants.CENTER);
		la_view = new JLabel("�� �ð�ǥ ����", SwingConstants.CENTER);
		la_cinemaNum = new JLabel(cinema_num, SwingConstants.CENTER);

		for (int i = 0; i < movieList.length; i++) {
			movieList[i] = new JLabel(mainFrame.movie[i]);
		}
		for (int i = 0; i < cinemaList.length; i++) {
			cinemaList[i] = new JLabel(mainFrame.cinema[i], SwingConstants.CENTER);
		}
		for (int i = 0; i < dateList.length; i++) {
			dateList[i] = new JLabel("2�� " + date + "��", SwingConstants.CENTER);
			date++;
		}
		for (int i = 0; i < timeList.size(); i++) {
			JLabel la_timeTable = new JLabel(timeList.get(i), SwingConstants.CENTER);
			timeTable.add(la_timeTable);
		}

		reserveThread = new ReserveThread(this);

		// ��Ʈ ����
		Font f1 = new Font("����", Font.BOLD, 20);
		la_movie.setFont(f1);
		la_cinema.setFont(f1);
		la_date.setFont(f1);
		la_time.setFont(f1);
		la_view.setFont(new Font("����", Font.BOLD, 18));
		la_cinemaNum.setFont(new Font("����", Font.BOLD, 16));

		la_movie.setForeground(Color.BLACK);
		la_cinema.setForeground(Color.BLACK);
		la_date.setForeground(Color.BLACK);
		la_time.setForeground(Color.BLACK);
		la_view.setForeground(Color.BLACK);
		la_cinemaNum.setForeground(Color.BLACK);

		Font f2 = new Font("����", Font.BOLD, 16);
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

		// ������ ����
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

		// �׵θ� ����
		p_movie.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		p_cinema.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		p_date.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
		p_time.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		// ���� ����
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

		// ����
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

		// ��ȭ �̸� �󺧰� ������ ����
		for (int a = 0; a < movieList.length; a++) {
			movieList[a].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < movieList.length; i++) {
						if (e.getSource().equals(movieList[i])) { // ������ ��ȭ �̸��� ���� ���
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

		// ���� �̸� �󺧰� ������ ����
		for (int a = 0; a < cinemaList.length; a++) {
			cinemaList[a].addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < cinemaList.length; i++) {
						if (e.getSource().equals(cinemaList[i])) { // ������ ���� �̸��� ���� ���
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

		// ��¥ �󺧰� ������ ����
		for (int a = 0; a < dateList.length; a++) {
			dateList[a].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < dateList.length; i++) {
						if (e.getSource().equals(dateList[i])) { // ������ ��¥�� ���� ���
							flag_date = true;
							selectDate = "2�� " + (i + 22) + "��";
							dateList[i].setForeground(Color.RED);
						} else {
							dateList[i].setForeground(Color.BLACK);
						}
					}
				}
			});
		}

		// �� �ð�ǥ ���� �󺧰� ������ ����
		la_view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!flag_movie) { // ��ȭ ������ ������ ���
					JOptionPane.showMessageDialog(mainFrame, "��ȭ�� �����ϼ���.");
				} else if (!flag_cinema) { // ���� ������ ������ ���
					JOptionPane.showMessageDialog(mainFrame, "������ �����ϼ���.");
				} else if (!flag_date) { // ��¥ ������ ������ ���
					JOptionPane.showMessageDialog(mainFrame, "��¥�� �����ϼ���.");
				} else {
					flag_thread = true;
					reserveThread.start(); // ������ �̿��Ͽ� �󿵽ð�ǥ�� ��� ������
					p_time.remove(la_view);
				}
			}
		});

		setBackground(new Color(125, 226, 97));
		setPreferredSize(new Dimension(1000, 620));
		setVisible(false);
	}

	// ���� �ѹ��� ������ �������� �Լ�
	public void getCinemaNum() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select cinema_num from movies where cinema='" + selectCinema + "' and movie='" + selectMovie
				+ "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) { // �˻� ����� �������� ���� ���
				cinema_num = null;
			} else {
				rs.next();
				cinema_num = rs.getString("cinema_num");
			}

			if (cinema_num == null) { // �ش��ϴ� ���� �ѹ��� �������� ���� ���
				// ���� �ѹ� ��� �ȳ� ���� ����
				p_time.remove(la_cinemaNum);
				la_cinemaNum = new JLabel("���Ͻô� �󿵽������� �����ϴ�", SwingConstants.CENTER);
				la_cinemaNum.setFont(new Font("����", Font.BOLD, 16));
				la_cinemaNum.setForeground(Color.BLACK);
				la_cinemaNum.setPreferredSize(new Dimension(260, 40));
				p_time.add(la_cinemaNum);

				// �󿵽ð�ǥ�� �����Ѵٸ� ȭ��� �迭���� ����
				for (int i = 0; i < timeTable.size(); i++) {
					p_time.remove(timeTable.get(i));
				}
				timeTable.clear();
				p_time.updateUI();

			} else {
				getTimeTable(); // �� �ð�ǥ ��������
				getTimeLabel(); // ���� �ѹ� �󺧰� �� �ð�ǥ �� ����
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}

	// �� �ð�ǥ�� �������� �Լ�
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

	// �̹� ������ �¼��� ���� �������� �Լ�
	public Integer getReserve(String time) {
		int count = 0;

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String sql = "select s_seat from selectInfo where s_movie='" + selectMovie + "' and s_cinema='" + selectCinema
				+ "' and s_date='" + selectDate + "' and s_time='" + time + "'";
		try {
			pstmt = mainFrame.con.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) { // �˻� ����� �������� ���� ���

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

	// ���� �ѹ��� �� �ð�ǥ ���� �����ϴ� �Լ�
	public void getTimeLabel() {
		// ���� �ѹ��� ���� ���� �����Ѵٸ� ���� �� ����
		p_time.remove(la_cinemaNum);
		la_cinemaNum = new JLabel(cinema_num, SwingConstants.CENTER);
		la_cinemaNum.setFont(new Font("����", Font.BOLD, 16));
		la_cinemaNum.setForeground(Color.BLACK);
		la_cinemaNum.setPreferredSize(new Dimension(260, 40));
		p_time.add(la_cinemaNum);

		// �� �ð�ǥ�� �̹� �����Ѵٸ� ȭ��� �迭���� ����
		for (int i = 0; i < timeTable.size(); i++) {
			p_time.remove(timeTable.get(i));
		}
		timeTable.clear();

		// �� �ð����� �����ͼ� �迭�� ���
		for (int i = 0; i < timeList.size(); i++) {
			JLabel la_timeTable = new JLabel(timeList.get(i) + "  " + (120 - getReserve(timeList.get(i))) + "��",
					SwingConstants.CENTER);
			timeTable.add(la_timeTable);
		}

		// �迭�� ���� �󺧵��� ȭ�鿡 ����
		for (int i = 0; i < timeTable.size(); i++) {
			timeTable.get(i).setFont(new Font("����", Font.BOLD, 16));
			timeTable.get(i).setForeground(Color.BLACK);
			timeTable.get(i).setPreferredSize(new Dimension(260, 40));
			p_time.add(timeTable.get(i));
		}
		p_time.updateUI();

		// �� �ð� Ŭ���� �ο� ���� ������ â ����
		for (int i = 0; i < timeTable.size(); i++) {
			timeTable.get(i).addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int a = 0; a < timeTable.size(); a++) {
						if (e.getSource() == timeTable.get(a)) {
							selectTime = timeList.get(a);
							timeTable.get(a).setForeground(Color.RED);

							ReserveMember member = new ReserveMember(ReservationMain.this);
							member.setBounds(840, 420, 350, 220);
							System.out.println("��ȭ: " + selectMovie + ", ����: " + selectCinema + ", ��¥: " + selectDate
									+ ", �ð�: " + selectTime);
						} else {
							timeTable.get(a).setForeground(Color.BLACK);
						}
					}
				};
			});
		}
	}
}
