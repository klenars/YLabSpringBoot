package com.edu.ulab.app.storage;

import com.edu.ulab.app.entity.Book;

import java.util.List;

public interface BookStorage {

    Book save(Book book);

    Book getById(Long id);

    List<Book> getAllByUserId(Long userId);

    Book update(Book book, Long id);

    void delete(Long id);
}
