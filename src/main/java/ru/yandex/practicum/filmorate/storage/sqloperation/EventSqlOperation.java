package ru.yandex.practicum.filmorate.storage.sqloperation;

public enum EventSqlOperation {

    GET_USER_EVENT(
            "SELECT *" +
                    "FROM events" +
                    "WHERE user_id = ?"),

    GET_EVENT_BY_ID(
            "SELECT event_id" +
                    "FROM events" +
                    "WHERE event_id = ?"),
    ;

    private final String title;

    EventSqlOperation(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }


}
