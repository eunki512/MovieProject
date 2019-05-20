package theater.event;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import theater.utils.ImageLoad;

public class EventMain extends JPanel {
	JPanel p_event1, p_event2, p_event3;
	JLabel la_event1, la_event2, la_event3;
	Image event1, event2, event3;

	ImageLoad imageLoad;

	public EventMain() {
		p_event1 = new JPanel(); // 이벤트1 패널
		p_event2 = new JPanel(); // 이벤트2 패널
		p_event3 = new JPanel(); // 이벤트3 패널

		imageLoad = new ImageLoad();
		event1 = imageLoad.getImage("event1.png");
		event1 = event1.getScaledInstance(550, 170, Image.SCALE_SMOOTH);
		event2 = imageLoad.getImage("event2.png");
		event2 = event2.getScaledInstance(550, 170, Image.SCALE_SMOOTH);
		event3 = imageLoad.getImage("event3.png");
		event3 = event3.getScaledInstance(550, 170, Image.SCALE_SMOOTH);

		la_event1 = new JLabel(new ImageIcon(event1));
		la_event2 = new JLabel(new ImageIcon(event2));
		la_event3 = new JLabel(new ImageIcon(event3));

		// 사이즈 조절
		Dimension d = new Dimension(550, 180);
		p_event1.setPreferredSize(d);
		p_event2.setPreferredSize(d);
		p_event3.setPreferredSize(d);

		// 여백 설정
		setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));

		// 배경색 설정
		Color c = new Color(239, 126, 84);
		p_event1.setBackground(c);
		p_event2.setBackground(c);
		p_event3.setBackground(c);

		// 부착
		p_event1.add(la_event1);
		p_event2.add(la_event2);
		p_event3.add(la_event3);

		add(p_event1);
		add(p_event2);
		add(p_event3);

		setBackground(new Color(239, 126, 84));
		setPreferredSize(new Dimension(1000, 620));
		setVisible(false);
	}
}
