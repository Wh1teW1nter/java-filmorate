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

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/users")
    public User createUser(@RequestBody User user) throws ValidationException {

        return userService.createUser(user);
    }

    @PutMapping(value = "/users")
    public User updateUser(@RequestBody User user) throws ValidationException {
        return userService.updateUser(user);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable Integer id) throws ValidationException {
        return userService.getUserById(id);
    }


    private User addNewUser(User user) throws ValidationException {
        return userService.createUser(user);
    }

    @PutMapping("/users/{id}/friendIds/{friendId}")
    public User addFriend(@PathVariable Integer id, @PathVariable Integer friendId) throws ValidationException {
        return userService.addFriend(id, friendId);
    }

    @DeleteMapping("/users/{id}/friendIds/{friendId}")
    public User deletefriendIds(@PathVariable Integer id, @PathVariable Integer friendId) throws ValidationException {
        return userService.removeFriend(id, friendId);
    }

    @GetMapping("/users/{id}/friendIds")
    public List<User> getfriendIds(@PathVariable Integer id) throws ValidationException {
        return userService.getfriendIdsByUserId(id);
    }

    @GetMapping("/users/{id}/friendIds/common/{otherId}")
    public List<User> getMatualfriendIds(@PathVariable Integer id, @PathVariable Integer otherId) throws ValidationException {
        return userService.getMatualfriendIds(id, otherId);
    }

}
