package net.rnvn.servletx.annotations;

/*
 * the controller mapping annotation is used to set the path of the controller
 */
public @interface ControllerMapping {
    String value() default "";
}