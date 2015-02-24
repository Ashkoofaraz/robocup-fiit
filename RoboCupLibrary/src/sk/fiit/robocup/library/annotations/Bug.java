/**
 * Name:    Bug.java
 * Created: Dec 8, 2010
 * 
 * @author: relation
 */
package sk.fiit.robocup.library.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mark 
 * 
 * @author relation
 *
 */
@Retention(RetentionPolicy.SOURCE)
public @interface Bug {
	String[] value();
}
