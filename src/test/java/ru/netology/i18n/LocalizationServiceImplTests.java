package ru.netology.i18n;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTests {

    LocalizationService sut;

    @BeforeAll
    public static void started() {
        System.out.println("LocalizationServiceImpl tests started");
    }

    @BeforeEach
    public void init() {
        sut = new LocalizationServiceImpl();
        System.out.println("LocalizationServiceImpl test started");
    }

    @AfterEach
    public void finished() {
        System.out.println("LocalizationServiceImpl test completed");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("LocalizationServiceImpl tests completed");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testLocale(Country country, String expected) {
        // when:
        String result = sut.locale(country);
        // then:
        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"));
    }
}