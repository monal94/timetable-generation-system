import java.awt.*;
import javax.swing.*;

public class About {
	private JFrame frmAboutUs = new JFrame("About Us");
	private JPanel panel = new JPanel();
	
	public About() {
		frmAboutUs.add(panel);
		frmAboutUs.setResizable(false);
		frmAboutUs.setBounds(100, 100, 300, 450);
		frmAboutUs.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frmAboutUs.setLocationRelativeTo(null);
		
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);

		JLabel lblAboutUs = new JLabel("ABOUT US");
		lblAboutUs.setHorizontalAlignment(SwingConstants.CENTER);
		lblAboutUs.setFont(new Font("Euphemia", Font.BOLD, 35));
		lblAboutUs.setBounds(10, 11, 274, 65);
		panel.add(lblAboutUs);

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("imgs\\nsit.jpg"));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 87, 274, 123);
		panel.add(lblNewLabel);

		JLabel lblKeshavGupta = new JLabel("Keshav Gupta - 279/CO/12");
		lblKeshavGupta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKeshavGupta.setBounds(10, 221, 274, 25);
		panel.add(lblKeshavGupta);

		JLabel lblMonalJain = new JLabel("Monal Jain - 299/CO/12");
		lblMonalJain.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMonalJain.setBounds(10, 257, 274, 25);
		panel.add(lblMonalJain);

		JLabel lblPrinceSehrawat = new JLabel("Prince Sehrawat - 325/CO/12");
		lblPrinceSehrawat.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPrinceSehrawat.setBounds(10, 293, 274, 25);
		panel.add(lblPrinceSehrawat);

		frmAboutUs.setVisible(true);
	}
}
