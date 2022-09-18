package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.User;

public interface UserStorage {

    User save(User user);

    User getById(long id);

    User update(User user, Long id);

    void delete(long id);
}
