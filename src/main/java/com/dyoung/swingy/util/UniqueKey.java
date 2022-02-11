package com.dyoung.swingy.util;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {UniqueKey.UniqueKeyValidator.class})
public @interface UniqueKey {
    String columnName() default "";

    String message() default "This user already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class UniqueKeyValidator implements ConstraintValidator<UniqueKey, String> {

        @Override
        public void initialize(UniqueKey constraintAnnotation) {
            String columnName = constraintAnnotation.columnName();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            ArrayList<String> arr = DataBase.selectNames();
            for (String str : arr) {
                if (Objects.equals(value, str))
                    return false;
            }
            return true;
        }
    }
}