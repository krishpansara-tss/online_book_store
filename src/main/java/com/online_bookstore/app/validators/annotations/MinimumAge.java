package com.online_bookstore.app.validators.annotations;


import com.online_bookstore.app.validators.impl.MinimumAgeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MinimumAgeValidator.class)
public @interface MinimumAge {

    String message() default "User must be at least {value} years old.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();

}
