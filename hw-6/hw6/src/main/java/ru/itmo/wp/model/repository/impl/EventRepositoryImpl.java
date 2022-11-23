package ru.itmo.wp.model.repository.impl;

import ru.itmo.wp.model.domain.Entity;
import ru.itmo.wp.model.domain.Event;
import ru.itmo.wp.model.repository.BasicRepository;
import ru.itmo.wp.model.repository.EventRepository;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;

public class EventRepositoryImpl extends BasicRepository implements EventRepository {

    @Override
    public Event find(long id) {
        return (Event) findByKey("id", id);
    }

    @Override
    public void save(Event event) {
        saveByKeys(event, new HashMap<String, Object>(){{
            put("userId", event.getUserId());
            put("type", event.getType());
        }});
    }

    @Override
    protected Entity toEntityImpl(ResultSetMetaData metaData, ResultSet resultSet) throws SQLException {
        if (!resultSet.next()) {
            return null;
        }

        Event event = new Event();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            switch (metaData.getColumnName(i)) {
                case "id":
                    event.setId(resultSet.getLong(i));
                    break;
                case "userId":
                    event.setUserId(resultSet.getLong(i));
                    break;
                case "type":
                    event.setType(Event.EventType.valueOf(resultSet.getString(i)));
                    break;
                case "creationTime":
                    event.setCreationTime(resultSet.getTimestamp(i));
                    break;
                default:
                    // No operations.
            }
        }

        return event;
    }
}
