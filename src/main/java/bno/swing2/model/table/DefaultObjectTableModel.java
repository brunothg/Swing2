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

	private ObjectColumn<T>[] columns;

	public DefaultObjectTableModel(Class<T> type) {

		this.type = type;

		initialize();
	}

	@SuppressWarnings("unchecked")
	private void initialize() {

		List<ObjectColumn<T>> columns = new LinkedList<ObjectColumn<T>>();

		// Search fields
		Field[] fields = type.getDeclaredFields();
		for (Field field : fields) {
			for (Annotation annotation : field.getDeclaredAnnotations()) {
				if (annotation instanceof Column) {
					Column _column = (Column) annotation;
					ObjectColumn<T> column = new FieldColumn<T>(_column, field);
					if (_column.index() < 0) {
						columns.add(column);
					} else {
						columns.add(_column.index(), column);
					}
				}
			}
		}

		// Search methods
		Method[] methods = type.getDeclaredMethods();
		for (Method method : methods) {
			for (Annotation annotation : method.getDeclaredAnnotations()) {
				if (annotation instanceof Column) {
					Column _column = (Column) annotation;
					ObjectColumn<T> column = new MethodColumn<T>(_column,
							method);
					if (_column.index() < 0) {
						columns.add(column);
					} else {
						columns.add(_column.index(), column);
					}
				}
			}
		}

		this.columns = columns.toArray(new ObjectColumn[columns.size()]);
	}

	protected ObjectColumn<T> getColumn(int columnIndex) {

		return columns[columnIndex];
	}

	@Override
	public String getColumnName(int columnIndex) {

		return getColumn(columnIndex).getName();
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

		return getColumn(columnIndex).getType();
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

		T removed = rows.remove(rowIndex);

		fireTableRowsDeleted(rowIndex, rowIndex);
		return removed;
	}

	protected static abstract class ObjectColumn<T> {

		private String name;
		private Class<?> type;
		private boolean editable;

		public ObjectColumn(Column column) {

			this.name = column.value();
			this.editable = column.editable();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Class<?> getType() {
			return type;
		}

		public void setType(Class<?> type) {
			this.type = type;
		}

		public abstract Object getValue(T row);

		public abstract Object setValue(T row, Object value);

		public boolean isEditable() {
			return editable;
		}

		public void setEditable(boolean editable) {
			this.editable = editable;
		}
	}

	protected static class FieldColumn<T> extends ObjectColumn<T> {

		public FieldColumn(Column column, Field f) {
			super(column);
			setType(f.getType());
		}

		@Override
		public Object getValue(T row) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object setValue(T row, Object value) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	protected static class MethodColumn<T> extends ObjectColumn<T> {

		public MethodColumn(Column column, Method m) {
			super(column);
			setType(m.getReturnType());
		}

		@Override
		public Object getValue(T row) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Object setValue(T row, Object value) {
			// TODO Auto-generated method stub
			return null;
		}

	}
}
