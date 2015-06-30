package bno.swing2.utils;

import java.util.HashMap;
import java.util.Map;

public class ClassUtils {

	private static final Map<Class<?>, Class<?>> wrapperMapping = new MapBuilder<Class<?>, Class<?>>(
			new HashMap<Class<?>, Class<?>>())
			.put(boolean.class, Boolean.class).put(byte.class, Byte.class)
			.put(char.class, Character.class).put(double.class, Double.class)
			.put(float.class, Float.class).put(int.class, Integer.class)
			.put(long.class, Long.class).put(short.class, Short.class)
			.put(void.class, Void.class).build();

	/**
	 * Test, if <strong>assignable</strong> could be assigned to
	 * <strong>container</strong>.<br>
	 * 
	 * <pre>
	 * <code>
	 * container = assignable;
	 * </code>
	 * </pre>
	 * 
	 * @param container
	 * @param assignable
	 * @return
	 */
	public static boolean isAssignable(Class<?> container, Class<?> assignable) {

		return getWrappedClass(container).isAssignableFrom(
				getWrappedClass(assignable));
	}

	/**
	 * Transform the class to its wrapper class if any exists. Else the class
	 * itself is returned.
	 * 
	 * @param c
	 *            Class from which to get the wrapper class
	 * @return A non primitive class
	 */
	@SuppressWarnings("unchecked")
	private static <T> Class<T> getWrappedClass(Class<T> c) {
		return (c.isPrimitive()) ? (Class<T>) wrapperMapping.get(c) : c;
	}

}