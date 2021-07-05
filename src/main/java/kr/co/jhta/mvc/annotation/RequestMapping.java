package kr.co.jhta.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kr.co.jhta.mvc.annotation.method.RequestMethod;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {

	RequestMethod method () default RequestMethod.GET;
	String path() default "";
}
