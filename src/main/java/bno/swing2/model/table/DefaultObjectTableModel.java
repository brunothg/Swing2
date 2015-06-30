package bno.swing2.model.table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class DefaultObjectTableModel<T> extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private Vector<T> rows = new Vector<T>();
	private Class<T> type;

	private Column[] columns;

	public DefaultObjectTableModel(Class<T> type) {

		this.type = type;

		initialize();
	}

	private void initialize() {

		List<Column> columns = new LinkedList<Column>();

		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				if (annotation instanceof Column) {
					Column column = (Column) annotation;
					if (column.index() < 0) {
						columns.add(column);
					} else {
						columns.add(column.index(), column);
					}
				}
			}
		}

		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
			for (Annotation annotation : method.getDeclaredAnnotations()) {
				if (annotation instanceof Column) {
					Column column = (Column) annotation;
					if (column.index() < 0) {
						columns.add(column);
					} else {
						columns.add(column.index(), column);
					}
				}
			}
		}

		this.columns = columns.toArray(new Column[columns.size()]);
	}

	protected Column getColumn(int columnIndex) {

		return columns[columnIndex];
	}

	@Override
	public String getColumnName(int columnIndex) {

		return getColumn(columnIndex).value();
	}

	@Override
	public int getRowCount() {

		return rows.size();
	}

	@Override
	public int getColumnCount() {

		return columns.length;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		// TODO isCellEditable
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		// TODO getColumnClass
		return super.getColumnClass(columnIndex);
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO getValueAt
		return null;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO setValueAt
	}

	/**
	 * Add a new row to the table model
	 * 
	 * @param object
	 *            The object, that represents the new row
	 */
	public void addRow(T object) {

		addRow(-1, object);
	}

	/**
	 * Add a new row at specific position to the table model. The element at
	 * this position (and the ones behind) will be shift to the right.
	 * 
	 * @param rowIndex
	 *            The index for the new row.
	 * @param object
	 *            The object, that represents the new row
	 */
	public void addRow(int rowIndex, T object) {

		if (rowIndex < 0) {
			rowIndex = rows.size();
			rows.add(object);
		} else {
			rows.add(rowIndex, object);
		}

		fireTableRowsInserted(rowIndex, rowIndex);
	}

	/**
	 * Get the row object at a specific position.
	 * 
	 * @param rowIndex
	 *            The index of the row
	 * @return The object for the row
	 */
	public T getRow(int rowIndex) {

		return rows.get(rowIndex);
	}

	/**
	 * Search for the object (using equals method) and then delete it. Return
	 * null if it could not be found. Otherwise the row object is returned.
	 * 
	 * @param object
	 *            The object, that should be deleted
	 * @return Null or the row object
	 */
	public T removeRow(T object) {

		for (int i = 0; i < rows.size(); i++) {

			if (rows.get(i).equals(object)) {

				return removeRow(i);
			}
		}

		return null;
	}

	/**
	 * Removes a row by its index.
	 * 
	 * @param rowIndex
	 *            The index of the row
	 * @return The row object that was deleted
	 */
	public T removeRow(int rowIndex) {

		return rows.remove(rowIndex);
	}
}
