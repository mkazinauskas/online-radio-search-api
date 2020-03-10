package com.modzo.ors.web.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SeoTextTest {

    @Test
    void shouldStripDashes() {
        String result = SeoText.from("Ludwig van Beethoven (1770-1827) - 'Sonatine' per");
        Assertions.assertEquals("ludwig-van-beethoven-1770-1827-sonatine-per", result);
    }

    @Test
    void shouldTrimWhiteSpace() {
        String result = SeoText.from("Test   ");
        Assertions.assertEquals("test", result);
    }

    @Test
    void shouldLowercaseLetters() {
        String result = SeoText.from("Test");
        Assertions.assertEquals("test", result);
    }

    @Test
    void shouldChangeSpacesToDashes() {
        String result = SeoText.from("Test go");
        Assertions.assertEquals("test-go", result);
    }

    @Test
    void shouldStripLettersAndNumbersToDashes() {
        String result = SeoText.from("L$%e#t$¥s@go");
        Assertions.assertEquals("letsgo", result);
    }
}