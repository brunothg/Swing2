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

		// Now
		final DefaultObjectTableModel<MyObject> tm = new DefaultObjectTableModel<MyObject>(
				MyObject.class);
		// Before
		/*
		 * new DefaultTableModel(new Object[][] {}, new String[] { "Prename",
		 * "Name", "Age" }) { private static final long serialVersionUID = 1L;
		 * 
		 * Class<?>[] columnTypes = new Class[] { String.class, String.class,
		 * int.class };
		 * 
		 * public Class<?> getColumnClass(int columnIndex) { return
		 * columnTypes[columnIndex]; }
		 * 
		 * boolean[] columnEditables = new boolean[] { true, true, false };
		 * 
		 * public boolean isCellEditable(int row, int column) { return
		 * columnEditables[column]; } };
		 */

		JTable table = new JTable(tm);
		table.setAutoCreateRowSorter(true);
		disp.add(new JScrollPane(table), BorderLayout.CENTER);

		for (int i = 0; i < 20; i++) {

			// Now
			tm.addRow(new MyObject("Prename " + i, "Name " + i, i + 1));
			// Before
			/*
			 * DefaultTableModel.addRow(new Object[]{obj.getPrename(),
			 * obj.getName(), obj.getAge()});
			 */
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

		@Column(value = "Prename", editable = true, index=1)
		String prename;

		private String name;

		int age;

		public MyObject(String prename, String name, int age) {
			this.prename = prename;
			this.setName(name);
			this.age = age;
		}

		@Column(value = "Age",index=2)
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
