package ru.yandex.practicum.filmorate.storage.impl;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exceptions.event.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.mapper.EventMapper;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.storage.dao.EventDao;

import static ru.yandex.practicum.filmorate.storage.sqloperation.EventSqlOperation.*;

@Slf4j
@Repository
@RequiredArgsConstructor
public class EventDaoImpl implements EventDao {

    private final EventMapper eventMapper;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Event> getUserFeed(Long id) {
        List<Event> events = jdbcTemplate.query(GET_USER_EVENT.getTitle(), eventMapper, id);
        return events;
    }

    @Override
    public Event addEvent(Long userId, Long entityId, String eventType, String operationType) {
        SimpleJdbcInsert eventInsertion = new SimpleJdbcInsert(jdbcTemplate).withTableName("events")
                .usingGeneratedKeyColumns("event_id");

        Event event = Event.builder()
                .userId(userId)
                .entityId(entityId)
                .eventType(Event.EventType.fromName(eventType))
                .operation(Event.OperationType.fromName(operationType))
                .timestamp(System.currentTimeMillis())
                .build();

        Long eventId = eventInsertion.executeAndReturnKey(event.toMap()).longValue();
        Event newEvent = getEvent(eventId);

        return newEvent;
    }

    @Override
    public Event getEvent(Long id) {
        checkEventId(id);
        Event event = jdbcTemplate.queryForObject(GET_USER_EVENT.getTitle(), eventMapper, id);
        return event;
    }

    public void checkEventId(Long eventId) {

        SqlRowSet sqlId = jdbcTemplate
                .queryForRowSet(GET_EVENT_BY_ID.getTitle(), eventId);

        if (!sqlId.next()) {
            throw new ObjectNotFoundException(String.format("Событие с id: %d не найдено", eventId));
        }
    }
}