package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Event;

import java.util.List;

@Repository
public interface EventDao {


    List<Event> listEvents(Long id); // Получить ленту событий по id пользователя

    Event getEvent(Long id); // Получение события по id

    Event addEvent(Long userId, Long entityId, String eventType, String operationType); // Добавить событие

}
