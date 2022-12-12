package client;

import java.awt.CardLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainFrame extends JFrame {
	ImageIcon icon; // 백그라운드 이미지
	ImageIcon icon2; // 홈패널 이미지
	ImageIcon icon3; // 찾기패널 이미지
	JPanel Main;
	JPanel ViewCard;

	JButton HomeButton;
	JButton SearchButton;

	CardLayout vc;
	// SearchPanel sp;

	MainFrame() {
		icon = new ImageIcon("src/Image/MainFrame.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(511, 773, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		Main = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};
		Main.setLayout(null);
		setSize(520, 800);
		getContentPane().add(Main);

		// 홈버튼
		HomeButton = new JButton();
		HomeButton.setBounds(12, 18, 33, 29);
		HomeButton.setContentAreaFilled(false);
		// HomeButton.setBorderPainted(false);
		HomeButton.setFocusPainted(false);
		Main.add(HomeButton);

		// 검색 버튼 -> 게시글 검색해서 찾기
		SearchButton = new JButton();
		SearchButton.setBounds(15, 66, 30, 24);
		SearchButton.setContentAreaFilled(false);
		// SearchButton.setBorderPainted(false);
		SearchButton.setFocusPainted(false);
		Main.add(SearchButton);

		icon2 = new ImageIcon("src/Image/HomePanel.png");
		Image img2 = icon2.getImage();
		Image changeImg2 = img2.getScaledInstance(453, 773, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon2 = new ImageIcon(changeImg2);

		JPanel HomePanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon2.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		icon3 = new ImageIcon("src/Image/Search.png");
		Image img3 = icon3.getImage();
		Image changeImg3 = img3.getScaledInstance(457, 600, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon3 = new ImageIcon(changeImg3);

		JPanel SearchPanel = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon3.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		ViewCard = new JPanel();
		vc = new CardLayout();
		ViewCard.setLayout(vc);
		ViewCard.setBounds(50, 0, 457, 600);
		Main.add(ViewCard);

		JPanel homeCard = HomePanel;
		JPanel searchCard = SearchPanel;

		ViewCard.add("hc", homeCard);
		ViewCard.add("sc", searchCard);

		vc.show(ViewCard, "hc");

		HomeButton.addActionListener(event -> {
			vc.show(ViewCard, "hc");
		});

		SearchButton.addActionListener(event -> {
			vc.show(ViewCard, "sc");
		});

		setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
