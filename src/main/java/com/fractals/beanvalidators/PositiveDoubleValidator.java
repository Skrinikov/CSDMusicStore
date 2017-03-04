package com.fractals.beanvalidators;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSR-303 Double bean validation class. Verifies that the double is positive.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
public class PositiveDoubleValidator implements ConstraintValidator<PositiveDouble, Double> {
    @Override
    public void initialize(PositiveDouble constraintAnnotation) {}
    
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return BigDecimal.ZERO.compareTo(bd) <= 0;
    }
}
