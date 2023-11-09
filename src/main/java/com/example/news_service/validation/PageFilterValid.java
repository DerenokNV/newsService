package com.example.news_service.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PageFilterValidValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PageFilterValid {

  String message() default  "Поля пагинации должны быть указаны.";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};


}
