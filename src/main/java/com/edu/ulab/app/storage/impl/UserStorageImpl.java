package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.UserStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Repository
public class UserStorageImpl implements UserStorage {

    private final static Map<Long, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        log.info("UserStorage got user for save: {}", user);
        users.put(user.getId(), user);
        log.info("UserStorage saved user id={}", user.getId());
        return user;
    }

    @Override
    public User getById(long id) {
        log.info("UserStorage got user id={} for get", id);
        checkUserExists(id);
        log.info("UserStorage: User id={} exists", id);
        return users.get(id);
    }

    @Override
    public User update(User user, Long id) {
        log.info("UserStorage got user id={} for update: {}", id, user);
        checkUserExists(id);
        log.info("UserStorage: User for update id={} exists", id);
        users.put(id, user);
        log.info("UserStorage: User id={} updated", id);
        return user;
    }

    @Override
    public void delete(long id) {
        log.info("UserStorage got user id={} for delete", id);
        checkUserExists(id);
        log.info("UserStorage: User id={} exists", id);
        users.remove(id);
        log.info("UserStorage: User id={} deleted", id);
    }

    private void checkUserExists(long userId) {
        if (!users.containsKey(userId)) {
            throw new NotFoundException(
                    String.format("User id=%d not found!", userId)
            );
        }
    }
}
