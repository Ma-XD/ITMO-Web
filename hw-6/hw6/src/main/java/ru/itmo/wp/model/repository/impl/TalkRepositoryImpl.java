package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.domain.Talk;
import ru.itmo.wp.model.repository.BasicRepository;
import ru.itmo.wp.model.repository.TalkRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class TalkRepositoryImpl extends BasicRepository implements TalkRepository {

    @Override
    public Talk find(long id) {
        return (Talk) findByKey("id", id);
    }

    @Override
    public List<Talk> findByUserId(long userId) {
        return findAllByKeys(new HashMap<String, Object>() {{
            put("sourceUserId", userId);
            put("targetUserId", userId);
        }}, "OR")
                .stream()
                .map(it -> (Talk) it)
                .collect(Collectors.toList());
    }

    @Override
    public void save(Talk talk) {
        saveByKeys(talk, new HashMap<String, Object>() {{
            put("sourceUserId", talk.getSourceUserId());
            put("targetUserId", talk.getTargetUserId());
            put("text", talk.getText());
        }});
    }

    @Override
    protected Entity toEntityImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Talk talk = new Talk();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    talk.setId(resultSet.getLong(i));
                    break;
                case "sourceUserId":
                    talk.setSourceUserId(resultSet.getLong(i));
                    break;
                case "targetUserId":
                    talk.setTargetUserId(resultSet.getLong(i));
                    break;
                case "text":
                    talk.setText(resultSet.getString(i));
                    break;
                case "creationTime":
                    talk.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return talk;
    }
}
