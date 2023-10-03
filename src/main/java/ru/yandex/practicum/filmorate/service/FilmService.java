package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.List;


@Service
public class FilmService {

    private InMemoryFilmStorage inMemoryFilmStorage;
    private InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryFilmStorage = inMemoryFilmStorage;
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public Film getFilmById(Integer id) throws ValidationException {
        return inMemoryFilmStorage.getFilmById(id);
    }

    public Film createFilm(Film film) throws ValidationException {
        return inMemoryFilmStorage.addNewFilm(film);
    }

    public Film updateFilm(Film film) throws ValidationException {
        return inMemoryFilmStorage.updateFilm(film);
    }

    public List<Film> getAllFilms() {

        return inMemoryFilmStorage.getAllFilms();

    }

    public Film addLikeFilm(int userId, int filmId) throws ValidationException {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUserById(userId);
        film.getLikes().add(user.getId());
        return film;
    }

    public Film removeLikeFilm(int userId, int filmId) throws ValidationException {
        Film film = inMemoryFilmStorage.getFilmById(filmId);
        User user = inMemoryUserStorage.getUserById(userId);
        film.getLikes().remove(user);
        return film;
    }

    public List<Film> getFilmsByRate(Integer limit) {
        return inMemoryFilmStorage.getFilmsByRate(limit);
    }

}
