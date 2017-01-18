import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Vector;

import javax.swing.*;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;

import javax.swing.table.*;

public class StudentLogin {
	private JFrame frmStudentLogin;
	private JPanel panel = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenuItem mntmLogout = new JMenuItem("LogOut");
	private JComboBox<String> combobox = new JComboBox<String>();
	private JTable Table;
	private String path, decodedPath, file;
	private Vector<String> headers = new Vector<String>();
	private Vector<Vector<String>> data = new Vector<Vector<String>>();
	private DefaultTableModel model;
	private Connection cn=null;
	private Statement st=null;
	private ResultSet rs=null;

	public StudentLogin() {
		panel.setLayout(new BorderLayout());

		frmStudentLogin = new JFrame("TimeTable");
		frmStudentLogin.setResizable(false);
		frmStudentLogin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmStudentLogin.setJMenuBar(menuBar);
		frmStudentLogin.add(panel);

		mntmLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frmStudentLogin.dispose();
				new Login();
			}
		});
		menuBar.add(mntmLogout);

		try {
			cn=DriverManager.getConnection("Jdbc:Odbc:data");
			st=cn.createStatement();
			rs=st.executeQuery("SELECT code FROM studentgrp");

			while(rs.next()) {
				combobox.addItem(rs.getString(1));
			}
			cn.close();
		}
		catch(Exception e) {
		}
		panel.add(combobox, BorderLayout.SOUTH);

		path = StudentLogin.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		try {
			decodedPath = URLDecoder.decode(path, "UTF-8");
		}
		catch(Exception e) {
		}

		file = decodedPath + combobox.getSelectedItem().toString() + "TimeTable.xls"; 
		fillData(new File(file));
		model = new DefaultTableModel(data, headers);
		
		Table = new JTable() {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false;
			}
		};
		panel.add(Table, BorderLayout.CENTER);
		panel.add(Table.getTableHeader(), BorderLayout.NORTH);
		Table.setModel(model);

		combobox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				file = decodedPath + combobox.getSelectedItem().toString() + "TimeTable.xls";
				fillData(new File(file));
				model = new DefaultTableModel(data, headers);
				Table.setModel(model);
			}
		});

		frmStudentLogin.pack();
		frmStudentLogin.setLocationRelativeTo(null);
		frmStudentLogin.setVisible(true);
	}

	public void fillData(File file) {
		Workbook workbook = null;
		try {
			workbook = Workbook.getWorkbook(file);
		} 
		catch (Exception e) {
		}

		Sheet sheet = workbook.getSheet(0);

		headers.clear();
		for (int i = 0; i < sheet.getColumns(); i++) {
			Cell cell = sheet.getCell(i, 0);
			headers.add(cell.getContents());
		}

		data.clear();
		for (int j = 1; j < sheet.getRows(); j++) {
			Vector<String> d = new Vector<String>();
			for (int i = 0; i < sheet.getColumns(); i++) {
				Cell cell = sheet.getCell(i, j);
				d.add(cell.getContents());
			}
			d.add("\n");
			data.add(d);
		}
	}
}
