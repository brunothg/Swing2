package bno.swing2.utils;

/**
 * 
 * Helper class for null values
 */
public class Null {

	/**
	 * Test if it is a null value and replace it
	 * 
	 * @param test
	 *            Test value
	 * @param replace
	 *            Replace value
	 */
	public static <T> T nvl(T test, T replace) {

		if (test == null) {

			return replace;
		}

		return test;
	}

	/**
	 * Test if at least one value is null
	 */
	public static boolean isAnyNull(Object... objs) {

		for (int i = 0; i < objs.length; i++) {

			if (objs[i] == null) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Test if at least one string is empty or null
	 */
	public static boolean isAnyEmpty(String... strings) {

		for (int i = 0; i < strings.length; i++) {

			if (strings[i] == null || strings[i].isEmpty()) {

				return true;
			}
		}

		return false;
	}

	/**
	 * Test if all values are null
	 */
	public static boolean isNull(Object... objs) {

		for (int i = 0; i < objs.length; i++) {

			if (objs[i] != null) {

				return false;
			}
		}

		return true;
	}

	/**
	 * Test if all values aren't null
	 */
	public static boolean isNotNull(Object... objs) {

		for (int i = 0; i < objs.length; i++) {

			if (objs[i] == null) {

				return false;
			}
		}

		return true;
	}
}
