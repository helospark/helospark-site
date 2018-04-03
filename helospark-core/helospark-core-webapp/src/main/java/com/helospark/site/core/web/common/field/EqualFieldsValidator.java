package com.helospark.site.core.web.common.field;

import java.lang.reflect.Field;
import java.util.Objects;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EqualFieldsValidator implements ConstraintValidator<EqualFields, Object> {
    private String firstField;
    private String secondField;

    @Override
    public void initialize(EqualFields constraintAnnotation) {
        firstField = constraintAnnotation.firstField();
        secondField = constraintAnnotation.secondField();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object firstValue = getFieldValue(value, firstField);
        Object secondValue = getFieldValue(value, secondField);

        return Objects.equals(firstValue, secondValue);
    }

    private Object getFieldValue(Object value, String fieldName) {
        try {
            Field extractedField = value.getClass().getDeclaredField(fieldName);
            extractedField.setAccessible(true);
            return extractedField.get(value);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
