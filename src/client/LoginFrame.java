package client;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class LoginFrame extends JFrame implements ActionListener {

	ImageIcon icon; // 백그라운드 이미지

	/* TextField */
	JTextField id = new JTextField();
	JPasswordField pw = new JPasswordField();

	/* Button */
	JButton loginBtn = new JButton("로그인");
	JButton joinBtn;
	JButton changeBtn;

	JoinFrame jf;
	CheckFrame cf;

	LoginFrame() {

		// 이미지 패널
		icon = new ImageIcon("src/Image/login2.png");
		Image img = icon.getImage();
		Image changeImg = img.getScaledInstance(422, 472, java.awt.Image.SCALE_SMOOTH);
		ImageIcon changeIcon = new ImageIcon(changeImg);
		JPanel login = new JPanel() {
			public void paintComponent(Graphics g) {
				g.drawImage(changeIcon.getImage(), 0, 0, null);
				setOpaque(false);
				super.paintComponent(g);
			}
		};

		setTitle("로그인");

		/* Panel 크기 작업 */
		setSize(430, 480);
		login.setSize(422, 472);
		login.setLayout(null);

		/* Field 작업 */
		id = new JTextField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		id.setBounds(130, 209, 220, 16);
		login.add(id);
		id.setOpaque(false);
		id.setColumns(10);

		pw = new JPasswordField() {
			@Override
			public void setBorder(Border border) {
			}
		};
		pw.setBounds(145, 263, 201, 13);
		login.add(pw);
		pw.setOpaque(false);

		/* Button 작업 */

		loginBtn = new JButton();
		loginBtn.setBounds(176, 308, 74, 42);
		login.add(loginBtn);
		loginBtn.setBorderPainted(false);
		loginBtn.setContentAreaFilled(false);
		loginBtn.setFocusPainted(false);

		joinBtn = new JButton();
		joinBtn.setBounds(81, 396, 94, 15);
		joinBtn.setBorderPainted(false);
		joinBtn.setContentAreaFilled(false);
		login.add(joinBtn);

		changeBtn = new JButton();
		changeBtn.setBounds(222, 396, 128, 14);
		// changeBtn.setBorderPainted(false);
		changeBtn.setContentAreaFilled(false);
		login.add(changeBtn);

		/* Button 이벤트 리스너 추가 */

		loginBtn.addActionListener(new LoginCheck());
		joinBtn.addActionListener(this);
		changeBtn.addActionListener(this);

		// 기타작업
		getContentPane().add(login);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/* Button 이벤트 리스너 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == joinBtn) {
			jf = new JoinFrame();
		}

		else if (e.getSource() == changeBtn) {
			cf = new CheckFrame();
			System.out.println("1");
		}

		else {
			System.out.println("오류입니다");
		}

	}

	// loginAction

	public class LoginCheck implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			/* TextField에 입력된 아이디와 비밀번호를 변수에 초기화 */
			String uid = id.getText();
			String upass = "";
			for (int i = 0; i < pw.getPassword().length; i++) {
				upass = upass + pw.getPassword()[i];
			}

			mainClient.setClientName(uid);
			mainClient.pw.println(JSONHandle.make101(uid));
			while (true) {
				try {
					Integer temp = Integer.parseInt(mainClient.br.readLine());
					if (temp == 1) {

					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

		}

	}// Listener end

	// 비밀번호 창에서 enter를 누르면 바로 전송되게
	public class LogindAction implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			if (e.getKeyCode() == 10) { // 엔터를 누를 때 보내기 버튼을 강제 클릭

				// bHome.doClick();

			}

		}

		public void keyReleased(KeyEvent arg0) {
		}

		public void keyTyped(KeyEvent arg0) {
		}

	}

}
