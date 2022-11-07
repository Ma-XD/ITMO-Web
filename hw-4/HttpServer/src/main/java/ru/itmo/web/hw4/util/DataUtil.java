package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class DataUtil {
    private static final String TEXT = "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "Baby, don't hurt me, don't hurt me\n" +
            "No more\n" +
            "What is love?\n" +
            "Yeah\n" +
            "No, I don't know why you're not fair\n" +
            "I give you my love, but you don't care\n" +
            "So what is right and what is wrong?\n" +
            "Gimme a sign\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "Whoa, whoa, oh\n" +
            "Whoa, whoa, oh\n" +
            "Oh, I don't know, what can I do?\n" +
            "What else can I say? It's up to you\n" +
            "I know we're one, just me and you\n" +
            "I can't go on\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "Whoa, whoa, oh\n" +
            "Whoa, whoa, oh\n" +
            "What is love?\n" +
            "What is love?\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "Don't hurt me\n" +
            "Don't hurt me\n" +
            "I want no other, no other lover\n" +
            "This is our life, our time\n" +
            "If we are together, I need you forever\n" +
            "Is it love?\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "Yeah, yeah\n" +
            "Whoa, whoa, oh\n" +
            "Whoa, whoa, oh\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "What is love?\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more (whoa, whoa)\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more (whoa, whoa)\n" +
            "Oh baby, don't hurt me\n" +
            "Don't hurt me\n" +
            "No more\n" +
            "What is love?";

    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov"),
            new User(6, "pashka", "Pavel Mavrin"),
            new User(9, "geranazavr555", "Georgiy Nazarov"),
            new User(11, "tourist", "Gennady Korotkevich")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(101, "No more", TEXT, 1),
            new Post(106, "Don't hurt me", TEXT, 6),
            new Post(109, "Oh baby, don't hurt me", TEXT, 9),
            new Post(111, "What is love?", TEXT, 11),
            new Post(201, "Let's sing!", "Haddaway - What Is Love", 1)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        List<Post> postsData = new ArrayList<>(POSTS);
        Collections.reverse(postsData);
        data.put("posts", postsData);
        data.put("users", USERS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id"))) {
                data.put("user", user);
            }
        }
    }
}
