package bno.swing2.model.table;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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
					ObjectColumn<T> column = new FieldColumn<T>(_column, type,
							field);
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
					ObjectColumn<T> column = new MethodColumn<T>(_column, type,
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

	/**
	 * Returns the value given by the {@link Column} Annotation.
	 * 
	 * @see Column#value()
	 */
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

	/**
	 * Returns the value given by the {@link Column} Annotation.
	 * 
	 * @see Column#editable()
	 */
	public boolean isCellEditable(int rowIndex, int columnIndex) {

		return getColumn(columnIndex).isEditable();
	}

	/**
	 * Returns the type of the annotated field or the return type of the
	 * annotated method.
	 */
	public Class<?> getColumnClass(int columnIndex) {

		return getColumn(columnIndex).getType();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {

		try {

			return getColumn(columnIndex).getValue(rows.get(rowIndex));
		} catch (IllegalArgumentException | InvocationTargetException
				| IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Sets the annotated field value
	 * 
	 * @see #getValueAt(int, int)
	 * @throws RuntimeException
	 *             if there are problems with the access through reflection
	 */
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {

		try {
			getColumn(columnIndex).setValue(rows.get(rowIndex), aValue);
		} catch (IllegalArgumentException | InvocationTargetException
				| IllegalAccessException e) {

			throw new RuntimeException(e);
		}

		fireTableCellUpdated(rowIndex, columnIndex);
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

	protected static abstract class ObjectColumn<V> {

		private Class<?> self;

		private String name;
		private Class<?> type;
		private boolean editable;

		private Method setter;

		public ObjectColumn(Column column, Class<?> self) {

			this.setSelf(self);
			this.name = column.value();
			this.editable = column.editable();
		}

		protected static Method findSetter(String name, Class<?> type,
				Class<?> self) {

			if (name == null || name.trim().isEmpty()) {
				return null;
			}

			Method[] methods = self.getDeclaredMethods();
			for (Method method : methods) {

				if (!method.getName().equals(name)) {
					continue;
				}

				Class<?>[] parameters = method.getParameterTypes();
				if (parameters.length != 1) {
					continue;
				}

				if (parameters[0].isInstance(type)
						|| parameters[0].equals(type)) {

					return method;
				}
			}

			return null;
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

		public boolean isEditable() {
			return editable;
		}

		public void setEditable(boolean editable) {
			this.editable = editable;
		}

		public Class<?> getSelf() {
			return self;
		}

		public void setSelf(Class<?> self) {
			this.self = self;
		}

		public abstract Object getValue(V row) throws IllegalArgumentException,
				IllegalAccessException, InvocationTargetException;

		public abstract void setValue(V row, Object value)
				throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException;

		public Method getSetter() {
			return setter;
		}

		public void setSetter(Method setter) {
			this.setter = setter;
		}

	}

	protected static class FieldColumn<V> extends ObjectColumn<V> {

		private Field f;

		public FieldColumn(Column column, Class<?> self, Field f) {
			super(column, self);
			this.f = f;
			setType(f.getType());
			setSetter(ObjectColumn.findSetter(column.setter(), getType(),
					getSelf()));
		}

		@Override
		public Object getValue(V row) throws IllegalArgumentException,
				IllegalAccessException {

			try {
				f.setAccessible(true);
			} catch (SecurityException e) {
			}
			return f.get(row);
		}

		@Override
		public void setValue(V row, Object value)
				throws IllegalArgumentException, IllegalAccessException,
				InvocationTargetException {

			if (getSetter() != null) {

				try {
					getSetter().setAccessible(true);
				} catch (SecurityException e) {
				}

				getSetter().invoke(row, value);

			} else {

				try {
					f.setAccessible(true);
				} catch (SecurityException e) {
				}

				f.set(row, value);
			}
		}
	}

	protected static class MethodColumn<V> extends ObjectColumn<V> {

		private Method m;

		public MethodColumn(Column column, Class<?> self, Method m) {
			super(column, self);
			this.m = m;
			setType(m.getReturnType());
			setSetter(ObjectColumn.findSetter(column.setter(), getType(),
					getSelf()));
		}

		@Override
		public boolean isEditable() {

			if (getSetter() == null) {

				return false;
			}

			return super.isEditable();
		}

		@Override
		public Object getValue(V row) throws IllegalAccessException,
				IllegalArgumentException, InvocationTargetException {

			try {
				m.setAccessible(true);
			} catch (SecurityException e) {
			}
			return m.invoke(row);
		}

		@Override
		public void setValue(V row, Object value)
				throws IllegalAccessException, IllegalArgumentException,
				InvocationTargetException {

			if (getSetter() != null) {

				try {
					getSetter().setAccessible(true);
				} catch (SecurityException e) {
				}

				getSetter().invoke(row, value);

			} else {

				throw new IllegalAccessException(
						"No setter is set for this column");
			}
		}

	}
}
