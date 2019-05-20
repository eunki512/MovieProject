package theater.main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import theater.cinema.CinemaMain;
import theater.db.ConnectionManager;
import theater.event.EventMain;
import theater.movie.MovieDetail;
import theater.movie.MovieMain;
import theater.reservation.ReservationMain;
import theater.utils.ImageLoad;

public class MainFrame extends JFrame {
	JPanel p_north, p_title, p_menu, p_content;
	JPanel p_logo, p_myTicket, p_movie, p_cinema, p_reserve, p_event;
	public JPanel[] pages = new JPanel[4]; // ��ȭ,����,����,�̺�Ʈ ������
	public JPanel[] pages_detail = new JPanel[8]; // ��ȭ ���� ������
	JLabel la_logo, la_myTicket, la_movie, la_cinema, la_reserve, la_event;
	Image titleIcon, logo;

	ImageLoad imageLoad;
	public ConnectionManager connectionManager;
	public Connection con;
	MovieMain movieMain; // ��ȭ ������
	MovieDetail movieDetail; // ��ȭ ���� ������
	CinemaMain cinemaMain; // ���� ������
	public ReservationMain reservationMain; // ���� ������
	EventMain eventMain; // �̺�Ʈ ������

	public String[] movie = { "�ظ����Ϳ� ����� ��", "�̼� ���ļ���", "�����", "���� ���� : ���� ŷ��", "�", "���� ġ������ ���Ҹ�", "�λ��̵� �ƿ�",
			"���� �ֽ�ȸ�� 3D" }; // ������ ��ȭ ���� �迭
	public String[] cinema = { "KGV ����", "KGV ��", "KGV ����", "KGV ����", "KGV ����", "KGV ��õ", "KGV ����", "KGV ����", "KGV �뱸",
			"KGV �λ�" }; // ���� �̸� �迭
	public ArrayList<String> reserveInfo = new ArrayList<String>(); // ���� ������ ���� �迭
	public boolean flag_reserve = false; // ���� ����

