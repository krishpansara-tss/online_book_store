package com.online_bookstore.app.validators.impl;

import com.online_bookstore.app.validators.annotations.MinimumAge;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MinimumAgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {
    private int minimumAge;

    @Override
    public void initialize(MinimumAge constraintAnnotation) {
        minimumAge = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(LocalDate dob, ConstraintValidatorContext context) {
        if (dob == null) {
            return true;
        }
        int age = Period.between(dob, LocalDate.now()).getYears();
        return age >= minimumAge;
    }
}
