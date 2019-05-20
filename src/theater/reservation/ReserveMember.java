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

	int num = 0; // �� �ο���
	int price = 0; // �� �����ݾ�

	public ReserveMember(ReservationMain reservationMain) {
		p_wrapper = new JPanel(); // ��ü �г�
		p_member = new JPanel(); // �Ϲ�,û�ҳ�,��̸� ��� �г�
		p_general = new JPanel(); // �Ϲ� �г�
		p_teen = new JPanel(); // û�ҳ� �г�
		p_child = new JPanel(); // ��� �г�
		p_button = new JPanel(); // ��ư �г�

		la_general = new JLabel("�Ϲ�  ");
		la_teen = new JLabel("û�ҳ�  ");
		la_child = new JLabel("���  ");
		la_gnum = new JLabel("��");
		la_tnum = new JLabel("��");
		la_cnum = new JLabel("��");

		t_general = new JTextField("0", 10);
		t_teen = new JTextField("0", 10);
		t_child = new JTextField("0", 10);

		bt_confirm = new JButton("Ȯ��");
		bt_cancel = new JButton("���");

		// ��Ʈ ����
		Font f = new Font("����", Font.BOLD, 22);
		la_general.setFont(f);
		la_teen.setFont(f);
		la_child.setFont(f);
		la_gnum.setFont(f);
		la_tnum.setFont(f);
		la_cnum.setFont(f);

		// ������ ����
		p_member.setPreferredSize(new Dimension(350, 150));
		p_general.setPreferredSize(new Dimension(350, 50));
		p_teen.setPreferredSize(new Dimension(350, 50));
		p_child.setPreferredSize(new Dimension(350, 50));
		p_button.setPreferredSize(new Dimension(350, 70));
		p_wrapper.setPreferredSize(new Dimension(350, 220));

		// ���� ����
		p_button.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		// �׵θ� ����
		p_wrapper.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		// ���� ����
		p_member.setBackground(Color.WHITE);
		p_general.setBackground(Color.WHITE);
		p_teen.setBackground(Color.WHITE);
		p_child.setBackground(Color.WHITE);
		p_button.setBackground(Color.WHITE);

		// ���̾ƿ� ����
		p_wrapper.setLayout(new BorderLayout());

		// ����
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

		// Ȯ�� ��ư Ŭ���� �¼� ���� ������ â ����
		bt_confirm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ��� �ο� ���� 0���� ���
				if (Integer.parseInt(t_general.getText()) == 0 && Integer.parseInt(t_teen.getText()) == 0
						&& Integer.parseInt(t_child.getText()) == 0) {
					JOptionPane.showMessageDialog(ReserveMember.this, "�ο� ���� �Է��ϼ���.");

				} else { // �ο� ���� �Է��� ���
					num = Integer.parseInt(t_general.getText()) + Integer.parseInt(t_teen.getText())
							+ Integer.parseInt(t_child.getText());

					price = Integer.parseInt(t_general.getText()) * 11000 + Integer.parseInt(t_teen.getText()) * 9000
							+ Integer.parseInt(t_child.getText()) * 7000;

					new ReserveSeat(reservationMain, num, price);
					dispose();
				}
			}
		});

		// ��� ��ư�� ������ ����
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
