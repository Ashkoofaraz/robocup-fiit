package sk.fiit.robocup.library.review;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  ReviewOk.java
 *
 *  Class or a method can be marked by this annotation if it fulfills all
 *  of those predicates:
 *  	1. is thorougly tested by a unit test
 *  	2. was judged by a code review
 *  	3. was tested against a server
 *
 *@Title	Jim
 *@author	marosurbanec
 */
@Retention(value=RetentionPolicy.SOURCE)
@Target(value=ElementType.TYPE)
@ReviewOk
public @interface ReviewOk {
}