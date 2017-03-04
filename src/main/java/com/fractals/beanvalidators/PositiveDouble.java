package com.fractals.beanvalidators;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * Custom annotation for double bean validation. Verifies that the value is positive.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
@Target({METHOD, FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = PositiveDoubleValidator.class)
public @interface PositiveDouble {
    String message() default "{com.fractals.PositiveDouble.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}