package ru.itmo.wp.models;

import java.util.*;

public class MessagesData {
    public static final String USER_KEY = "user";
    public static final String TEXT_KEY = "text";

    private static class Message {
        public String user;
        public String text;

        public Message(String user, String text) {
            this.user = user;
            this.text = text;
        }
    }

    private static final Set<String> USERS = new HashSet<>();
    private static final List<Message> MESSAGES = new ArrayList<>();

    public static boolean isNotAuthorizedUser(String user) {
        return !USERS.contains(user);
    }

    public static void addUser(String user) {
        if (isNotAuthorizedUser(user)) {
            MessagesData.USERS.add(user);
        }
    }

    public static void addText(String user, String text) {
        MessagesData.MESSAGES.add(new Message(user, text));
    }

    public static Object toJsonObject() {
        ArrayList<Map<String, String>> jsonArray = new ArrayList<>();
        for (Message message : MESSAGES) {
            Map<String, String> jsonObject = new HashMap<>();
            jsonObject.put(USER_KEY, message.user);
            jsonObject.put(TEXT_KEY, message.text);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
