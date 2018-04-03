package com.helospark.site.core.web.common.password;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.stream.Stream;

import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorContext.ConstraintViolationBuilder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

public class ValidPasswordValidatorTest {
    @Mock
    private ConstraintValidatorContext constraintValidatorContext;
    @Mock
    private ConstraintViolationBuilder constraintViolationBuilder;

    private ValidPasswordValidator underTest;

    @BeforeEach
    public void setUp() {
        initMocks(this);
        underTest = new ValidPasswordValidator();
        when(constraintValidatorContext.buildConstraintViolationWithTemplate(Mockito.anyString())).thenReturn(constraintViolationBuilder);
    }

    @ParameterizedTest
    @MethodSource("passwordTestDataSource")
    public void testWrongPassword() {

        boolean result = underTest.isValid("asd", constraintValidatorContext);

        assertThat(result, is(false));
    }

    private static Stream<Arguments> passwordTestDataSource() {
        return Stream.of(
                Arguments.of("pass"),
                Arguments.of("pass1"),
                Arguments.of("pass12121pass12121pass12121pass12121pass12121pass12121pass12121pass12121pass12121pass12121"),
                Arguments.of(""));
    }

    @ParameterizedTest
    @MethodSource("correctPasswordTestDataSource")
    public void testCorrectPasswords(String input) {

        boolean result = underTest.isValid(input, constraintValidatorContext);

        assertThat(result, is(true));
    }

    private static Stream<Arguments> correctPasswordTestDataSource() {
        return Stream.of(
                Arguments.of("password1"),
                Arguments.of("SecurePassword!!!?"),
                Arguments.of("This is a sentence"),
                Arguments.of("Whut????????"));
    }

}
