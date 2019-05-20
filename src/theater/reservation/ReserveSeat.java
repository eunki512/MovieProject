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
	JPanel[] seatPanel = new JPanel[120]; // 좌석 라벨을 담은 패널 배열
	JLabel[] seatList = new JLabel[120]; // 좌석 라벨 배열
	JLabel la_screen, la_movie, la_price, la_pay;
	Image titleIcon;

	ImageLoad imageLoad;
	ReservationMain reservationMain;

	ArrayList<String> reserveSeatList = new ArrayList<String>(); // 이미 예매된 좌석을 담는 배열
	ArrayList<String> selectSeatList = new ArrayList<String>(); // 선택한 좌석을 담는 배열
	int selectNum = 0; // 선택한 좌석의 개수

	public ReserveSeat(ReservationMain reservationMain, int num, int price) {
		this.reservationMain = reservationMain;

		getReserve(); // 예매한 좌석 가져오기

		p_screen = new JPanel(); // 스크린 패널
		p_seat = new JPanel(); // 좌석 패널
		p_info = new JPanel(); // 정보들을 담는 패널
		p_infoLeft = new JPanel(); // 영화 이름과 결제 금액 패널
		p_pay = new JPanel(); // 결제 버튼 패널

		for (int i = 0; i < seatPanel.length; i++) {
			seatPanel[i] = new JPanel();
		}

		imageLoad = new ImageLoad();
		titleIcon = imageLoad.getImage("titleLogo.png");
		titleIcon = titleIcon.getScaledInstance(350, 200, Image.SCALE_SMOOTH);

		la_screen = new JLabel("screen");
		la_movie = new JLabel(reservationMain.selectMovie, SwingConstants.LEFT);
		la_price = new JLabel("결제금액 : " + price + " 원", SwingConstants.LEFT);
		la_pay = new JLabel("결제");

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

		// 폰트 설정
		la_screen.setFont(new Font("굴림", Font.BOLD, 24));
		la_screen.setForeground(Color.WHITE);
		la_movie.setFont(new Font("굴림", Font.BOLD, 20));
		la_price.setFont(new Font("굴림", Font.BOLD, 20));
		la_pay.setFont(new Font("굴림", Font.BOLD, 18));
		la_pay.setForeground(Color.WHITE);

		// 사이즈 조절
		for (int i = 0; i < seatPanel.length; i++) {
			seatPanel[i].setPreferredSize(new Dimension(50, 30));
		}
		p_screen.setPreferredSize(new Dimension(800, 50));
		p_seat.setPreferredSize(new Dimension(800, 400));
		p_info.setPreferredSize(new Dimension(800, 50));
		la_movie.setPreferredSize(new Dimension(350, 50));
		la_price.setPreferredSize(new Dimension(350, 50));
		la_pay.setPreferredSize(new Dimension(50, 50));

		// 여백 설정
		p_seat.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 40));
		la_movie.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
		la_price.setBorder(BorderFactory.createEmptyBorder(0, 0, 7, 0));
		la_pay.setBorder(BorderFactory.createEmptyBorder(0, 5, 7, 0));

		// 이미 예매한 좌석은 어둡게 배경색 설정 + 라벨 텍스트를 'X'로 설정
		for (int i = 0; i < seatPanel.length; i++) {
			seatPanel[i].setBackground(Color.LIGHT_GRAY);
		}
		for (int i = 0; i < reserveSeatList.size(); i++) {
			for (int a = 0; a < seatList.length; a++) {
				if (reserveSeatList.get(i).equals(seatList[a].getText())) {
					seatList[a].setText("X");
					seatList[a].setFont(new Font("맑은 고딕", Font.PLAIN, 17));
					seatList[a].setForeground(Color.WHITE);
					seatPanel[a].setBackground(Color.DARK_GRAY);
				}
			}
		}

		// 배경색 설정
		p_screen.setBackground(Color.BLACK);
		p_seat.setBackground(Color.BLACK);
		p_infoLeft.setBackground(Color.WHITE);
		p_pay.setBackground(Color.RED);

		// 레이아웃 설정
		p_info.setLayout(new BorderLayout());

		// 부착
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

		// 좌석 라벨과 리스너 연결
		for (int a = 0; a < seatList.length; a++) {
			seatList[a].addMouseListener(new MouseAdapter() {
				boolean flag = false;

				public void mouseClicked(MouseEvent e) {
					for (int i = 0; i < seatList.length; i++) {
						if (e.getSource().equals(seatList[i])) {
							if (seatList[i].getText().equals("X")) {
								// 이미 예매한 좌석은 선택하지 못하도록
							} else {
								flag = !flag;
								if (flag) { // 한 번 클릭시 배경 빨강색으로 변경
									if (selectNum < num) { // 선택한 좌석 수가 인원 수보다 적을 경우
										selectNum++;
										selectSeatList.add(seatList[i].getText());
										seatPanel[i].setBackground(Color.RED);
									} else { // 선택한 좌석 수가 인원 수보다 많을 경우
										flag = !flag;
										JOptionPane.showMessageDialog(ReserveSeat.this, "인원 수를 확인해주세요.");
									}
								} else { // 두 번 클릭시 배경 원래대로
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

		// 결제 버튼과 리스너 연결
		la_pay.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (selectNum == num) { // 좌석을 모두 선택한 경우
					// 메인에 예매한 정보를 배열에 저장
					reservationMain.mainFrame.flag_reserve = true;
					reservationMain.mainFrame.reserveInfo.add(0, reservationMain.selectMovie); // 예매한 영화
					reservationMain.mainFrame.reserveInfo.add(1, reservationMain.selectCinema); // 에매한 극장
					reservationMain.mainFrame.reserveInfo.add(2, reservationMain.selectDate); // 예매한 날짜
					reservationMain.mainFrame.reserveInfo.add(3, reservationMain.selectTime); // 예매한 시간

					String selectSeat = ""; // 선택한 좌석을 나열한 문자열
					for (int i = 0; i < selectSeatList.size(); i++) {
						if (i == selectSeatList.size() - 1) {
							selectSeat += selectSeatList.get(i);
						} else {
							selectSeat += selectSeatList.get(i) + ", ";
						}
					}
					reservationMain.mainFrame.reserveInfo.add(4, selectSeat); // 예매한 좌석

					for (int i = 0; i < selectSeatList.size(); i++) {
						setReserve(selectSeatList.get(i));// 예매한 정보를 테이블에 저장
					}

					JOptionPane.showMessageDialog(ReserveSeat.this, "결제 완료");
					dispose();
					reset(); // 예매 초기화면으로 돌아가기

				} else {
					JOptionPane.showMessageDialog(ReserveSeat.this, "좌석을 먼저 선택해주세요.");
				}
			}
		});

		setIconImage(titleIcon); // 타이틀 아이콘
		setBounds(600, 280, 800, 500);
		setResizable(false);
		setVisible(true);
	}

	// 이미 예매한 좌석을 가져오는 함수
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
			if (!rs.isBeforeFirst()) { // 검색 결과가 존재하지 않을 경우

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

	// 예매한 정보를 테이블에 저장하는 함수
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

	// 예매 초기 화면으로 돌아가는 함수
	public void reset() {
		// 쓰레드 정지
		reservationMain.flag_thread = false;

		// 아무것도 선택안했을 때로 설정
		reservationMain.flag_movie = false;
		reservationMain.flag_cinema = false;
		reservationMain.flag_date = false;
		reservationMain.selectMovie = null;
		reservationMain.selectCinema = null;
		reservationMain.selectDate = null;

		// 모든 목록 글씨를 다시 검정색으로 변경
		Font f2 = new Font("굴림", Font.BOLD, 16);
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

		// 상영시간표를 화면과 배열에서 제거
		reservationMain.p_time.remove(reservationMain.la_cinemaNum);
		for (int i = 0; i < reservationMain.timeTable.size(); i++) {
			reservationMain.p_time.remove(reservationMain.timeTable.get(i));
		}

		// 상영 시간표 보기 라벨 부착
		reservationMain.la_view = new JLabel("상영 시간표 보기", SwingConstants.CENTER);
		reservationMain.la_view.setFont(new Font("굴림", Font.BOLD, 18));
		reservationMain.la_view.setForeground(Color.BLACK);
		reservationMain.la_view.setPreferredSize(new Dimension(260, 40));
		reservationMain.p_time.add(reservationMain.la_view);

		// 상영 시간표 보기 라벨과 리스너 연결
		reservationMain.la_view.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!reservationMain.flag_movie) { // 영화 선택을 안했을 경우
					JOptionPane.showMessageDialog(reservationMain.mainFrame, "영화를 선택하세요.");
				} else if (!reservationMain.flag_cinema) { // 극장 선택을 안했을 경우
					JOptionPane.showMessageDialog(reservationMain.mainFrame, "극장을 선택하세요.");
				} else if (!reservationMain.flag_date) { // 날짜 선택을 안했을 경우
					JOptionPane.showMessageDialog(reservationMain.mainFrame, "날짜를 선택하세요.");
				} else {
					reservationMain.flag_thread = true;
					reservationMain.reserveThread = new ReserveThread(reservationMain);
					reservationMain.reserveThread.start();
					reservationMain.p_time.remove(reservationMain.la_view);
				}
			}
		});

		// 영화 페이지로 돌아가도록 설정
		reservationMain.mainFrame.pages[2].setVisible(false);
		reservationMain.mainFrame.pages[0].setVisible(true);
	}
}
