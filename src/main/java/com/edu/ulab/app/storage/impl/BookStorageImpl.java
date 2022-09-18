package com.edu.ulab.app.storage.impl;

import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.exception.NotFoundException;
import com.edu.ulab.app.storage.BookStorage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Repository
public class BookStorageImpl implements BookStorage {

    private final static Map<Long, Book> books = new HashMap<>();

    @Override
    public Book save(Book book) {
        log.info("BookStorage got book for save: {}", book);
        books.put(book.getId(), book);
        log.info("BookStorage saved book id={}", book.getId());
        return book;
    }

    @Override
    public Book getById(Long id) {
        log.info("BookStorage got book id={} for get", id);
        checkBookExists(id);
        log.info("BookStorage: Book id={} exists", id);
        return books.get(id);
    }

    @Override
    public List<Book> getAllByUserId(Long userId) {
        log.info("BookStorage got user id={} for list of books", userId);
        return books.values().stream()
                .filter(book -> Objects.equals(book.getUser().getId(), userId))
                .peek(book -> log.info("Found book id={} for user id={}", book.getId(), userId))
                .toList();
    }

    @Override
    public Book update(Book book, Long bookId) {
        log.info("BookStorage got book id={} for update: {}", bookId, book);
        checkBookExists(bookId);
        log.info("BookStorage: Book for update id={} exists", bookId);
        books.put(bookId, book);
        log.info("BookStorage: Book id={} updated", bookId);
        return book;
    }

    @Override
    public void delete(Long id) {
        log.info("BookStorage got book id={} for delete", id);
        checkBookExists(id);
        log.info("BookStorage: Book id={} exists", id);
        books.remove(id);
        log.info("BookStorage: Book id={} deleted", id);
    }

    private void checkBookExists(Long bookId) {
        if (!books.containsKey(bookId)) {
            throw new NotFoundException(
                    String.format("Book id=%d not found!", bookId)
            );
        }
    }
}
