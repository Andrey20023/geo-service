package ru.netology.i18n;

import org.junit.Assert;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

class LocalizationServiceImplTest {

    LocalizationService sut = new LocalizationServiceImpl();

    static Stream<Arguments> dataProvider() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome")
        );
    }

    @ParameterizedTest
    @MethodSource("dataProvider")
    void locale(Country country, String expected) {
        String result = sut.locale(country);
        Assert.assertEquals(result, expected);
    }


}