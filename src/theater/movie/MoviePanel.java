package theater.movie;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.utils.ImageLoad;

public class MoviePanel extends JPanel {
	JPanel p_wrapper, p_poster, p_info;
	JLabel la_poster, la_name, la_rate, la_date;
	Image poster;

	ImageLoad imageLoad;

	public MoviePanel(int num, String name, int rate, String date) {
		p_wrapper = new JPanel(); // �����Ϳ� ��ȭ ������ ���� �г�
		p_poster = new JPanel(); // ������ �г�
		p_info = new JPanel(); // ��ȭ ���� �г�

		imageLoad = new ImageLoad();
		poster = imageLoad.getImage("poster" + num + ".jpg");
		poster = poster.getScaledInstance(180, 180, Image.SCALE_SMOOTH);

		la_poster = new JLabel(new ImageIcon(poster));
		la_name = new JLabel(name, SwingConstants.CENTER);
		la_rate = new JLabel("������ " + rate + "%", SwingConstants.CENTER);
		la_date = new JLabel(date + " ����", SwingConstants.CENTER);

		// ��Ʈ ����
		la_name.setFont(new Font("����", Font.BOLD, 15));
		la_rate.setFont(new Font("����", Font.PLAIN, 14));
		la_date.setFont(new Font("����", Font.PLAIN, 14));

		// ������ ����
		la_name.setPreferredSize(new Dimension(200, 20));
		la_rate.setPreferredSize(new Dimension(200, 15));
		la_date.setPreferredSize(new Dimension(200, 15));
		p_poster.setPreferredSize(new Dimension(200, 180));
		p_info.setPreferredSize(new Dimension(200, 60));
		p_wrapper.setPreferredSize(new Dimension(200, 250));
		setPreferredSize(new Dimension(230, 260));

		// �׵θ� ����
		p_wrapper.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		// ���� ����
		p_poster.setBackground(Color.WHITE);
		p_info.setBackground(Color.WHITE);
		setBackground(new Color(239, 185, 84));

		// ���̾ƿ� ����
		p_wrapper.setLayout(new BorderLayout());

		// ����
		p_poster.add(la_poster);
		p_info.add(la_name);
		p_info.add(la_rate);
		p_info.add(la_date);

		p_wrapper.add(p_poster, BorderLayout.NORTH);
		p_wrapper.add(p_info);
		add(p_wrapper);
	}
}
