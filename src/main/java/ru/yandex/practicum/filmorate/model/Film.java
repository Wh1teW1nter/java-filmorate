package ru.yandex.practicum.filmorate.model;

import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
public class Film {
    long id;
    String name;
    String description;
    LocalDate releaseDate;
    int duration;
}
