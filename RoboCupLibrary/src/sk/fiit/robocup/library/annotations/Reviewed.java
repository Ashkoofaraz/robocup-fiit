/**
 * Name:    Reviewed.java
 * Created: Dec 8, 2010
 * 
 * @author: relation
 */
package sk.fiit.robocup.library.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used for marking reviewed code.
 * 
 * Parameters: 
 * <code>date</code>	date of revision, format: "dd.mm.yy"
 * <code>result</code>  result of revision	
 * 
 * @author relation
 *
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Reviewed {
	String date();
	String result();
	String person();
}
