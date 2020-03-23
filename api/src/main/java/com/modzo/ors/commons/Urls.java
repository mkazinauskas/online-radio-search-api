package com.modzo.ors.commons;

import java.net.MalformedURLException;
import java.net.URL;

public class Urls {

    public static boolean isValid(String url) {
        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    public static boolean isNotValid(String url) {
        return !isValid(url);
    }
}
