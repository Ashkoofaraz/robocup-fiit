/**
 * Name:    TestCovered.java
 * Created: Dec 8, 2010
 * 
 * @author: relation
 */
package sk.fiit.robocup.library.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Mark elements of code, which are covered by tests.
 * 
 * @author relation
 *
 */
@Retention(RetentionPolicy.SOURCE)
public @interface TestCovered {

}
