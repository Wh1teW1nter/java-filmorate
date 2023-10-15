package ru.yandex.practicum.filmorate.storage.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Director;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.dao.DirectorDao;
import static ru.yandex.practicum.filmorate.storage.sqloperation.DirectorSqlOperation.*;

import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;

@Slf4j
@Component("directorDaoImpl")
@AllArgsConstructor
public class DirectorDaoImpl implements DirectorDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Director createDirector(Director director) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("director").usingGeneratedKeyColumns("id");
        director.setId(simpleJdbcInsert.executeAndReturnKey(Map.of("name", director.getName())).longValue());
        log.info("В базу добавлен новый режиссёр с id - {}", director.getId());
        return director;
    }

    @Override
    public Director updateDirector(Director director) {
        jdbcTemplate.update(UPDATE_DIRECTOR.getTitle(),
                director.getName(),
                director.getId());
        log.info("Режиссёр обновлен. id - {}", director.getId());
        return director;
    }

    @Override
    public void deleteDirector(Long directorId) {
        jdbcTemplate.update(DELETE_DIRECTOR.getTitle(), directorId);
        log.info("Режиссёр с id {} удален", directorId);
    }

    @Override
    public List<Director> getDirectorsList() {
        return jdbcTemplate.query(GET_ALL_DIRECTORS.getTitle(), this::mapRowToDirector);
    }

    @Override
    public Director getDirectorById(Long directorId) {
        return jdbcTemplate.queryForObject(GET_DIRECTOR_BY_ID.getTitle(),
                this::mapRowToDirector, directorId);
    }

    @Override
    public List<Director> getDirectorByFilmId(Long filmId) {
        if(jdbcTemplate.queryForList(GET_DIRECTOR_BY_FILM_ID.getTitle(), filmId).isEmpty()) {
            return List.of();
        }
        return jdbcTemplate.query(GET_DIRECTOR_BY_FILM_ID.getTitle(), this::mapRowToDirector, filmId);
    }

    @Override
    public boolean checkDirectorExistInDb(Long id) {
        return !jdbcTemplate.query(GET_DIRECTOR_BY_ID.getTitle(), this::mapRowToDirector, id).isEmpty();
    }

    @Override
    public void addDirectorToFilm(Film film) {
        if (film.getDirectors() != null) {
            if (!film.getDirectors().isEmpty()) {
                for (Director director : film.getDirectors()) {
                    jdbcTemplate.update(ADD_DIRECTOR_TO_FILM.getTitle(), film.getId(), director.getId());
                }
            }
        }
    }

    @Override
    public void deleteDirectorsFromFilm(Long filmId) {
        jdbcTemplate.update(DELETE_DIRECTOR_FROM_FILM.getTitle(), filmId);
    }

    public Director mapRowToDirector(ResultSet resultSet, int row) throws SQLException {
        return Director.builder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("Name"))
                .build();
    }
}
