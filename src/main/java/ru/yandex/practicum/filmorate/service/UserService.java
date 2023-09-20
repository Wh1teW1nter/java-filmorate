package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exceptions.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.InMemoryUserStorage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserService {

    InMemoryUserStorage inMemoryUserStorage;

    @Autowired
    public UserService(InMemoryUserStorage inMemoryUserStorage) {
        this.inMemoryUserStorage = inMemoryUserStorage;
    }

    public User createUser(User user) throws ValidationException {
        return inMemoryUserStorage.createUser(user);
    }

    public User updateUser(User user) throws ValidationException {
        return inMemoryUserStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return inMemoryUserStorage.getAllUsers();
    }

    public User addFriend(Integer mainId, Integer addingId) throws ValidationException {
        User mainUser = inMemoryUserStorage.getUserById(mainId);
        User addingUser = inMemoryUserStorage.getUserById(addingId);


        mainUser.getFriends().add(addingId.longValue());
        addingUser.getFriends().add(mainId.longValue());

        updateUser(addingUser);
        return updateUser(mainUser);

    }

    public User getUserById(int id) throws ValidationException {
        return inMemoryUserStorage.getUserById(id);
    }

    public User removeFriend(int mainId, int removingId) throws ValidationException {
        User mainUser = inMemoryUserStorage.getUserById(mainId);
        User removingUser = inMemoryUserStorage.getUserById(removingId);

        mainUser.getFriends().remove(removingUser);
        removingUser.getFriends().remove(mainUser);

        updateUser(removingUser);
        return updateUser(mainUser);
    }

    public List<Long> getFriends(int userId) throws ValidationException {
        User user = inMemoryUserStorage.getUserById(userId);
        Set<Long> friends = user.getFriends();

        return List.copyOf(friends);
    }

    public List<User> getFriendsByUserId(int userId) throws ValidationException {
        User user = inMemoryUserStorage.getUserById(userId);
        Set<Long> friends = user.getFriends();
        List<User> result = new ArrayList<User>();
        for (Long id : friends) {
            result.add(inMemoryUserStorage.getUserById(id.intValue()));
        }
        return List.copyOf(result);
    }

    public List<User> getMatualFriends(int mainId, int otherId) throws ValidationException {
        List<User> mainSet = getFriendsByUserId(mainId);
        List<User> otherFriends = getFriendsByUserId(otherId);

        if (mainSet == null || otherFriends == null) {
            return Collections.emptyList();
        }


        List<User> intersection = new ArrayList<>(mainSet);
        intersection.retainAll(otherFriends);
        return List.copyOf(intersection);
    }
}
