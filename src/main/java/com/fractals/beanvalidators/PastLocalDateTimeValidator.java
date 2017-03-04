package com.fractals.beanvalidators;

import java.time.LocalDateTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSR-303 bean past LocalDateTime validation class.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
public class PastLocalDateTimeValidator implements ConstraintValidator<PastLocalDateTime, LocalDateTime> {
    @Override
    public void initialize(PastLocalDateTime constraintAnnotation) {}
    
    @Override
    public boolean isValid(LocalDateTime value, ConstraintValidatorContext context) {
        LocalDateTime now = LocalDateTime.now();
        return value == null || now.isAfter(value) || now.isEqual(value); 
    }
}
