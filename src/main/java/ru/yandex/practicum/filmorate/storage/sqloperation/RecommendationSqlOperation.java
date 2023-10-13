package ru.yandex.practicum.filmorate.storage.sqloperation;

public enum RecommendationSqlOperation {

    GET_FILMS_RECOMMENDATION("SELECT f.film_id, f.film_name, f.description, f.duration, " +
            "f.release_date, r.mpa_id, r.mpa_name " +
            "FROM films AS f " +
            "JOIN rating AS r ON f.mpa_id = r.mpa_id" +
            "WHERE f.film_id in " +
            "(SELECT DISTINCT fl.film_id " +
            "FROM film_like AS fl WHERE fl.user_id in (SELECT fl1.user_id FROM film_like AS fl1 WHERE fl1.film_id in " +
            "(SELECT fl2.film_id from film_like AS fl2 where fl2.user_id = ?) and fl1.user_id != ? GROUP BY fl1.user_id limit 1) " +
            "and f.film_id not in " +
            "(SELECT fl4.film_id FROM film_like AS fl4 WHERE fl4.user_id = ?)" );


    private final String title;

    RecommendationSqlOperation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

}
