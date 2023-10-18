package ru.yandex.practicum.filmorate.storage.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


@Slf4j
@Repository
@RequiredArgsConstructor
public class EventDaoImpl implements EventDao {

    private final EventMapper eventMapper;
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Event> getUserFeed(Long id) {
        // запрашиваем список всех событий пользователя
        String sqlEvents = "SELECT * FROM events where user_id = ?";
        List<Event> events = jdbcTemplate.query(sqlEvents, eventMapper, id);
        logResultList(events);
        return events;
    }

    @Override
    public Event addEvent(Long userId, Long entityId, String eventType, String operationType) {
        // вставляем данные события в базу данных и получаем сгенерированный id

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

        // возвращаем данные события с присвоенным id
        Event newEvent = getEvent(eventId);

        log.info("Создано событие: {} ", newEvent);

        return newEvent;
    }

    @Override
    public Event getEvent(Long id) {

        checkEventId(id);
        // выполняем запрос к базе данных
        String sqlEvent = "SELECT * FROM events WHERE event_id = ?";
        Event event = jdbcTemplate.queryForObject(sqlEvent, eventMapper, id);
        log.info("Найдено событие: {} ", id);
        return event;
    }

    public void checkEventId(Long eventId) {

        SqlRowSet sqlId = jdbcTemplate
                .queryForRowSet("SELECT event_id FROM events WHERE event_id = ?", eventId);

        if (!sqlId.next()) {
            log.info("Событие с идентификатором {} не найдено.", eventId);
            throw new ObjectNotFoundException(String.format("Событие с id: %d не найдено", eventId));
        }
    }

    private void logResultList(List<Event> events) {

        String result = events.stream()
                .map(Event::toString)
                .collect(Collectors.joining(", "));

        log.info("Список событий по запросу: {}", result);

    }
}
