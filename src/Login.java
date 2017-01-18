import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Arrays;

public class Login {
	private JFrame frmLogin = new JFrame("LOGIN");
	private JTextField textField;
	private JPasswordField passwordField;
	private String dialogMessage, dialogs;
	private JProgressBar progressBar;

	public Login() {
		frmLogin.setResizable(false);
		frmLogin.setBackground(Color.WHITE);
		frmLogin.setBounds(100, 100, 330, 305);
		frmLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmLogin.setLayout(new BorderLayout(0, 0));
		frmLogin.setLocationRelativeTo(null);

		JLabel backImage = new JLabel(new ImageIcon("imgs\\Login.png"));
		frmLogin.add(backImage);
		backImage.setLayout(null);

		JLabel lblNewLabel = new JLabel("Username");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel.setBounds(10, 135, 78, 14);
		backImage.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setFont(new Font("Arial", Font.BOLD, 16));
		lblNewLabel_1.setBounds(10, 172, 78, 14);
		backImage.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(98, 134, 206, 20);
		backImage.add(textField);
		textField.setColumns(10);

		passwordField = new JPasswordField();
		passwordField.setBounds(98, 171, 206, 20);
		backImage.add(passwordField);

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String loginname;
				char[] loginpass;
				loginname = textField.getText();
				loginpass = passwordField.getPassword();
				dialogMessage = "Welcome - " + loginname.toUpperCase();
				if( (loginname.equals("admin") && Arrays.equals("admin".toCharArray(), loginpass)) || loginname.equals("student") && Arrays.equals("student".toCharArray(), loginpass) ) {
					new Thread(new PBar(loginname)).start();
				}
				else {
					JOptionPane.showMessageDialog(null, "Invaild User Name and Password!" , "ERROR!!!", JOptionPane.INFORMATION_MESSAGE);
					textField.setText("");
					passwordField.setText("");
				}	
			}
		});
		btnNewButton.setBounds(50, 208, 89, 23);
		backImage.add(btnNewButton);

		JButton btnNewButton_1 = new JButton("Reset");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				passwordField.setText("");
			}
		});
		btnNewButton_1.setBounds(185, 208, 89, 23);
		backImage.add(btnNewButton_1);

		progressBar = new JProgressBar(0, 100);
		progressBar.setBounds(10, 242, 294, 14);
		backImage.add(progressBar);
		
		frmLogin.setVisible(true);
	}

	class PBar implements Runnable {
		String name;
		public PBar(String s) {
			name = s;
		}

		public void run() {
			for (int i=0; i<=100; i++) { 
				progressBar.setValue(i);
				progressBar.repaint();
				try {
					Thread.sleep(15);
				}	 
				catch (Exception e) {
				}
			}
			
			JOptionPane.showMessageDialog(null, dialogMessage, dialogs, JOptionPane.INFORMATION_MESSAGE);
			frmLogin.dispose();
			
			if(name.equals("student"))
				new StudentLogin();
			else if(name.equals("admin"))
				new AdminLogin();
		}
	}
}
