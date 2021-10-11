package com.modzo.ors.commons;

public final class SqlHelper {

    private SqlHelper() {
    }

    public static String toILikeSearch(String title) {
        return "%" + title.replaceAll(" ", "%") + "%";
    }

}
