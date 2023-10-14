package ru.yandex.practicum.filmorate.service.implservice;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.Event;
import ru.yandex.practicum.filmorate.service.inmemoryservice.UserService;
import ru.yandex.practicum.filmorate.storage.dao.EventDao;


@Service
@RequiredArgsConstructor
public class EventServiceImpl{ // реализация сервиса событий

    private final UserService userService;
    private final EventDao eventDao;

    public List<Event> listUserEvents(Long id) { // получение всех событий пользователя
        userService.getUserById(id);
        return this.eventDao.listEvents(id);
    }


    public Event addEvent(Long userId, Long entityId, String eventType, String operationType) { // добавление события пользователя
        userService.getUserById(userId);
        return this.eventDao.addEvent(userId, entityId, eventType, operationType);
    }
}
