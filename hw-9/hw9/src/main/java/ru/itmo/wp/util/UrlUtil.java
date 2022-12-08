package ru.itmo.wp.util;

public class UrlUtil {
    public static Long getLongIdOrNull(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException ignored) {
            return null;
        }
    }
}
