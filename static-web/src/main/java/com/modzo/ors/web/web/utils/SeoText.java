package com.modzo.ors.web.web.utils;

import java.util.Objects;

public class SeoText {

    public static String from(String text) {
        if (Objects.isNull(text)) {
            return text;
        }
        return text.replaceAll(" ?- ?", "-") // remove spaces around hyphens
                .replaceAll("[ ']", "-") // turn spaces and quotes into hyphens
                .replaceAll("[^0-9a-zA-Z-]", "");
    }
}
