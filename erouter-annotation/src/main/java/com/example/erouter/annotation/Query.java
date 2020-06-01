package com.example.erouter.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author EdisonLi
 * @version 1.0
 * @since 2020-05-31
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Query {
    String key();

    String desc() default "";
}
