package ru.yandex.practicum.filmorate.storage.dao;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmDao {

    List<Film> findAll();

    Film save(Film film);

    Optional<Film> update(Film film);

    void delete(Long id);

    Optional<Film> getFilmById(Long id);

    List<Film> getSortedDirectorFilms(Long directorId, String sortBy);

    void addLike(long filmId, long userId);

    void deleteLike(long filmId, long userId);

    List<Film> getSortedFilmsByLikes(Long count);

    List<Long> getLikesByFilmId(long filmId);
  
    List<Film> searchFilmsByDirector(String director);

    List<Film> searchFilmsByTitle(String title);

    List<Film> searchFilmsByDirectorAndTitle(String query);
  
    List<Film> getCommonFilms(long userId, long friendId);

    List<Film> getPopularFilms(long genreId, int year, int count);
  
}
