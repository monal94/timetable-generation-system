import javax.swing.*;
import java.awt.*;

class PrintTable {
	Table[][][] ttable;
	JFrame tableframe;
	int stgrp, i;
	TablePanel panel;
	JPanel south;
	InputData input;
	JMenuBar menuBar = new JMenuBar();
	JMenu file = new JMenu("File");
	JMenuItem save = new JMenuItem("Save");

	public PrintTable(Table[][][] t, int nostgrp, InputData input1) {
		ttable = t;
		tableframe = new JFrame("TimeTable");
		stgrp = nostgrp;
		south = new JPanel();
		input = input1;
	}

	void print() {
		panel = new TablePanel(ttable, stgrp, input, save);

		tableframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tableframe.setLayout(new BorderLayout());
		tableframe.setJMenuBar(menuBar);
		tableframe.add(panel, BorderLayout.CENTER);

		menuBar.add(file);
		file.add(save);

		tableframe.pack();
		tableframe.setLocationRelativeTo(null);
		tableframe.setVisible(true);
	}
}