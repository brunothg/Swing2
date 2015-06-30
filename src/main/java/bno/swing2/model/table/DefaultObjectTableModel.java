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

	public void addRow(T object) {

		addRow(-1, object);
	}

	public void addRow(int rowIndex, T object) {
		// TODO addRow
	}

	public T getRow(int rowIndex) {
		// TODO getRow
		return null;
	}

	public T removeRow(T object) {
		// TODO removeRow
		return null;
	}

	public T removeRow(int rowIndex) {
		// TODO removeRow
		return null;
	}
}
