package ru.itmo.wp.util;

import java.util.regex.Pattern;

public class LanguageUtil {
    public static final String KEY = "lang";

    public static boolean isLanguageParameter(String param) {
        return param != null && param.length() == 2 && Pattern.compile("[a-z]+").matcher(param).matches();
    }

    public static String getPageNameWithLanguage(Object lang, String pageName) {
        if (lang != null && !lang.equals("en")) {
            return pageName + "_" + lang;
        }
        return pageName;
    }
}
