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


        mainUser.getFriendIds().add(addingId.longValue());
        addingUser.getFriendIds().add(mainId.longValue());

        updateUser(addingUser);
        return updateUser(mainUser);

    }

    public User getUserById(int id) throws ValidationException {
        return inMemoryUserStorage.getUserById(id);
    }

    public User removeFriend(int mainId, int removingId) throws ValidationException {
        User mainUser = inMemoryUserStorage.getUserById(mainId);
        User removingUser = inMemoryUserStorage.getUserById(removingId);

        mainUser.getFriendIds().remove(removingUser);
        removingUser.getFriendIds().remove(mainUser);

        updateUser(removingUser);
        return updateUser(mainUser);
    }

    public List<Long> getFriendIds(int userId) throws ValidationException {
        User user = inMemoryUserStorage.getUserById(userId);
        Set<Long> friendIds = user.getFriendIds();

        return List.copyOf(friendIds);
    }

    public List<User> getFriendIdsByUserId(int userId) throws ValidationException {
        User user = inMemoryUserStorage.getUserById(userId);
        Set<Long> friendIds = user.getFriendIds();
        List<User> result = new ArrayList<User>();
        for (Long id : friendIds) {
            result.add(inMemoryUserStorage.getUserById(id.intValue()));
        }
        return List.copyOf(result);
    }

    public List<User> getMatualfriendIds(int mainId, int otherId) throws ValidationException {
        List<User> mainSet = getFriendIdsByUserId(mainId);
        List<User> otherFriendIds = getFriendIdsByUserId(otherId);

        if (mainSet == null || otherFriendIds == null) {
            return Collections.emptyList();
        }


        List<User> intersection = new ArrayList<>(mainSet);
        intersection.retainAll(otherFriendIds);
        return List.copyOf(intersection);
    }
}
