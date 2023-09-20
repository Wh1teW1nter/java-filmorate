package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exceptions.ObjectNotFoundException;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class InMemoryFilmStorage {

    private final Map<Long, Film> films = new HashMap<Long, Film>();
    private Long iterator = 0L;

    public Film addNewFilm(Film film) throws ValidationException {
        if (validateFilm(film)) {
            film.setId(++iterator);
            films.put(iterator, film);
            return film;
        }
        throw new ValidationException("Validation failed");
    }

    public Film updateFilm(Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            throw new ObjectNotFoundException("Validation failed");
        }
        if (validateFilm(film)) {
            Long checkingUserId = film.getId();

            if (checkingUserId == null) {
                log.debug("Передан фильм без ID. Переданные данные: {}", film);
                throw new ObjectNotFoundException("Передан фильм без ID. Переданные данные: {}" + film);
            } else if (film.equals(films.get(checkingUserId))) {
                log.debug("Существует идентичный фильм. Переданные данные: {}", film);
            }

            log.debug("Обновлен пользователь. Переданные данные: {}", film);
            films.put(checkingUserId, film);
            return film;
        } else {
            throw new ValidationException("Validation failed");
        }
    }

    private boolean validateFilm(Film film) {
        String name = film.getName();
        String description = film.getDescription();
        LocalDate date = film.getReleaseDate();
        int duration = film.getDuration();
        LocalDate filmStart = LocalDate.of(1895, 12, 28);
        boolean isOK = true;
        if (name == null || name.equals("")) {
            log.debug("Название поля не может быть пустым. Переданное поле name: {}", name);
            isOK = false;
        }
        if (description.length() > 200) {
            log.debug("Максимальная длина описания - 200 символов. Длина переданного описания: {}", description.length());
            isOK = false;
        }
        if (date.isBefore(filmStart)) {
            log.debug("Поле birthday не может быть старше нынешней даты. Переданные данные: {}", date);
            isOK = false;
        }
        if (duration <= 0) {
            log.debug("Продолжительность должна быть положительной. Переданная продолжительность: {}. ID фильма :{} ", duration, film.getId());
            isOK = false;
        }
        return isOK;
    }

    public List<Film> getAllFilms() {
        log.debug("Все пользователи на момент вызова метода: GET /films {}", films);
        return List.copyOf(films.values());
    }

    public Film getFilmById(Integer id) throws ValidationException {
        if (id <= 0) {
            log.debug("Id не может быть меньше или равен нулю. Значение id: " + id);
            throw new ObjectNotFoundException("Id не может быть меньше или равен нулю. Значение id: " + id);
        } else {
            if (films.containsKey(id.longValue())) {
                return films.get(id.longValue());
            }
            log.debug("Указанный ID отсутствует. Значение ID: " + id);
            throw new ObjectNotFoundException("Пользователь с указанным ID отсутствует. Значение ID: " + id);
        }
    }

    public List<Film> getFilmsByRate(Integer limit) {
        return films.values().stream()
                .sorted(this::compare)
                .limit(limit)
                .collect(Collectors.toList());
    }

    private int compare(Film f1, Film f0) {
        return Integer.compare(f0.getRating(), f1.getRating());
    }
}
