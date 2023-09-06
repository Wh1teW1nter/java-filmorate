package ru.yandex.practicum.filmorate.controller;

import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    Map<Long, User> users = new HashMap<Long, User>();
    Long iterator = 0L;

    @PostMapping(value = "/user")
    public User createUser(@RequestBody User user) {

        return addNewUser(user);

    }

    @PutMapping(value = "/user")
    public void updateUser(@RequestBody User user) {
        Long checkingUserId = user.getId();
        if (checkingUserId == null) {
            System.out.println("User without id was rejected");
        } else if(user.equals(users.get(checkingUserId))) {
            System.out.println("Существует идентичный пользователь");
        } else {
            users.put(checkingUserId, user);
        }
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return users.values().stream().toList();
    }


    public User addNewUser(User user) {
        user.setId(iterator++);
        users.put(iterator, user);
        return user;
    }

}
