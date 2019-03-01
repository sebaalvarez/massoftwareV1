package com.massoftware.backend.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
@Inherited
public @interface FieldConfAnont {

	String label() default "";
	String labelError() default "";
	boolean unique() default false;
	boolean readOnly() default false;
	boolean required() default false;
	float columns() default 20;
	int maxLength() default 100;
	String minValue() default "1";
	String maxValue() default Integer.MAX_VALUE + "";
	String mask() default "";
	
//	boolean visible() default true;

}
