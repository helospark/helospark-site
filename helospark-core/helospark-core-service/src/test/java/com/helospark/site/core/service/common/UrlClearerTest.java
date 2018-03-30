package com.helospark.site.core.service.common;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UrlClearerTest {
    private UrlClearer urlClearer;

    @BeforeEach
    public void setUp() {
        urlClearer = new UrlClearer();
    }

    @ParameterizedTest
    @MethodSource("createTestData")
    public void test(String input, String expected) {
        // GIVEN

        // WHEN
        String result = urlClearer.clearString(input);

        // THEN
        assertThat(result, is(expected));
    }

    private static Stream<Arguments> createTestData() {
        return Stream.of(
                Arguments.of("testdata", "testdata"),
                Arguments.of("test data", "test-data"),
                Arguments.of("test/data", "testdata"));
    }

}
