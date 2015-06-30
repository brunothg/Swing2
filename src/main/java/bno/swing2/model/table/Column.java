package bno.swing2.model.table;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.METHOD, ElementType.FIELD })
public @interface Column {

	/**
	 * The name of the column
	 * 
	 * @return The columns name
	 */
	public String value();

	public boolean editable() default false;

	/**
	 * The column position. Default behavior will use the order of the
	 * annotations.
	 * 
	 * @return The position for the column
	 */
	public int index() default -1;
}