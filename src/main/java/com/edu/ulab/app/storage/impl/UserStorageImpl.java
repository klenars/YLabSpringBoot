package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.UserStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserStorageImpl implements UserStorage {

    private final static Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public User getById(long id) {
        checkUserExists(id);
        return users.get(id);
    }

    @Override
    public User update(User user, Long id) {
        checkUserExists(id);
        users.put(id, user);
        return user;
    }

    @Override
    public void delete(long id) {
        checkUserExists(id);
        users.remove(id);
    }

    private void checkUserExists(long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException(
                    String.format("User id=%d not found!", userId)
            );
        }
    }
}
