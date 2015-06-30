package bno.swing2.model.table;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ObjectTableTest {

	public static void main(String[] args) {

		JFrame disp = new JFrame("Table Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLocationRelativeTo(null);
		disp.setSize(800, 600);
		disp.setLayout(new BorderLayout());

		DefaultObjectTableModel<MyObject> tm = new DefaultObjectTableModel<MyObject>(
				MyObject.class);
		JTable table = new JTable(tm);
		table.setAutoCreateRowSorter(true);
		disp.add(new JScrollPane(table), BorderLayout.CENTER);

		for (int i = 0; i < 20; i++) {

			tm.addRow(new MyObject("Prename " + i, "Name " + i, i + 1));
		}

		disp.setVisible(true);
	}

	static class MyObject {

		@Column("Prename")
		String prename;

		@Column(value = "Name", index = 0)
		String name;

		@Column("Age")
		int age;

		public MyObject(String prename, String name, int age) {
			this.prename = prename;
			this.name = name;
			this.age = age;
		}
	}
}
