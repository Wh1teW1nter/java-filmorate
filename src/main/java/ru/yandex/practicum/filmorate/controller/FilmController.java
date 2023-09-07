package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class FilmController {
    private final Map<Long, Film> films = new HashMap<Long, Film>();
    private Long iterator = 0L;

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException {
        if (validateFilm(film)) {
            log.debug("Добавлен фильм. Переданные данные: {}", film);
            return addNewFilm(film);
        } else {
            throw new ValidationException("Validation failed");
        }
    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {
        if (!films.containsKey(film.getId())) {
            throw new ValidationException("Validation failed");
        }
        if (validateFilm(film)) {
            Long checkingUserId = film.getId();

            if (checkingUserId == null) {
                log.debug("Передан пользователь без ID. Переданные данные: {}", film);
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

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        log.debug("Все пользователи на момент вызова метода: GET /films {}", films);
        return List.copyOf(films.values());
    }


    private Film addNewFilm(Film film) {
        film.setId(++iterator);
        films.put(iterator, film);
        return film;
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
}
