package com.fractals.beanvalidators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import jodd.mail.EmailAddress;

/**
 * JSR-303 User bean email validation class.
 *
 * @author Aline Shulzhenko
 * @version 04/03/2017
 * @since 1.8
 */
public class EmailValidator implements ConstraintValidator<EmailCheck, String> {
    @Override
    public void initialize(EmailCheck constraintAnnotation) {}
    
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || (new EmailAddress(value)).isValid(); 
    }
}
