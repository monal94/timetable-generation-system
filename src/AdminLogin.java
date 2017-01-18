import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.sql.*;
import java.util.*;
import javax.swing.border.*;

class VerifyException extends Exception {
	VerifyException(String s) {
		super(s);
	}
}

public class AdminLogin implements ActionListener {
	private JFrame frmTimetableGenerationSystem;
	private JPanel panel1, panel2, panel, panel_1, panel_2, panel_3;
	private JMenuBar menuBar = new JMenuBar();
	private JTabbedPane tabbedPane, tabbedPane_1;
	private JButton btnNewButton_2, btnNewButton_1, btnDelete, btnDelete_1, btnDelete_2, btnDelete_3, btnAdd, btnAdd_1, btnAdd_2, btnAdd_3;
	private JLabel lblNewLabel, lblNewLabel_1, lblNewLabel_2;
	private JTextField textField, textField_1, textField_2, textField_3, textField_4, textField_5, textField_6, textField_7, textField_8, textField_9, textField_10, textField_11, textField_12, textField_13, textField_14, textField_15, textField_16;
	private JTable table, table_1, table_2, table_3;
	private Connection cn=null;
	private Statement st=null;
	private ResultSet rs=null;

	public AdminLogin() {
		frmTimetableGenerationSystem = new JFrame("TimeTable Generation System");
		frmTimetableGenerationSystem.setJMenuBar(menuBar);
		frmTimetableGenerationSystem.setResizable(false);
		frmTimetableGenerationSystem.setBounds(100, 100, 800, 500);
		frmTimetableGenerationSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTimetableGenerationSystem.setLayout(new GridLayout(1, 1));
		frmTimetableGenerationSystem.setLocationRelativeTo(null);

		JMenuItem mntmLogout = new JMenuItem("LogOut");
		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmTimetableGenerationSystem.setVisible(false);
				new Login();
			}
		});
		menuBar.add(mntmLogout);

		panel1 = new JPanel();
		panel2 = new JPanel();

		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		frmTimetableGenerationSystem.add(tabbedPane);
		tabbedPane.addTab("Manage Data", panel1);
		panel1.setLayout(new GridLayout(1, 1));

		tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		panel1.add(tabbedPane_1);

		panel = new JPanel();
		tabbedPane_1.addTab("Courses Manager", panel);
		panel.setLayout(null);

		JLabel lblNewLabel_14 = new JLabel("Name");
		lblNewLabel_14.setBounds(10, 11, 46, 14);
		panel.add(lblNewLabel_14);

		JLabel lblNewLabel_15 = new JLabel("Code*");
		lblNewLabel_15.setBounds(10, 36, 46, 14);
		panel.add(lblNewLabel_15);

		JLabel lblNewLabel_16 = new JLabel("Department");
		lblNewLabel_16.setBounds(10, 61, 64, 14);
		panel.add(lblNewLabel_16);

		textField_14 = new JTextField();
		textField_14.setBounds(84, 8, 86, 20);
		panel.add(textField_14);
		textField_14.setColumns(10);

		textField_15 = new JTextField();
		textField_15.setBounds(84, 33, 86, 20);
		panel.add(textField_15);
		textField_15.setColumns(10);

		textField_16 = new JTextField();
		textField_16.setBounds(84, 58, 86, 20);
		panel.add(textField_16);
		textField_16.setColumns(10);

		btnAdd_3 = new JButton("Add");
		btnAdd_3.addActionListener(this);
		btnAdd_3.setBounds(10, 86, 89, 23);
		panel.add(btnAdd_3);

		JButton btnReset_3 = new JButton("Reset");
		btnReset_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_14.setText("");
				textField_15.setText("");
				textField_16.setText("");
			}
		});
		btnReset_3.setBounds(109, 86, 89, 23);
		panel.add(btnReset_3);

		table = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		table.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table.setBounds(299, 29, 475, 356);
		panel.add(table);
		panel.add(table.getTableHeader()).setBounds(299, 11, 475, 20);
		updateCourses();

		btnDelete_3 = new JButton("Delete");
		btnDelete_3.addActionListener(this);
		btnDelete_3.setBounds(208, 86, 89, 23);
		panel.add(btnDelete_3);

		JLabel lblMandatory_1 = new JLabel("* : Mandatory Field for Deletion");
		lblMandatory_1.setBounds(10, 120, 152, 14);
		panel.add(lblMandatory_1);

		panel_1 = new JPanel();
		tabbedPane_1.addTab("Teachers Manager", panel_1);
		panel_1.setLayout(null);

		lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(10, 11, 46, 14);
		panel_1.add(lblNewLabel);

		lblNewLabel_1 = new JLabel("Code*");
		lblNewLabel_1.setBounds(10, 36, 46, 14);
		panel_1.add(lblNewLabel_1);

		lblNewLabel_2 = new JLabel("Subject");
		lblNewLabel_2.setBounds(10, 61, 46, 14);
		panel_1.add(lblNewLabel_2);

		textField = new JTextField();
		textField.setBounds(66, 8, 86, 20);
		panel_1.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(66, 33, 86, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(66, 58, 86, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);

		btnAdd = new JButton("Add");
		btnAdd.addActionListener(this);
		btnAdd.setBounds(10, 86, 89, 23);
		panel_1.add(btnAdd);

		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField.setText("");
				textField_1.setText("");
				textField_2.setText("");
			}
		});
		btnReset.setBounds(109, 86, 89, 23);
		panel_1.add(btnReset);

		table_1 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		table_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table_1.setBounds(299, 29, 475, 356);
		panel_1.add(table_1);
		panel_1.add(table_1.getTableHeader()).setBounds(299, 11, 475, 20);
		updateTeachers();

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(this);
		btnDelete.setBounds(208, 86, 89, 23);
		panel_1.add(btnDelete);

		JLabel label_2 = new JLabel("* : Mandatory Field for Deletion");
		label_2.setBounds(10, 120, 152, 14);
		panel_1.add(label_2);

		panel_2 = new JPanel();
		tabbedPane_1.addTab("Classes Manager", panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Department");
		lblNewLabel_3.setBounds(10, 11, 67, 14);
		panel_2.add(lblNewLabel_3);

		JLabel lblNewLabel_4 = new JLabel("Code*");
		lblNewLabel_4.setBounds(10, 36, 46, 14);
		panel_2.add(lblNewLabel_4);

		JLabel lblNewLabel_5 = new JLabel("Strength");
		lblNewLabel_5.setBounds(10, 61, 46, 14);
		panel_2.add(lblNewLabel_5);

		JLabel lblNewLabel_6 = new JLabel("Subject - 1");
		lblNewLabel_6.setBounds(10, 86, 56, 14);
		panel_2.add(lblNewLabel_6);

		JLabel lblNewLabel_7 = new JLabel("Subject - 2");
		lblNewLabel_7.setBounds(10, 111, 56, 14);
		panel_2.add(lblNewLabel_7);

		JLabel lblNewLabel_8 = new JLabel("Subject - 3");
		lblNewLabel_8.setBounds(10, 136, 56, 14);
		panel_2.add(lblNewLabel_8);

		JLabel lblNewLabel_9 = new JLabel("Subject - 4");
		lblNewLabel_9.setBounds(10, 161, 56, 14);
		panel_2.add(lblNewLabel_9);

		JLabel lblNewLabel_10 = new JLabel("Subject - 5");
		lblNewLabel_10.setBounds(10, 186, 56, 14);
		panel_2.add(lblNewLabel_10);

		textField_3 = new JTextField();
		textField_3.setBounds(76, 8, 86, 20);
		panel_2.add(textField_3);
		textField_3.setColumns(10);

		textField_4 = new JTextField();
		textField_4.setBounds(76, 33, 86, 20);
		panel_2.add(textField_4);
		textField_4.setColumns(10);

		textField_5 = new JTextField();
		textField_5.setBounds(76, 58, 86, 20);
		panel_2.add(textField_5);
		textField_5.setColumns(10);

		textField_6 = new JTextField();
		textField_6.setBounds(76, 83, 86, 20);
		panel_2.add(textField_6);
		textField_6.setColumns(10);

		textField_7 = new JTextField();
		textField_7.setBounds(76, 108, 86, 20);
		panel_2.add(textField_7);
		textField_7.setColumns(10);

		textField_8 = new JTextField();
		textField_8.setBounds(76, 133, 86, 20);
		panel_2.add(textField_8);
		textField_8.setColumns(10);

		textField_9 = new JTextField();
		textField_9.setBounds(76, 158, 86, 20);
		panel_2.add(textField_9);
		textField_9.setColumns(10);

		textField_10 = new JTextField();
		textField_10.setBounds(76, 183, 86, 20);
		panel_2.add(textField_10);
		textField_10.setColumns(10);

		btnAdd_1 = new JButton("Add");
		btnAdd_1.addActionListener(this);
		btnAdd_1.setBounds(10, 211, 89, 23);
		panel_2.add(btnAdd_1);

		JButton btnReset_1 = new JButton("Reset");
		btnReset_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_3.setText("");
				textField_4.setText("");
				textField_5.setText("");
				textField_6.setText("");
				textField_7.setText("");
				textField_8.setText("");
				textField_9.setText("");
				textField_10.setText("");
			}
		});
		btnReset_1.setBounds(109, 211, 89, 23);
		panel_2.add(btnReset_1);

		table_2 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		table_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table_2.setBounds(299, 29, 475, 356);
		panel_2.add(table_2);
		panel_2.add(table_2.getTableHeader()).setBounds(299, 11, 475, 20);
		updateClasses();

		btnDelete_1 = new JButton("Delete");
		btnDelete_1.addActionListener(this);
		btnDelete_1.setBounds(208, 211, 89, 23);
		panel_2.add(btnDelete_1);

		JLabel label = new JLabel("* : Mandatory Field for Deletion");
		label.setBounds(10, 245, 152, 14);
		panel_2.add(label);

		panel_3 = new JPanel();
		tabbedPane_1.addTab("ClassRooms Manager", panel_3);
		panel_3.setLayout(null);

		JLabel lblNewLabel_11 = new JLabel("Code*");
		lblNewLabel_11.setBounds(10, 11, 46, 14);
		panel_3.add(lblNewLabel_11);

		JLabel lblNewLabel_12 = new JLabel("Department");
		lblNewLabel_12.setBounds(10, 36, 64, 14);
		panel_3.add(lblNewLabel_12);

		JLabel lblNewLabel_13 = new JLabel("Strength");
		lblNewLabel_13.setBounds(10, 61, 46, 14);
		panel_3.add(lblNewLabel_13);

		textField_11 = new JTextField();
		textField_11.setBounds(76, 8, 86, 20);
		panel_3.add(textField_11);
		textField_11.setColumns(10);

		textField_12 = new JTextField();
		textField_12.setBounds(76, 33, 86, 20);
		panel_3.add(textField_12);
		textField_12.setColumns(10);

		textField_13 = new JTextField();
		textField_13.setBounds(76, 58, 86, 20);
		panel_3.add(textField_13);
		textField_13.setColumns(10);

		btnAdd_2 = new JButton("Add");
		btnAdd_2.addActionListener(this);
		btnAdd_2.setBounds(10, 86, 89, 23);
		panel_3.add(btnAdd_2);

		JButton btnReset_2 = new JButton("Reset");
		btnReset_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_11.setText("");
				textField_12.setText("");
				textField_13.setText("");
			}
		});
		btnReset_2.setBounds(109, 86, 89, 23);
		panel_3.add(btnReset_2);

		table_3 = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		table_3.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		table_3.setBounds(299, 29, 475, 356);
		panel_3.add(table_3);
		panel_3.add(table_3.getTableHeader()).setBounds(299, 11, 475, 20);
		updateClassRooms();

		btnDelete_2 = new JButton("Delete");
		btnDelete_2.addActionListener(this);
		btnDelete_2.setBounds(208, 86, 89, 23);
		panel_3.add(btnDelete_2);

		JLabel label_1 = new JLabel("* : Mandatory Field for Deletion");
		label_1.setBounds(10, 120, 152, 14);
		panel_3.add(label_1);

		tabbedPane.addTab("TimeTable", panel2);
		panel2.setLayout(new GridLayout(2, 1));

		btnNewButton_1 = new JButton("Verify Data");
		btnNewButton_1.setFont(new Font("Euphemia", Font.BOLD, 38));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					verifyData();
				} 
				catch (VerifyException ve) {
					JOptionPane.showMessageDialog(null, ve.getMessage(), "ERROR!!!", JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception ee) {
				}
			}
		});
		panel2.add(btnNewButton_1);

		btnNewButton_2 = new JButton("Click to Generate TimeTable");
		btnNewButton_2.setFont(new Font("Euphemia", Font.BOLD, 38));
		btnNewButton_2.setEnabled(false);
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Schedule();
			}
		});
		panel2.add(btnNewButton_2);

		frmTimetableGenerationSystem.setVisible(true);
	}

	public void updateCourses() {
		DefaultTableModel model = new DefaultTableModel();
		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();
			rs=st.executeQuery("SELECT * FROM course");
			model.addColumn("NAME");
			model.addColumn("CODE");
			model.addColumn("DEPARTMENT");

			while(rs.next()) {
				model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
			}

			cn.close();
		}
		catch(Exception e) {
		}
		table.setModel(model);
	}

	public void updateTeachers() {
		DefaultTableModel model = new DefaultTableModel();
		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();
			rs=st.executeQuery("SELECT * FROM instructors");
			model.addColumn("NAME");
			model.addColumn("CODE");
			model.addColumn("SUBJECT");

			while(rs.next()) {
				model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
			}

			cn.close();
		}
		catch(Exception e) {
		}
		table_1.setModel(model);
	}

	public void updateClasses() {
		DefaultTableModel model = new DefaultTableModel();
		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();
			rs=st.executeQuery("SELECT * FROM studentgrp");
			model.addColumn("CODE");
			model.addColumn("STRENGTH");
			model.addColumn("SUB-1");
			model.addColumn("SUB-2");
			model.addColumn("SUB-3");
			model.addColumn("SUB-4");
			model.addColumn("SUB-5");

			while(rs.next()) {
				model.addRow(new Object[]{rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)});
			}

			cn.close();
		}
		catch(Exception e) {
		}
		table_2.setModel(model);
	}

	public void updateClassRooms() {
		DefaultTableModel model = new DefaultTableModel();
		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();
			rs=st.executeQuery("SELECT * FROM classrooms");
			model.addColumn("CODE");
			model.addColumn("DEPARTMENT");
			model.addColumn("STRENGTH");

			while(rs.next()) {
				model.addRow(new Object[]{rs.getString(1), rs.getString(2), rs.getString(3)});
			}

			cn.close();
		}
		catch(Exception e) {
		}
		table_3.setModel(model);
	}

	public void actionPerformed(ActionEvent e) {
		String name, code="", dep, sub, info, strength, sub1, sub2, sub3, sub4, sub5;
		int no=0;
		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();

			if(e.getSource()==btnAdd_3) {
				name = textField_14.getText();
				code = textField_15.getText();
				dep = textField_16.getText();

				if(name.equals("") || code.equals("") || dep.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill ALL The Fields!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				no = st.executeUpdate("INSERT INTO course VALUES('"+name+"','"+code+"','"+dep+"');");
				if(no == 0) {
					JOptionPane.showMessageDialog(null, "Code: " +code+ " already exists!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
				cn.close();
				updateCourses();
			}

			else if(e.getSource()==btnAdd) {
				name = textField.getText();
				code = textField_1.getText();
				sub = textField_2.getText();

				if(name.equals("") || code.equals("") || sub.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill ALL The Fields!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				no = st.executeUpdate("INSERT INTO instructors VALUES('"+name+"','"+code+"','"+sub+"');");
				if(no == 0) {
					JOptionPane.showMessageDialog(null, "Code: " +code+ " already exists!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
				cn.close();
				updateTeachers();
			}

			else if(e.getSource()==btnAdd_1) {
				info = textField_3.getText();
				code = textField_4.getText();
				strength = textField_5.getText();
				sub1 = textField_6.getText();
				sub2 = textField_7.getText();
				sub3 = textField_8.getText();
				sub4 = textField_9.getText();
				sub5 = textField_10.getText();

				if(info.equals("") || code.equals("") || strength.equals("") || sub1.equals("") || sub2.equals("") || sub3.equals("") || sub4.equals("") || sub5.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill ALL The Fields!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				no = st.executeUpdate("INSERT INTO studentgrp VALUES('"+info+"','"+code+"','"+strength+"','"+sub1+"','"+sub2+"','"+sub3+"','"+sub4+"','"+sub5+"');");
				if(no == 0) {
					JOptionPane.showMessageDialog(null, "Code: " +code+ " already exists!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
				cn.close();
				updateClasses();
			}

			else if(e.getSource()==btnAdd_2) {
				code = textField_11.getText();
				dep = textField_12.getText();
				strength = textField_13.getText();

				if(code.equals("") || dep.equals("") || strength.equals("")) {
					JOptionPane.showMessageDialog(null, "Please Fill ALL The Fields!", "ERROR!", JOptionPane.ERROR_MESSAGE);
					return;
				}

				no = st.executeUpdate("INSERT INTO classrooms VALUES('"+code+"','"+dep+"','"+strength+"');");
				if(no == 0) {
					JOptionPane.showMessageDialog(null, "Code: " +code+ " already exists!", "ERROR!", JOptionPane.ERROR_MESSAGE);
				}
				cn.close();
				updateClassRooms();
			}

			else if(e.getSource()==btnDelete_3) {
				code = textField_15.getText();
				st.executeUpdate("DELETE from course where ccode='"+code+"'");
				cn.close();
				updateCourses();
			}

			else if(e.getSource()==btnDelete) {
				code = textField_1.getText();
				st.executeUpdate("DELETE from instructors where tcode='"+code+"'");
				cn.close();
				updateTeachers();
			}

			else if(e.getSource()==btnDelete_1) {
				code = textField_4.getText();
				st.executeUpdate("DELETE from studentgrp where code='"+code+"'");
				cn.close();
				updateClasses();
			}

			else if(e.getSource()==btnDelete_2) {
				code = textField_11.getText();
				st.executeUpdate("DELETE from classrooms where code='"+code+"'");
				cn.close();
				updateClassRooms();
			}
		}
		catch(Exception ee) {
		}

		btnNewButton_2.setEnabled(false);
	}

	public void verifyData() throws VerifyException
	{
		TreeSet<Integer> studentgrp = new TreeSet<Integer>();
		TreeSet<Integer> course = new TreeSet<Integer>();
		TreeSet<Integer> instructors = new TreeSet<Integer>();

		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();
			rs=st.executeQuery("SELECT sub1 FROM studentgrp UNION SELECT sub2 FROM studentgrp UNION SELECT sub3 FROM studentgrp UNION SELECT sub4 FROM studentgrp UNION SELECT sub5 FROM studentgrp");
			while(rs.next()) {
				studentgrp.add(Integer.parseInt(rs.getString(1)));
			}

			rs=st.executeQuery("SELECT ccode FROM course");
			while(rs.next()) {
				course.add(Integer.parseInt(rs.getString(1)));
			}

			rs=st.executeQuery("SELECT tsub FROM instructors");
			while(rs.next()) {
				instructors.add(Integer.parseInt(rs.getString(1)));
			}
			cn.close();
		}

		catch(Exception e) {
		}

		if(!studentgrp.equals(course)) {
			throw new VerifyException("Subject Codes in Classes does not match with codes in Courses!");
		}
		else if(!studentgrp.equals(instructors)) {
			throw new VerifyException("Subject Codes in Classes does not match with codes in Teachers!");
		}
		else if(!instructors.equals(course)) {
			throw new VerifyException("Subject Codes in Teachers does not match with codes in Courses!");
		}

		JOptionPane.showMessageDialog(null, "Data Succesfully Verified!", "Welcome!", JOptionPane.INFORMATION_MESSAGE);
		btnNewButton_2.setEnabled(true);
	}
}
