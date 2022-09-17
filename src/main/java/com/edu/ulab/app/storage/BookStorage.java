package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;

public interface BookStorage {
    Book getById(long id);
}
