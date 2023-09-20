package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.List;


@RestController
@Slf4j
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) throws ValidationException {
        /*System.out.println("Start POSTing user...");
        if (validateUser(user)) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            log.debug("Создан пользователь. Переданные данные: {}", user);
            System.out.println(user);
            return addNewUser(user);
        }
        throw new ValidationException("Validation failed");*/
        return userService.createUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User user) throws ValidationException {
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        /*log.debug("Все пользователи на момент вызова метода: GET /users {}", users);
        return List.copyOf(users.values());*/
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) throws ValidationException {
        return userService.getUserById(id);
    }


    private User addNewUser(User user) throws ValidationException {
        /*user.setId(++iterator);
        users.put(iterator, user);
        return user;*/
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}/friends/{friendId}")
    public User addFriend(@PathVariable Integer id, @PathVariable Integer friendId) throws ValidationException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friends/{friendId}")
    public User deleteFriends(@PathVariable Integer id, @PathVariable Integer friendId) throws ValidationException {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friends")
    public List<User> getFriends(@PathVariable Integer id) throws ValidationException {
        return userService.getFriendsByUserId(id);
    }

    @GetMapping("/users/{id}/friends/common/{otherId}")
    public List<User> getMatualFriends(@PathVariable Integer id, @PathVariable Integer otherId) throws ValidationException {
        return userService.getMatualFriends(id, otherId);
    }

}
