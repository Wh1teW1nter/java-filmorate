package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.EntityNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage {

    private final Map<Long, User> users = new HashMap<Long, User>();
    private Long iterator = 0L;

    public User createUser(User user) throws ValidationException {
        System.out.println("Start POSTing user...");
        if (validateUser(user)) {
            if (user.getName() == null || user.getName().equals("")) {
                user.setName(user.getLogin());
            }
            log.debug("Создан пользователь. Переданные данные: {}", user);
            System.out.println(user);
            return addNewUser(user);
        }
        throw new ValidationException("Validation failed");
    }

    public User updateUser(User user) throws ValidationException {
        if (!users.containsKey(user.getId())) {
            throw new EntityNotFoundException("Object not found: " + user.getId());
        }
        if (validateUser(user)) {
            Long checkingUserId = user.getId();
            if (checkingUserId == null) {
                log.debug("Передан пользователь без ID. Переданные данные: {}", user);
            } else if (user.equals(users.get(checkingUserId))) {
                log.debug("Существует идентичный пользователь. Переданные данные: {}", user);
            }
            log.debug("Обновлен пользователь. Переданные данные: {}", user);
            users.put(checkingUserId, user);
            return user;
        }
        throw new ValidationException("Validation failed");
    }

    public List<User> getAllUsers() {
        log.debug("Все пользователи на момент вызова метода: GET /users {}", users);
        return List.copyOf(users.values());
    }

    private User addNewUser(User user) {
        user.setId(++iterator);
        users.put(iterator, user);
        return user;
    }

    public User getUserById(Integer id) throws ValidationException {
        if (id <= 0) {
            log.debug("Id не может быть меньше или равен нулю. Значение id: " + id);
            throw new EntityNotFoundException("Id не может быть меньше или равен нулю. Значение id: " + id);
        } else {
            if (users.containsKey(id.longValue())) {
                return users.get(id.longValue());
            }
            log.debug("Указанный ID отсутствует. Значение ID: " + id);
            throw new EntityNotFoundException("Пользователь с указанным ID отсутствует. Значение ID: " + id);
        }
    }


    private boolean validateUser(User user) {
        System.out.println("Start validation");
        String email = user.getEmail();
        String login = user.getLogin();
        LocalDate date = user.getBirthday();
        boolean isOK = true;
        if (email == null || email.indexOf("@") == -1) {
            log.debug("Некорректное значение поля email. Переданные данные: {}", email);
            isOK = false;
        }
        if (login == null || login.indexOf(" ") != -1) {
            log.debug("Некорректное значение поля login. Переданные данные: {}", login);
            isOK = false;
        }
        if (date.isAfter(LocalDate.now())) {
            log.debug("Поле birthday не может быть старше нынешней даты. Переданные данные: {}", date);
            isOK = false;
        }
        return isOK;
    }

}
