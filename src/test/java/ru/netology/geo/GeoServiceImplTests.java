package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceImplTests {
    GeoService sut;

    @BeforeAll
    public static void started() {
        System.out.println("GeoServiceImpl tests started");
    }

    @BeforeEach
    public void init() {
        sut = new GeoServiceImpl();
        System.out.println("GeoServiceImpl test started");
    }

    @AfterEach
    public void finished() {
        System.out.println("GeoServiceImpl test completed");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("GeoServiceImpl tests completed");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testByIp(String ip, Location expected) {
        // when:
        Location result = sut.byIp(ip);
        // then:
        Assertions.assertTrue(new ReflectionEquals(expected).matches(result));
    }

    @Test
    public void testByCoordinates_shouldThrowException() {

        // given:
        double latitude = Mockito.anyDouble();
        double longitude = Mockito.anyDouble();
        Class expected = RuntimeException.class;
        // then:
        Assertions.assertThrows(expected,
                // when:
                () -> sut.byCoordinates(latitude, longitude));
    }

    private static Stream<Arguments> source() {
        // given:
        return Stream.of(
                Arguments.of("127.0.0.1",
                        new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11",
                        new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149",
                        new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.0.0.1",
                        new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.",
                        new Location("New York", Country.USA, null, 0)),
                Arguments.of("0.0.0.0", null));
    }
}
