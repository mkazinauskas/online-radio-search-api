package com.modzo.ors.web.utils;

import java.util.Objects;

public class SeoText {

    public static String from(String text) {
        if (Objects.isNull(text)) {
            return null;
        }
        return text
                .toLowerCase()
                .replaceAll("-", " ")
                .trim()
                .replaceAll(" ?- ?", "-") // remove spaces around hyphens
                .replaceAll("[ ']", "-") // turn spaces and quotes into hyphens
                .replaceAll("[^0-9a-zA-Z-]", "")
                .replaceAll("(-)\\1+", "-");
    }

    public static String revert(String seoText) {
        if (Objects.isNull(seoText)) {
            return null;
        }
        return seoText.replaceAll("-", " ");
    }
}