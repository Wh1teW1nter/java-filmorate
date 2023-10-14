package ru.yandex.practicum.filmorate.storage.dao;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Event;
import java.util.*;

@Repository
public interface EventDao {


    List<Event> listEvents(Long id); // Получение из хранилища объектов Event по id пользователя

    Event getEvent(Long id); // Получение из хранилища объекта Event по id

    Event addEvent(Long userId, Long entityId, String eventType, String operationType); //Добавление события для пользователя с заданными параметрами.

}
