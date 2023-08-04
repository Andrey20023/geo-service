package ru.netology.sender;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.stream.Stream;

import static ru.netology.geo.GeoServiceImpl.*;

class MessageSenderImplTest {
    MessageSender sut;
    //LocalizationService localizationService = Mockito.spy(new LocalizationServiceImpl());/
    //GeoService geoService = Mockito.spy(new GeoServiceImpl());
    @Mock

    LocalizationService localizationService = Mockito.mock(LocalizationService.class);
    @Mock

    GeoService geoService = Mockito.mock(GeoService.class);



    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(new HashMap<String, String>() {{
                    put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");
                }}, "Добро пожаловать"),
                Arguments.of(new HashMap<String, String>() {{
                    put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");
                }}, "Welcome"),
                Arguments.of(new HashMap<String, String>() {{
                    put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.44.183.149");
                }}, "Добро пожаловать")
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void send(HashMap headers, String expected) {
        Answer<Location> answerGeoService = new Answer<Location>() {
            @Override
            public Location answer(InvocationOnMock invocation) throws Throwable {
                String ip = invocation.getArgument(0, String.class);
                if (LOCALHOST.equals(ip)) {
                    return new Location(null, null, null, 0);
                } else if (MOSCOW_IP.equals(ip)) {
                    return new Location("Moscow", Country.RUSSIA, "Lenina", 15);
                } else if (NEW_YORK_IP.equals(ip)) {
                    return new Location("New York", Country.USA, " 10th Avenue", 32);
                } else if (ip.startsWith("172.")) {
                    return new Location("Moscow", Country.RUSSIA, null, 0);
                } else if (ip.startsWith("96.")) {
                    return new Location("New York", Country.USA, null, 0);
                }
                return null;
            }
        };
        Mockito.when(geoService.byIp(Mockito.any(String.class))).thenAnswer(answerGeoService);
        Answer<String> answerLocal = new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Country country = invocation.getArgument(0, Country.class);
                if (country.equals(Country.RUSSIA)) {
                    return "Добро пожаловать";
                }
                return "Welcome";
            }
        };
        Mockito.when(localizationService.locale(Mockito.any(Country.class))).thenAnswer(answerLocal);

        sut = new MessageSenderImpl(geoService, localizationService);
        String result = sut.send(headers);
        Assert.assertEquals(result, expected);
    }
}

