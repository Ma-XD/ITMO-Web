package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;

public class StaticServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String[] multipleURI = request.getRequestURI().split("\\+");

        for (String uri : multipleURI) {
            File file = getFile(uri);
            if (file.isFile()) {
                if (response.getContentType() == null) {
                    response.setContentType(getServletContext().getMimeType(file.getName()));
                }
                try (OutputStream outputStream = response.getOutputStream()) {
                    Files.copy(file.toPath(), outputStream);
                }
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
    }

    private File getFile(String uri) {
        File file = new File("./src/main/webapp/static", uri);
        if (!file.isFile()) {
            file = new File(getServletContext().getRealPath("/static"), uri);
        }
        return file;
    }
}
