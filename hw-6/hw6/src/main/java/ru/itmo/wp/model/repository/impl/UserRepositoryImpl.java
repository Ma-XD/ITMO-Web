package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.repository.BasicRepository;
import ru.itmo.wp.model.repository.UserRepository;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("SqlNoDataSourceInspection")
public class UserRepositoryImpl extends BasicRepository implements UserRepository {

    @Override
    public User find(long id) {
        return (User) findByKey("id", id);
    }

    @Override
    public User findByLogin(String login) {
        return (User) findByKey("login", login);
    }

    @Override
    public User findByEmail(String email) {
        return (User) findByKey("email", email);
    }

    @Override
    public User findByLoginAndPasswordSha(String login, String passwordSha) {
        return (User) findByKeys(new HashMap<String, Object>(){{
            put("login", login);
            put("passwordSha", passwordSha);
        }});
    }

    @Override
    public User findByEmailAndPasswordSha(String email, String passwordSha) {
        return (User) findByKeys(new HashMap<String, Object>(){{
            put("email", email);
            put("passwordSha", passwordSha);
        }});
    }

    @Override
    public List<User> findAll() {
        return findAllEntities().stream().map(it -> (User) it).collect(Collectors.toList());
    }

    @Override
    public void save(User user, String passwordSha) {
        saveByKeys(user, new HashMap<String, Object>(){{
            put("login", user.getLogin());
            put("email", user.getEmail());
            put("passwordSha", passwordSha);
        }});
    }

    @Override
    protected Entity toEntityImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        User user = new User();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    user.setId(resultSet.getLong(i));
                    break;
                case "login":
                    user.setLogin(resultSet.getString(i));
                    break;
                case "email":
                    user.setEmail(resultSet.getString(i));
                    break;
                case "creationTime":
                    user.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return user;
    }
}
