import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class Start {
	private JFrame frmStart = new JFrame("TimeTable Generation System");
	private JMenuBar menuBar = new JMenuBar();
	private JMenu mnFile = new JMenu("File");
	private JLabel backImage = new JLabel(new ImageIcon("imgs\\1.jpg"));
	private JLabel title = new JLabel("TimeTable Generation System");
	private JButton btnLogin = new JButton("Login", new ImageIcon("imgs\\licon.png"));
	private JButton btnExit = new JButton("Exit", new ImageIcon("imgs\\exit.png"));

	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch(Exception e) {
		}

		new Start();
	}

	public Start() {
		frmStart.setJMenuBar(menuBar);
		frmStart.add(backImage);
		frmStart.setResizable(false);
		frmStart.setBounds(100, 100, 505, 350);
		frmStart.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStart.setLocationRelativeTo(null);

		menuBar.add(mnFile);

		JMenuItem mntmAboutUs = new JMenuItem("About Us");
		mntmAboutUs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new About();
			}
		});
		mnFile.add(mntmAboutUs);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}
		});
		mnFile.add(mntmExit);

		backImage.setLayout(null);

		title.setBounds(0, 0, 500, 90);
		title.setFont(new Font("Chiller", Font.BOLD, 45));
		title.setHorizontalAlignment(SwingConstants.CENTER);
		backImage.add(title);

		btnLogin.setBounds(129, 186, 230, 50);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmStart.dispose();
				new Login();
			}
		});
		backImage.add(btnLogin);

		btnExit.setBounds(129, 240, 230, 50);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		backImage.add(btnExit);
		
		frmStart.setVisible(true);
	}
}
