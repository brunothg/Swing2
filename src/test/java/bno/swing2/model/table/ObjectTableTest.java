package bno.swing2.model.table;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class ObjectTableTest {

	public static void main(String[] args) {

		JFrame disp = new JFrame("Table Test");
		disp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		disp.setLocationRelativeTo(null);
		disp.setSize(800, 600);
		disp.setLayout(new BorderLayout());

		final DefaultObjectTableModel<MyObject> tm = new DefaultObjectTableModel<MyObject>(
				MyObject.class);
		JTable table = new JTable(tm);
		table.setAutoCreateRowSorter(true);
		disp.add(new JScrollPane(table), BorderLayout.CENTER);

		for (int i = 0; i < 20; i++) {

			tm.addRow(new MyObject("Prename " + i, "Name " + i, i + 1));
		}

		tm.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {

				System.out.println("Table changed:");
				for (int i = 0; i < tm.getRowCount(); i++) {

					System.out.println("\t\t" + tm.getRow(i).toString());
				}
				System.out.println("\n");
			}
		});

		disp.setVisible(true);
	}

	static class MyObject {

		@Column(value = "Prename", editable = true)
		String prename;

		private String name;

		int age;

		public MyObject(String prename, String name, int age) {
			this.prename = prename;
			this.setName(name);
			this.age = age;
		}

		@Column(value = "Age")
		int getAge() {

			return age;
		}

		void setAge(int age) {

			this.age = age;
		}

		@Column(value = "Name", index = 0, setter = "setName", editable = true)
		String getName() {
			return name;
		}

		void setName(String name) {
			this.name = name;
		}

		public String toString() {

			return String.format("%s, %s - %d", prename, getName(), age);
		}

	}
}
