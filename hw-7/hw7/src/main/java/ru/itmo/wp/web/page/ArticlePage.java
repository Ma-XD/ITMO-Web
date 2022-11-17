package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.Article;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void action(HttpServletRequest request, Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            setMessage("You should enter to use Article");
            throw new RedirectException("/index");
        }
    }

    private void add(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        User user = getUser();
        String title = request.getParameter("title");
        String text = request.getParameter("text");
        articleService.validateArticle(user, title, text);

        Article article = new Article();
        article.setUserId(user.getId());
        article.setTitle(title);
        article.setText(text);
        articleService.add(article);

        setMessage("Article added");
    }
}


