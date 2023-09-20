package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.constraints.Min;
import java.util.List;

@RestController
@Slf4j
public class FilmController {

    FilmService filmService;

    @Autowired
    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @PostMapping(value = "/films")
    public Film createFilm(@RequestBody Film film) throws ValidationException {

        return filmService.createFilm(film);

    }

    @PutMapping(value = "/films")
    public Film updateFilm(@RequestBody Film film) throws ValidationException {

        return filmService.updateFilm(film);
    }

    @GetMapping("/films")
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PutMapping("/films/{id}/like/{userId}")
    public Film addLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) throws ValidationException {
        return filmService.addLikeFilm(userId, id);
    }

    @DeleteMapping("/films/{id}/like/{userId}")
    public Film deleteLikeFilm(@PathVariable Integer id, @PathVariable Integer userId) throws ValidationException {
        return filmService.removeLikeFilm(userId, id);
    }

    @GetMapping("/films/popular")
    public List<Film> getRatedFilms(@RequestParam(value = "count", required = false, defaultValue = "10")
                                    @Min(1) Integer count) {

        return filmService.getFilmsByRate(count);

    }

    @GetMapping("/films/{id}")
    public Film getFilmById(@PathVariable Integer id) throws ValidationException {
        return filmService.getFilmById(id);
    }
}
