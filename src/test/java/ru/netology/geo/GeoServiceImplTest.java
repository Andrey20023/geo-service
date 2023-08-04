package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.unitils.reflectionassert.ReflectionAssert.assertReflectionEquals;

class GeoServiceImplTest {

    GeoService sut;

    @org.junit.jupiter.api.BeforeEach
    public void BeforeEach() {
        sut = new GeoServiceImpl();
    }

    @org.junit.jupiter.api.AfterEach
    public void AfterEach() {
        sut = null;
    }

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.44.32.11", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.44.32.11", new Location("New York", Country.USA, null, 0))
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void byIp(String ip, Location expected) {
        Location result = sut.byIp(ip);
        assertReflectionEquals(expected, result);
    }

    @org.junit.jupiter.api.Test
    void byCoordinates() {
        {
            double latitude = 54.5;
            double longitude = 55.6;
            Class<RuntimeException> expected = RuntimeException.class;
            Assertions.assertThrowsExactly(expected, () -> sut.byCoordinates(latitude, longitude));
        }
    }
}
