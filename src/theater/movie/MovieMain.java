package theater.movie;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import theater.main.MainFrame;
import theater.reservation.ReservationMain;

public class MovieMain extends JPanel {
	JPanel[] movieList = new JPanel[8]; // moviePanel을 담는 배열

	MoviePanel moviePanel;

	public MovieMain(MainFrame mainFrame) {
		movieList[0] = new MoviePanel(1, mainFrame.movie[0], 24, "2019.02.10");
		movieList[1] = new MoviePanel(2, mainFrame.movie[1], 16, "2019.02.03");
		movieList[2] = new MoviePanel(3, mainFrame.movie[2], 18, "2019.02.10");
		movieList[3] = new MoviePanel(4, mainFrame.movie[3], 10, "2019.02.17");
		movieList[4] = new MoviePanel(5, mainFrame.movie[4], 12, "2019.02.03");
		movieList[5] = new MoviePanel(6, mainFrame.movie[5], 8, "2019.02.03");
		movieList[6] = new MoviePanel(7, mainFrame.movie[6], 6, "2019.02.17");
		movieList[7] = new MoviePanel(8, mainFrame.movie[7], 6, "2019.02.10");

		// 여백 설정
		setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

		// 영화 정보를 담은 패널 부착
		for (int i = 0; i < movieList.length; i++) {
			add(movieList[i]);
		}

		// 영화 포스터 클릭시 영화 세부 페이지로 전환
		for (int i = 0; i < movieList.length; i++) {
			movieList[i].addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					for (int a = 0; a < movieList.length; a++) {
						if (e.getSource() == movieList[a]) {
							mainFrame.pages_detail[a].setVisible(true);
						}
					}
					// 영화 세부 페이지만 보이도록 설정
					for (int i = 0; i < mainFrame.pages.length; i++) {
						mainFrame.pages[i].setVisible(false);
					}
				}
			});
		}

		setBackground(new Color(239, 185, 84));
		setPreferredSize(new Dimension(1000, 620));
		setVisible(true);
	}
}
