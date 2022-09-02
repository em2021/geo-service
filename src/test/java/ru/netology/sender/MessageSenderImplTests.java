package ru.netology.sender;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class MessageSenderImplTests {

    MessageSender sut;

    @BeforeAll
    public static void startAll() {
        System.out.println("MessageSenderImpl tests started");
    }

    @BeforeEach
    public void init() {
        System.out.println("MessageSenderImpl test started");
    }

    @AfterEach
    public void finished() {
        System.out.println("MessageSenderImpl test completed");
    }

    @AfterAll
    public static void finishedAll() {
        System.out.println("MessageSenderImpl tests completed");
    }

    @ParameterizedTest
    @MethodSource("source")
    public void testSend(String ip,
                         String expected) {
        // given:
        GeoService geoServiceMock = Mockito.mock(GeoService.class);
        Mockito.when(geoServiceMock.byIp(Mockito.startsWith("172.")))
                .thenReturn(new Location("Moscow", Country.RUSSIA, null, 0));
        Mockito.when(geoServiceMock.byIp(Mockito.startsWith("96.")))
                .thenReturn(new Location("New York", Country.USA, null, 0));
        Mockito.when(geoServiceMock.byIp(Mockito.startsWith("103.")))
                .thenReturn(new Location(null, null, null, 0));
        LocalizationService localizationServiceMock = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationServiceMock.locale(Country.RUSSIA))
                .thenReturn("Добро пожаловать");
        Mockito.when(localizationServiceMock.locale(Country.USA))
                .thenReturn("Welcome");
        sut = new MessageSenderImpl(geoServiceMock, localizationServiceMock);
        Map<String, String> headers = new HashMap<String, String>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, ip);
        // when:
        String result = sut.send(headers);
        System.out.println(); // Перенос строки, т.к. send() вызывает printf без переноса
        // then:
        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> source() {
        return Stream.of(
                Arguments.of("172.0.32.11",
                        "Добро пожаловать"),
                Arguments.of("172.0.0.1",
                        "Добро пожаловать"),
                Arguments.of("96.44.183.149",
                        "Welcome"),
                Arguments.of("96.0.0.1",
                        "Welcome"),
                Arguments.of(new String(),
                        "Welcome"),
                Arguments.of("103.",
                        null));
    }
}