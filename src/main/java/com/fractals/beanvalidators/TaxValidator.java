package com.fractals.beanvalidators;

import java.math.BigDecimal;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSR-303 Double tax bean validation class.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
public class TaxValidator implements ConstraintValidator<TaxCheck, Double> {
    @Override
    public void initialize(TaxCheck constraintAnnotation) {}
    
    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        BigDecimal bd = BigDecimal.valueOf(value);
        return BigDecimal.ZERO.compareTo(bd) <= 0 && BigDecimal.ONE.compareTo(bd) > 0;
    }
}
