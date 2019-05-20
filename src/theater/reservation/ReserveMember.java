package theater.reservation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ReserveMember extends JFrame {
	JPanel p_wrapper, p_member, p_general, p_teen, p_child, p_button;
	JLabel la_general, la_teen, la_child, la_gnum, la_tnum, la_cnum;
	JTextField t_general, t_teen, t_child;
	JButton bt_confirm, bt_cancel;

	int num = 0; // 총 인원수
	int price = 0; // 총 결제금액

	public ReserveMember(ReservationMain reservationMain) {
		p_wrapper = new JPanel(); // 전체 패널
		p_member = new JPanel(); // 일반,청소년,어린이를 담는 패널
		p_general = new JPanel(); // 일반 패널
		p_teen = new JPanel(); // 청소년 패널
		p_child = new JPanel(); // 어린이 패널
		p_button = new JPanel(); // 버튼 패널

		la_general = new JLabel("일반  ");
		la_teen = new JLabel("청소년  ");
		la_child = new JLabel("어린이  ");
		la_gnum = new JLabel("명");
		la_tnum = new JLabel("명");
		la_cnum = new JLabel("명");

		t_general = new JTextField("0", 10);
		t_teen = new JTextField("0", 10);
		t_child = new JTextField("0", 10);

		bt_confirm = new JButton("확인");
		bt_cancel = new JButton("취소");

		// 폰트 설정
		Font f = new Font("굴림", Font.BOLD, 22);
		la_general.setFont(f);
		la_teen.setFont(f);
		la_child.setFont(f);
		la_gnum.setFont(f);
		la_tnum.setFont(f);
		la_cnum.setFont(f);

		// 사이즈 조절
		p_member.setPreferredSize(new Dimension(350, 150));
		p_general.setPreferredSize(new Dimension(350, 50));
		p_teen.setPreferredSize(new Dimension(350, 50));
		p_child.setPreferredSize(new Dimension(350, 50));
		p_button.setPreferredSize(new Dimension(350, 70));
		p_wrapper.setPreferredSize(new Dimension(350, 220));

		// 여백 설정
		p_button.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		// 테두리 설정
		p_wrapper.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		// 배경색 설정
		p_member.setBackground(Color.WHITE);
		p_general.setBackground(Color.WHITE);
		p_teen.setBackground(Color.WHITE);
		p_child.setBackground(Color.WHITE);
		p_button.setBackground(Color.WHITE);

		// 레이아웃 설정
		p_wrapper.setLayout(new BorderLayout());

		// 부착
		p_general.add(la_general);
		p_general.add(t_general);
		p_general.add(la_gnum);
		p_teen.add(la_teen);
		p_teen.add(t_teen);
		p_teen.add(la_tnum);
		p_child.add(la_child);
		p_child.add(t_child);
		p_child.add(la_cnum);

		p_member.add(p_general);
		p_member.add(p_teen);
		p_member.add(p_child);
		p_button.add(bt_confirm);
		p_button.add(bt_cancel);

		p_wrapper.add(p_member, BorderLayout.NORTH);
		p_wrapper.add(p_button);
		add(p_wrapper);

		// 확인 버튼 클릭시 좌석 선택 페이지 창 띄우기
		bt_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 모든 인원 수가 0명인 경우
				if (Integer.parseInt(t_general.getText()) == 0 && Integer.parseInt(t_teen.getText()) == 0
						&& Integer.parseInt(t_child.getText()) == 0) {
					JOptionPane.showMessageDialog(ReserveMember.this, "인원 수를 입력하세요.");

				} else { // 인원 수를 입력한 경우
					num = Integer.parseInt(t_general.getText()) + Integer.parseInt(t_teen.getText())
							+ Integer.parseInt(t_child.getText());

					price = Integer.parseInt(t_general.getText()) * 11000 + Integer.parseInt(t_teen.getText()) * 9000
							+ Integer.parseInt(t_child.getText()) * 7000;

					new ReserveSeat(reservationMain, num, price);
					dispose();
				}
			}
		});

		// 취소 버튼과 리스너 연결
		bt_cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		setSize(350, 220);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}
}