	public MainFrame() {
		connectionManager = new ConnectionManager();
		con = connectionManager.getConnection();

		p_north = new JPanel(); // title�� menu �г��� ���� �г�
		p_title = new JPanel(); // ����� ����Ƽ���� ���� �г�
		p_menu = new JPanel(); // �޴����� ���� �г�
		p_content = new JPanel(); // ������ ���� �г�

		p_logo = new JPanel(); // �ΰ� ���� �г�
		p_myTicket = new JPanel(); // ����Ƽ�� ��ư�� ���� �г�
		p_movie = new JPanel(); // �޴� '��ȭ'�� ���� �г�
		p_cinema = new JPanel();// �޴� '����'�� ���� �г�
		p_reserve = new JPanel();// �޴� '����'�� ���� �г�
		p_event = new JPanel();// �޴� '�̺�Ʈ'�� ���� �г�

		imageLoad = new ImageLoad();
		titleIcon = imageLoad.getImage("titleLogo.png");
		titleIcon = titleIcon.getScaledInstance(350, 200, Image.SCALE_SMOOTH);
		logo = imageLoad.getImage("logo.png");
		logo = logo.getScaledInstance(250, 80, Image.SCALE_SMOOTH);

		la_logo = new JLabel(new ImageIcon(logo));
		la_myTicket = new JLabel("myTicket", SwingConstants.CENTER);
		la_movie = new JLabel("��ȭ", SwingConstants.CENTER);
		la_cinema = new JLabel("����", SwingConstants.CENTER);
		la_reserve = new JLabel("����", SwingConstants.CENTER);
		la_event = new JLabel("�̺�Ʈ", SwingConstants.CENTER);

		pages[0] = new MovieMain(this);
		pages[1] = new CinemaMain(this);
		reservationMain = new ReservationMain(this);
		pages[2] = reservationMain;
		pages[3] = new EventMain();

		for (int i = 0; i < pages_detail.length; i++) {
			pages_detail[i] = new MovieDetail(this, i);
		}

		// ��Ʈ ����
		la_myTicket.setFont(new Font("����", Font.BOLD, 20));
		Font f = new Font("����", Font.BOLD, 30);
		la_movie.setFont(f);
		la_cinema.setFont(f);
		la_reserve.setFont(f);
		la_event.setFont(f);

		// ������ ����
		p_logo.setPreferredSize(new Dimension(770, 100));
		p_myTicket.setPreferredSize(new Dimension(130, 40));
		p_title.setPreferredSize(new Dimension(1000, 100));
		p_menu.setPreferredSize(new Dimension(1000, 80));
		p_north.setPreferredSize(new Dimension(1000, 180));
		p_content.setPreferredSize(new Dimension(1000, 620));

		Dimension d = new Dimension(250, 80);
		la_movie.setPreferredSize(d);
		la_cinema.setPreferredSize(d);
		la_reserve.setPreferredSize(d);
		la_event.setPreferredSize(d);

		// ���� ����
		la_logo.setBorder(BorderFactory.createEmptyBorder(0, 180, 30, 0));
		la_movie.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		la_cinema.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		la_reserve.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
		la_event.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

		// �׵θ� ����
		p_myTicket.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 3, true));

		// ���� ����
		p_logo.setBackground(Color.WHITE);
		p_myTicket.setBackground(Color.YELLOW);
		p_title.setBackground(Color.WHITE);
		p_north.setBackground(Color.WHITE);
		p_movie.setBackground(new Color(239, 185, 84));
		p_cinema.setBackground(new Color(103, 156, 220));
		p_reserve.setBackground(new Color(125, 226, 97));
		p_event.setBackground(new Color(239, 126, 84));
		p_content.setBackground(Color.WHITE);

		// ���̾ƿ� ����
		p_menu.setLayout(new GridLayout(1, 4));

		// ����
		p_logo.add(la_logo);
		p_myTicket.add(la_myTicket, BorderLayout.EAST);
		p_title.add(p_logo);
		p_title.add(p_myTicket);

		p_movie.add(la_movie);
		p_cinema.add(la_cinema);
		p_reserve.add(la_reserve);
		p_event.add(la_event);
		p_menu.add(p_movie);
		p_menu.add(p_cinema);
		p_menu.add(p_reserve);
		p_menu.add(p_event);

		for (int i = 0; i < pages.length; i++) {
			p_content.add(pages[i]);
		}
		for (int i = 0; i < pages_detail.length; i++) {
			p_content.add(pages_detail[i]);
		}

		p_north.add(p_title, BorderLayout.NORTH);
		p_north.add(p_menu);
		add(p_north, BorderLayout.NORTH);
		add(p_content);

		// ���� Ƽ�� �󺧰� ������ ����
		la_myTicket.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int x = (int) e.getComponent().getParent().getLocationOnScreen().getX();
				int y = (int) e.getComponent().getParent().getLocationOnScreen().getY();

				if (flag_reserve) { // ���� ������ ���� ���
					MyTicket myTicket = new MyTicket(MainFrame.this);
					myTicket.setBounds(x + 190, y, 350, 580);
				} else { // ���� ������ ���� ���
					MyTicketEmpty myTicketEmpty = new MyTicketEmpty(MainFrame.this);
					myTicketEmpty.setBounds(x + 190, y, 350, 580);
				}
			}
		});

		// ��ȭ ������ ���� ������
		la_movie.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(0);
			}
		});

		// ���� ������ ���� ������
		la_cinema.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(1);
			}
		});

		// ���� ������ ���� ������
		la_reserve.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(2);
			}
		});

		// �̺�Ʈ ������ ���� ������
		la_event.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				chageChannel(3);
			}
		});

		setIconImage(titleIcon); // Ÿ��Ʋ ������
		setBounds(500, 150, 1000, 800);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	// ������ ���� �Լ�
	public void chageChannel(int page) {
		for (int i = 0; i < pages.length; i++) {
			if (i == page) { // ������ �������� ���̵���
				pages[i].setVisible(true);
			} else {
				pages[i].setVisible(false);
			}
		}

		// ��ȭ ���� �������� ��� �Ⱥ��̵���
		for (int i = 0; i < pages_detail.length; i++) {
			pages_detail[i].setVisible(false);
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
