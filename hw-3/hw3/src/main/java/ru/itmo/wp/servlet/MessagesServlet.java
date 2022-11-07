package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import ru.itmo.wp.models.MessagesData;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class MessagesServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(StandardCharsets.UTF_8.name());

        String[] uriParts = request.getRequestURI().split("/");

        switch (uriParts[uriParts.length - 1]) {
            case "auth":
                authorizeUser(request, response);
                return;
            case "findAll":
                findAllUsersWithMessages(response);
                return;
            case "add":
                addMessage(request, response);
        }
    }

    private void authorizeUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String dataRaw = request.getReader().readLine();

        if (dataRaw == null) {
            writeJson(response, "");
            return;
        }

        String user = parsePostData(dataRaw, MessagesData.USER_KEY);

        if (user == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        HttpSession session = request.getSession();
        session.setAttribute(MessagesData.USER_KEY, user);
        MessagesData.addUser(user);
        writeJson(response, user);
    }

    private void findAllUsersWithMessages(HttpServletResponse response) throws IOException {
        writeJson(response, MessagesData.toJsonObject());
    }

    private void addMessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        Object userValue = session.getAttribute(MessagesData.USER_KEY);

        if (userValue == null || MessagesData.isNotAuthorizedUser(userValue.toString())) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        String dataRaw = request.getReader().readLine();
        String user = userValue.toString();
        String text = parsePostData(dataRaw, MessagesData.TEXT_KEY);

        if (text == null) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        MessagesData.addText(user, text);
        writeJson(response, text);
    }

    private String parsePostData(String dataRaw, String keyPattern) {
        String[] data = dataRaw.replace('+', ' ').split("=");

        if (data.length == 2 && data[0].equals(keyPattern) && !data[1].trim().isEmpty()) {
            return data[1];
        }
        return null;
    }

    private void writeJson(HttpServletResponse response, Object source) throws IOException {
        response.setContentType("application/json");
        String json = new Gson().toJson(source);
        response.getWriter().print(json);
        response.getWriter().flush();
    }
}
