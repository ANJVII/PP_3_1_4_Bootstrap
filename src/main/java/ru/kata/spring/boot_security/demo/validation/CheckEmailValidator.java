package ru.kata.spring.boot_security.demo.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckEmailValidator
        implements ConstraintValidator<CheckEmail, String> {

    private String endOfEmail;

    @Override
    public void initialize(CheckEmail constraintAnnotation) { //инициализация аннотации
        endOfEmail = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String enteredValue,
                           ConstraintValidatorContext constraintValidatorContext) {
        return enteredValue.endsWith(endOfEmail);
    }
}
