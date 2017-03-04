package com.fractals.beanvalidators;

import java.time.LocalDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSR-303 bean past LocalDate validation class.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
public class PastLocalDateValidator implements ConstraintValidator<PastLocalDate, LocalDate> {
    @Override
    public void initialize(PastLocalDate constraintAnnotation) {}
    
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        LocalDate now = LocalDate.now();
        return value == null || now.isAfter(value) || now.isEqual(value); 
    }
}
