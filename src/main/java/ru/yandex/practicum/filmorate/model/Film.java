package ru.yandex.practicum.filmorate.model;

import java.time.Duration;
import java.time.LocalDateTime;

public class Film {
    long id;
    String name;
    String description;
    LocalDateTime releaseDate;
    Duration duration;
}
