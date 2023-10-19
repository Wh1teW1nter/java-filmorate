package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface DirectorDao {

    Director createDirector(Director director);

    Director updateDirector(Director director);

    void deleteDirector(Long directorId);

    List<Director> getDirectorsList();

    Director getDirectorById(Long directorId);

    List<Director> getDirectorByFilmId(Long filmId);

    boolean checkDirectorExistInDb(Long id);

    void addDirectorToFilm(Film film);

    void deleteDirectorsFromFilm(Long directorId);
}
