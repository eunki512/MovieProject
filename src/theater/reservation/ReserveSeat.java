package theater.reservation;

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
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.main.MainFrame;
import theater.utils.ImageLoad;

public class ReserveSeat extends JFrame {
	JPanel p_screen, p_seat, p_info, p_infoLeft, p_pay;
	JPanel[] seatPanel = new JPanel[120]; // �¼� ���� ���� �г� �迭
	JLabel[] seatList = new JLabel[120]; // �¼� �� �迭
	JLabel la_screen, la_movie, la_price, la_pay;
	Image titleIcon;

	ImageLoad imageLoad;
	ReservationMain reservationMain;

	ArrayList<String> reserveSeatList = new ArrayList<String>(); // �̹� ���ŵ� �¼��� ��� �迭
	ArrayList<String> selectSeatList = new ArrayList<String>(); // ������ �¼��� ��� �迭
	int selectNum = 0; // ������ �¼��� ����

	public ReserveSeat(ReservationMain reservationMain, int num, int price) {
		this.reservationMain = reservationMain;

		getReserve(); // ������ �¼� ��������

		p_screen = new JPanel(); // ��ũ�� �г�
		p_seat = new JPanel(); // �¼� �г�
		p_info = new JPanel(); // �������� ��� �г�
		p_infoLeft = new JPanel(); // ��ȭ �̸��� ���� �ݾ� �г�
		p_pay = new JPanel(); // ���� ��ư �г�

		for (int i = 0; i < seatPanel.length; i++) {
			seatPanel[i] = new JPanel();
		}

		imageLoad = new ImageLoad();
		titleIcon = imageLoad.getImage("titleLogo.png");
		titleIcon = titleIcon.getScaledInstance(350, 200, Image.SCALE_SMOOTH);

		la_screen = new JLabel("screen");
		la_movie = new JLabel(reservationMain.selectMovie, SwingConstants.LEFT);
		la_price = new JLabel("�����ݾ� : " + price + " ��", SwingConstants.LEFT);
		la_pay = new JLabel("����");

		for (int i = 0; i < seatList.length; i++) {
			if (i < seatList.length / 10) {
				seatList[i] = new JLabel("A" + (i + 1));
			} else if (i < (seatList.length / 10) * 2) {
				seatList[i] = new JLabel("B" + (i - 11));
			} else if (i < (seatList.length / 10) * 3) {
				seatList[i] = new JLabel("C" + (i - 23));
			} else if (i < (seatList.length / 10) * 4) {
				seatList[i] = new JLabel("D" + (i - 35));
			} else if (i < (seatList.length / 10) * 5) {
				seatList[i] = new JLabel("E" + (i - 47));
			} else if (i < (seatList.length / 10) * 6) {
				seatList[i] = new JLabel("F" + (i - 59));
			} else if (i < (seatList.length / 10) * 7) {
				seatList[i] = new JLabel("G" + (i - 71));
			} else if (i < (seatList.length / 10) * 8) {
				seatList[i] = new JLabel("H" + (i - 83));
			} else if (i < (seatList.length / 10) * 9) {
				seatList[i] = new JLabel("I" + (i - 95));
			} else {
				seatList[i] = new JLabel("J" + (i - 107));
			}
		}

		// ��Ʈ ����
		la_screen.setFont(new Font("����", Font.BOLD, 24));
		la_screen.setForeground(Color.WHITE);
		la_movie.setFont(new Font("����", Font.BOLD, 20));
		la_price.setFont(new Font("����", Font.BOLD, 20));
		la_pay.setFont(new Font("����", Font.BOLD, 18));
		la_pay.setForeground(Color.WHITE);

		// ������ ����
		for (int i = 0; i < seatPanel.length; i++) {
			seatPanel[i].setPreferredSize(new Dimension(50, 30));
		}
		p_screen.setPreferredSize(new Dimension(800, 50));
		p_seat.setPreferredSize(new Dimension(800, 400));
		p_info.setPreferredSize(new Dimension(800, 50));
		la_movie.setPreferredSize(new Dimension(350, 50));
		la_price.setPreferredSize(new Dimension(350, 50));
		la_pay.setPreferredSize(new Dimension(50, 50));

		// ���� ����
		p_seat.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		la_movie.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
		la_price.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
		la_pay.setBorder(BorderFactory.createEmptyBorder(0, 5, 7, 0));

		// �̹� ������ �¼��� ��Ӱ� ���� ���� + �� �ؽ�Ʈ�� 'X'�� ����
		for (int i = 0; i < seatPanel.length; i++) {
			seatPanel[i].setBackground(Color.LIGHT_GRAY);
		}
		for (int i = 0; i < reserveSeatList.size(); i++) {
			for (int a = 0; a < seatList.length; a++) {
				if (reserveSeatList.get(i).equals(seatList[a].getText())) {
					seatList[a].setText("X");
					seatList[a].setFont(new Font("���� ���", Font.PLAIN, 17));
					seatList[a].setForeground(Color.WHITE);
					seatPanel[a].setBackground(Color.DARK_GRAY);
				}
			}
		}

		// ���� ����
		p_screen.setBackground(Color.BLACK);
		p_seat.setBackground(Color.BLACK);
		p_infoLeft.setBackground(Color.WHITE);
		p_pay.setBackground(Color.RED);

		// ���̾ƿ� ����
		p_info.setLayout(new BorderLayout());

		// ����
		p_screen.add(la_screen);
		for (int i = 0; i < seatList.length; i++) {
			seatPanel[i].add(seatList[i]);
			p_seat.add(seatPanel[i]);
		}

		p_infoLeft.add(la_movie);
		p_infoLeft.add(la_price);
		p_pay.add(la_pay);
		p_info.add(p_infoLeft);
		p_info.add(p_pay, BorderLayout.EAST);

		add(p_screen, BorderLayout.NORTH);
		add(p_seat);
		add(p_info, BorderLayout.SOUTH);

		// �¼� �󺧰� ������ ����
		for (int a = 0; a < seatList.length; a++) {
			seatList[a].addMouseListener(new MouseAdapter() {
				boolean flag = false;

				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < seatList.length; i++) {
						if (e.getSource().equals(seatList[i])) {
							if (seatList[i].getText().equals("X")) {
								// �̹� ������ �¼��� �������� ���ϵ���
							} else {
								flag = !flag;
								if (flag) { // �� �� Ŭ���� ��� ���������� ����
									if (selectNum < num) { // ������ �¼� ���� �ο� ������ ���� ���
										selectNum++;
										selectSeatList.add(seatList[i].getText());
										seatPanel[i].setBackground(Color.RED);
									} else { // ������ �¼� ���� �ο� ������ ���� ���
										flag = !flag;
										JOptionPane.showMessageDialog(ReserveSeat.this, "�ο� ���� Ȯ�����ּ���.");
									}
								} else { // �� �� Ŭ���� ��� �������
									selectNum--;
									selectSeatList.remove(seatList[i].getText());
									seatPanel[i].setBackground(Color.LIGHT_GRAY);
								}
							}
						}
					}
				}
			});
		}

		// ���� ��ư�� ������ ����
		la_pay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (selectNum == num) { // �¼��� ��� ������ ���
					// ���ο� ������ ������ �迭�� ����
					reservationMain.mainFrame.flag_reserve = true;
					reservationMain.mainFrame.reserveInfo.add(0, reservationMain.selectMovie); // ������ ��ȭ
					reservationMain.mainFrame.reserveInfo.add(1, reservationMain.selectCinema); // ������ ����
					reservationMain.mainFrame.reserveInfo.add(2, reservationMain.selectDate); // ������ ��¥
					reservationMain.mainFrame.reserveInfo.add(3, reservationMain.selectTime); // ������ �ð�

					String selectSeat = ""; // ������ �¼��� ������ ���ڿ�
					for (int i = 0; i < selectSeatList.size(); i++) {
						if (i == selectSeatList.size() - 1) {
							selectSeat += selectSeatList.get(i);
						} else {
							selectSeat += selectSeatList.get(i) + ", ";
						}
					}
					reservationMain.mainFrame.reserveInfo.add(4, selectSeat); // ������ �¼�

					for (int i = 0; i < selectSeatList.size(); i++) {
						setReserve(selectSeatList.get(i));// ������ ������ ���̺� ����
					}

					JOptionPane.showMessageDialog(ReserveSeat.this, "���� �Ϸ�");
					dispose();
					reset(); // ���� �ʱ�ȭ������ ���ư���

				} else {
					JOptionPane.showMessageDialog(ReserveSeat.this, "�¼��� ���� �������ּ���.");
				}
			}
		});

		setIconImage(titleIcon); // Ÿ��Ʋ ������
		setBounds(600, 280, 800, 500);
		setResizable(false);
		setVisible(true);
	}

	// �̹� ������ �¼��� �������� �Լ�
	public void getReserve() {
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		String movie = reservationMain.selectMovie;
		String cinema = reservationMain.selectCinema;
		String date = reservationMain.selectDate;
		String time = reservationMain.selectTime;

		String sql = "select s_seat from selectInfo where s_movie='" + movie + "' and s_cinema='" + cinema
				+ "' and s_date='" + date + "' and s_time='" + time + "'";
		try {
			pstmt = reservationMain.mainFrame.con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if (!rs.isBeforeFirst()) { // �˻� ����� �������� ���� ���

			} else {
				while (rs.next()) {
					String seat = rs.getString("s_seat");
					reserveSeatList.add(seat);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reservationMain.mainFrame.connectionManager.closeDB(pstmt, rs);
		}
	}

	// ������ ������ ���̺� �����ϴ� �Լ�
	public void setReserve(String seat) {
		PreparedStatement pstmt = null;

		String movie = reservationMain.selectMovie;
		String cinema = reservationMain.selectCinema;
		String date = reservationMain.selectDate;
		String time = reservationMain.selectTime;

		String sql = "insert into selectInfo values('" + movie + "', '" + cinema + "', '" + date + "', '" + time
				+ "', '" + seat + "')";
		try {
			pstmt = reservationMain.mainFrame.con.prepareStatement(sql);
			int result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			reservationMain.mainFrame.connectionManager.closeDB(pstmt);
		}
	}

	// ���� �ʱ� ȭ������ ���ư��� �Լ�
	public void reset() {
		// ������ ����
		reservationMain.flag_thread = false;

		// �ƹ��͵� ���þ����� ���� ����
		reservationMain.flag_movie = false;
		reservationMain.flag_cinema = false;
		reservationMain.flag_date = false;
		reservationMain.selectMovie = null;
		reservationMain.selectCinema = null;
		reservationMain.selectDate = null;

		// ��� ��� �۾��� �ٽ� ���������� ����
		Font f2 = new Font("����", Font.BOLD, 16);
		for (int i = 0; i < reservationMain.movieList.length; i++) {
			reservationMain.movieList[i].setFont(f2);
			reservationMain.movieList[i].setForeground(Color.BLACK);
		}
		for (int i = 0; i < reservationMain.cinemaList.length; i++) {
			reservationMain.cinemaList[i].setFont(f2);
			reservationMain.cinemaList[i].setForeground(Color.BLACK);
		}
		for (int i = 0; i < reservationMain.dateList.length; i++) {
			reservationMain.dateList[i].setFont(f2);
			reservationMain.dateList[i].setForeground(Color.BLACK);
		}

		// �󿵽ð�ǥ�� ȭ��� �迭���� ����
		reservationMain.p_time.remove(reservationMain.la_cinemaNum);
		for (int i = 0; i < reservationMain.timeTable.size(); i++) {
			reservationMain.p_time.remove(reservationMain.timeTable.get(i));
		}

		// �� �ð�ǥ ���� �� ����
		reservationMain.la_view = new JLabel("�� �ð�ǥ ����", SwingConstants.CENTER);
		reservationMain.la_view.setFont(new Font("����", Font.BOLD, 18));
		reservationMain.la_view.setForeground(Color.BLACK);
		reservationMain.la_view.setPreferredSize(new Dimension(260, 40));
		reservationMain.p_time.add(reservationMain.la_view);

		// �� �ð�ǥ ���� �󺧰� ������ ����
		reservationMain.la_view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!reservationMain.flag_movie) { // ��ȭ ������ ������ ���
					JOptionPane.showMessageDialog(reservationMain.mainFrame, "��ȭ�� �����ϼ���.");
				} else if (!reservationMain.flag_cinema) { // ���� ������ ������ ���
					JOptionPane.showMessageDialog(reservationMain.mainFrame, "������ �����ϼ���.");
				} else if (!reservationMain.flag_date) { // ��¥ ������ ������ ���
					JOptionPane.showMessageDialog(reservationMain.mainFrame, "��¥�� �����ϼ���.");
				} else {
					reservationMain.flag_thread = true;
					reservationMain.reserveThread = new ReserveThread(reservationMain);
					reservationMain.reserveThread.start();
					reservationMain.p_time.remove(reservationMain.la_view);
				}
			}
		});

		// ��ȭ �������� ���ư����� ����
		reservationMain.mainFrame.pages[2].setVisible(false);
		reservationMain.mainFrame.pages[0].setVisible(true);
	}
}
