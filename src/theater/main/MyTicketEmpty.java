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
import javax.swing.SwingConstants;

import theater.utils.ImageLoad;

public class MyTicketEmpty extends JFrame {
	JPanel p_wrapper, p_title, p_content;
	JLabel la_title, la_x, la_content;

	public MyTicketEmpty(MainFrame mainFrame) {
		p_wrapper = new JPanel(); // 전체 패널
		p_title = new JPanel(); // 마이 티켓 패널
		p_content = new JPanel(); // 내용 패널

		la_title = new JLabel("My Ticket");
		la_x = new JLabel("X");
		la_content = new JLabel("예매한 영화가 없습니다.", SwingConstants.CENTER);

		// 폰트 설정
		la_title.setFont(new Font("굴림", Font.BOLD, 22));
		la_x.setFont(new Font("굴림", Font.BOLD, 17));
		la_content.setFont(new Font("굴림", Font.BOLD, 17));

		// 사이즈 조절
		p_title.setPreferredSize(new Dimension(350, 50));
		p_content.setPreferredSize(new Dimension(350, 550));
		la_content.setPreferredSize(new Dimension(350, 25));
		p_wrapper.setPreferredSize(new Dimension(350, 600));

		// 여백 설정
		la_title.setBorder(BorderFactory.createEmptyBorder(0, 125, 5, 0));
		la_x.setBorder(BorderFactory.createEmptyBorder(0, 90, 30, 0));
		p_content.setBorder(BorderFactory.createEmptyBorder(230, 0, 0, 0));

		// 테두리 설정
		p_title.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		p_wrapper.setBorder(BorderFactory.createLineBorder((Color.GRAY), 2));

		// 배경색 설정
		p_title.setBackground(Color.YELLOW);
		p_content.setBackground(Color.WHITE);

		// 레이아웃 설정
		p_wrapper.setLayout(new BorderLayout());

		// 부착
		p_title.add(la_title);
		p_title.add(la_x);
		p_content.add(la_content);

		p_wrapper.add(p_title, BorderLayout.NORTH);
		p_wrapper.add(p_content);
		add(p_wrapper);

		// 'x' 클릭시 마이티켓 창을 닫는다
		la_x.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				dispose();
			}
		});

		setSize(350, 700);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}
}
