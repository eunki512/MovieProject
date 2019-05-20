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

	String cinema_num; // ���� �ѹ��� ����
	ArrayList<String> timeList = new ArrayList<String>(); // �ش� ��ȭ�� �󿵽ð����� ��� �迭

	public TimePanel(MainFrame mainFrame, String cinema, String movie, int num) {
		this.mainFrame = mainFrame;

		p_title = new JPanel(); // ��ȭ ���� �г�
		p_cinema = new JPanel(); // ���� �ѹ�,����,�¼��� �г�
		p_time = new JPanel(); // �󿵽ð� �г�

		la_title = new JLabel(movie, SwingConstants.LEFT);

		getCinemaNum(cinema, movie); // �ش� ��ȭ�� ���� �ѹ��� ���� ��������
		la_cinema = new JLabel(cinema_num + " �� �� 120��", SwingConstants.LEFT);

		getTimeTable(movie); // �ش� ��ȭ�� �� �ð��� ��������
		String time = "";
		for (int i = 0; i < timeList.size(); i++) {
			time += timeList.get(i) + "   ";
		}
		la_time = new JLabel(time, SwingConstants.LEFT);

		// ��Ʈ ����
		la_title.setFont(new Font("����", Font.BOLD, 16));
		la_cinema.setFont(new Font("����", Font.PLAIN, 15));
		la_time.setFont(new Font("����", Font.PLAIN, 17));

		// ������ ����
		la_title.setPreferredSize(new Dimension(470, 25));
		p_title.setPreferredSize(new Dimension(480, 25));
		la_cinema.setPreferredSize(new Dimension(470, 25));
		p_cinema.setPreferredSize(new Dimension(480, 30));
		la_time.setPreferredSize(new Dimension(470, 30));
		p_time.setPreferredSize(new Dimension(480, 30));
		setPreferredSize(new Dimension(480, 90));

		// �׵θ� ����
		setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 1));

		// ���� ����
		p_title.setBackground(Color.WHITE);
		p_cinema.setBackground(Color.WHITE);
		p_time.setBackground(Color.WHITE);
		setBackground(new Color(103, 156, 220));

		// ���̾ƿ� ����
		setLayout(new BorderLayout());

		// ����
		p_title.add(la_title);
		p_cinema.add(la_cinema);
		p_time.add(la_time);

		add(p_title, BorderLayout.NORTH);
		add(p_cinema);
		add(p_time, BorderLayout.SOUTH);
	}

	// �ش� ��ȭ�� �󿵰��� ������ �������� �Լ�
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

	// �ش� ��ȭ�� �� �ð����� �������� �Լ�
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
