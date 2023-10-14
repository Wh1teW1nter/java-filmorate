package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.util.HashMap;
import java.util.Map;

@Validated
@Data
@Builder
public class Event implements Comparable<Event>{
    Long eventId; // id события

    @NotBlank
    @NotNull
    @NotEmpty
    Long userId; // id пользователя

    @NotBlank
    @NotNull
    @NotEmpty
    Long entityId; // идентификатор сущности, с которой произошло событие

    @NotNull
    EventType eventType; // тип события

    @NotNull
    OperationType operation; // тип операции

    @PastOrPresent
    Long timestamp; // дата и время события

    public Map<String, Object> toMap() {
        Map<String, Object> values = new HashMap<>();

        values.put("event_id", eventId);
        values.put("user_id", userId);
        values.put("entity_id", entityId);
        values.put("event_type", eventType.getName());
        values.put("operatiom_type", operation.getName());
        values.put("time_stamp", timestamp);

        return values;
    }


    public enum EventType { // Типы событий
        FRIEND("FRIEND"),
        LIKE("LIKE"),
        REVIEW("REVIEW");

        private String name;

        EventType(String name) { // Получение строкового значениея из типа операции
            this.name = name;
        }

        @JsonCreator
        public static EventType fromName (String name) {
            if (name == null) {
                return null;
            }

            switch (name) {
                case "FRIEND": {
                    return FRIEND;
                }

                case "LIKE": {
                    return LIKE;
                }

                case "REVIEW": {
                    return REVIEW;
                }

                default: {
                    throw new UnsupportedOperationException(String.format("Неизвестный тип события: '%s'", name));
                }
            }
        }
        @JsonValue
        public String getName() {
            return name;
        }
    }

    public enum OperationType {
        ADD("ADD"),
        UPDATE("UPDATE"),
        REMOVE("REMOVE");
        private String name;

        OperationType(String name) { // Получение строкового значениея из типа операции
            this.name = name;
        }

        @JsonCreator
        public static OperationType fromName (String name) {
            if (name == null) {
                return null;
            }

            switch (name) {
                case "ADD": {
                    return ADD;
                }

                case "UPDATE": {
                    return UPDATE;
                }

                case "REMOVE": {
                    return REMOVE;
                }

                default: {
                    throw new UnsupportedOperationException(String.format("Неизвестный тип события: '%s'", name));
                }
            }
        }
        @JsonValue
        public String getName() {
            return name;
        }
    }

    @Override
    public int compareTo(Event otherEvent) {
        return this.getTimestamp().compareTo(otherEvent.getTimestamp());
    }
}
