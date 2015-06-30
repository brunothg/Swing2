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

	/**
	 * Set the method used for setting values for this column. If this
	 * annotation is at a field the default behavior directly manipulates the
	 * field. If it is a method the setter is needed or the column can't be
	 * editable.<br>
	 * The setter method must be declared in the class, where the annotation is
	 * put.<br>
	 * Also the method signature of the setter must be compatible to
	 * .&lt;setter&gt;(Class&lt;[returnType|fieldType]&gt;);<br>
	 * For example:<br>
	 * <code><pre>
	 * 	&#64;Column(value="Age", editable=true, setter="setAge")
	 * 	public int getAge(){ 
	 * 		return age;
	 * 	} 
	 * 
	 * 	public void setAge(int age){
	 * 		this.age = age;
	 * 	}
	 * </pre></code>
	 * 
	 * @return The setter method
	 */
	public String setter() default "";
}