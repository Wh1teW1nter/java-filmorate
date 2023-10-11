package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
public class Review {
    private Long id;
    @NotBlank
    private String content;
    @NotBlank
    private boolean isPositive;
    @NotBlank
    private Long userId;
    @NotBlank
    private Long filmId;
    @NotBlank
    private Long useful;

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();
        values.put("content", content);
        values.put("is_positive", isPositive);
        values.put("user_id", userId);
        values.put("film_id", filmId);
        values.put("useful", useful);
        return values;
    }
}
