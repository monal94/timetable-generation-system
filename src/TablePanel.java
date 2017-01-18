import javax.swing.*;
import java.awt.*;
import javax.swing.table.*;
import java.awt.event.*;
import java.awt.print.*;
import java.io.*;
import java.net.URLDecoder;

class TablePanel extends JPanel {
	Table[][][] ttable;
	DefaultTableModel[] model = new DefaultTableModel[15];
	JComboBox<String> comboBox = new JComboBox<String>();
	JButton print = new JButton("Print");
	JTable Table;
	String path, decodedPath, file;

	public TablePanel(Table[][][] t, int stgrp, InputData input, JMenuItem save) {
		double[] time = {9.30, 10.30, 11.30, 12.30, 14.30, 15.30, 16.30}; 
		String[] day = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"};
		ttable = t;
		int stno;
		setLayout(new BorderLayout());

		for(stno=0;stno<stgrp;stno++) {
			model[stno] = new DefaultTableModel();
			comboBox.addItem(input.stgrp[stno].code);
		}
		add(comboBox, BorderLayout.SOUTH);

		Table = new JTable(model[0]) {
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; 
			}
		};

		for(stno=0;stno<stgrp;stno++) {
			model[stno].addColumn("");
			for(int i=1;i<=5;i++) {
				model[stno].addColumn(day[i-1]);
			}
		}

		for(stno=0;stno<stgrp;stno++) {
			for(int i=1;i<=4;i++) {
				double x=time[i-1]+1;
				model[stno].addRow(new Object[]{time[i-1]+"0-"+x+"0",ttable[stno][i-1][0].course+" "+ttable[stno][i-1][0].ins+" "+ttable[stno][i-1][0].room,ttable[stno][i-1][1].course+" "+ttable[stno][i-1][1].ins+" "+ttable[stno][i-1][1].room,ttable[stno][i-1][2].course+" "+ttable[stno][i-1][2].ins+" "+ttable[stno][i-1][2].room,ttable[stno][i-1][3].course+" "+ttable[stno][i-1][3].ins+" "+ttable[stno][i-1][3].room,ttable[stno][i-1][4].course+" "+ttable[stno][i-1][4].ins+" "+ttable[stno][i-1][4].room});
			}

			model[stno].addRow(new Object[]{"13.30-14.30", "-- -- --", "-- -- --", "-- -- --", "-- -- --", "-- -- --"});

			for(int i=5;i<=7;i++) {
				double x=time[i-1]+1;
				model[stno].addRow(new Object[]{time[i-1]+"0-"+x+"0",ttable[stno][i-1][0].course+" "+ttable[stno][i-1][0].ins+" "+ttable[stno][i-1][0].room,ttable[stno][i-1][1].course+" "+ttable[stno][i-1][1].ins+" "+ttable[stno][i-1][1].room,ttable[stno][i-1][2].course+" "+ttable[stno][i-1][2].ins+" "+ttable[stno][i-1][2].room,ttable[stno][i-1][3].course+" "+ttable[stno][i-1][3].ins+" "+ttable[stno][i-1][3].room,ttable[stno][i-1][4].course+" "+ttable[stno][i-1][4].ins+" "+ttable[stno][i-1][4].room});
			}
		}

		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				Table.setModel(model[comboBox.getSelectedIndex()]);
			}
		});

		add(Table, BorderLayout.CENTER);
		add(Table.getTableHeader(), BorderLayout.NORTH);

		add(print, BorderLayout.EAST);
		print.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					boolean complete = Table.print();
					if (complete) {
						JOptionPane.showMessageDialog(null, "Printing Complete!", "Printing Result", JOptionPane.INFORMATION_MESSAGE);
					} 
					else {
						JOptionPane.showMessageDialog(null, "Printing Cancelled!", "Printing Result", JOptionPane.INFORMATION_MESSAGE);
					}
				} 
				catch (PrinterException pe) {
				}
			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				path = TablePanel.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				try {
					decodedPath = URLDecoder.decode(path, "UTF-8");
				}
				catch(Exception ee) {
				}
				file = decodedPath + comboBox.getSelectedItem().toString() + "TimeTable.xls"; 
				toExcel(Table, new File(file));
			}
		});
	}

	public void toExcel(JTable Table, File file) {
		try {
			TableModel model = Table.getModel();
			FileWriter excel = new FileWriter(file);

			for(int i = 0; i < model.getColumnCount(); i++){
				excel.write(model.getColumnName(i) + "\t");
			}
			excel.write("\n");

			for(int i=0; i< model.getRowCount(); i++) {
				for(int j=0; j < model.getColumnCount(); j++) {
					excel.write(model.getValueAt(i,j).toString()+"\t");
				}
				excel.write("\n");
			}
			excel.close();
		}

		catch(IOException e) { 
			JOptionPane.showMessageDialog(null, "File failed to save!", "ERROR!", JOptionPane.ERROR_MESSAGE);
		}

		JOptionPane.showMessageDialog(null, "TimeTable successfully saved to " + file, "SUCCESSFUL!", JOptionPane.INFORMATION_MESSAGE);
	}
}
