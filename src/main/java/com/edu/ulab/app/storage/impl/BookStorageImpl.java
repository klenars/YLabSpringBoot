package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.BookStorage;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class BookStorageImpl implements BookStorage {

    private final static Map<Long, Book> books = new HashMap<>();

    @Override
    public Book save(Book book) {
        books.put(book.getId(), book);
        return book;
    }

    @Override
    public Book getById(Long id) {
        checkBookExists(id);
        return books.get(id);
    }

    @Override
    public List<Book> getAllByUserId(Long userId) {
        return books.values().stream()
                .filter(book -> Objects.equals(book.getUser().getId(), userId))
                .toList();
    }

    @Override
    public Book update(Book book, Long bookId) {
        checkBookExists(bookId);
        books.put(bookId, book);
        return book;
    }

    @Override
    public void delete(Long id) {
        checkBookExists(id);
        books.remove(id);
    }

    private void checkBookExists(Long bookId) {
        if (!books.containsKey(bookId)) {
            throw new NotFoundException(
                    String.format("Book id=%d not found!", bookId)
            );
        }
    }
}
