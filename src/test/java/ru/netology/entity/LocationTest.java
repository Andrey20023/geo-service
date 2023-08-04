package ru.netology.entity;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LocationTest {
    Location location;

    @BeforeEach
    public void BeforeEach() {
        location = new Location("Moscow", Country.RUSSIA, "Lenina", 15);
    }

    @AfterEach
    public void AfterEach() {
        Location location = null;
    }

    @Test
    public void getCity() {
        String actual = location.getCity();
        String expected = "Moscow";
        Assert.assertEquals(actual, expected);
    }

    @Test
    void getCountry() {
        Country actual = location.getCountry();
        Country expected = Country.RUSSIA;
        Assert.assertEquals(actual, expected);
    }

    @Test
    void getStreet() {
        String actual = location.getStreet();
        String expected = "Lenina";
        Assert.assertEquals(actual, expected);
    }

    @Test
    void getBuiling() {
        int actual = location.getBuiling();
        int expected = 15;
        Assert.assertEquals(actual, expected);
    }
}