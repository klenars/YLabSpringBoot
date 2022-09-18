package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.storage.BookStorage;
import com.edu.ulab.app.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private static long idForBooks = 0;

    private final BookStorage bookStorage;
    private final UserStorage userStorage;
    private final BookMapper bookMapper;

    @Override
    public BookDto createBook(BookDto bookDto) {
        log.info("bookService got bookDto for create: {}", bookDto);
        bookDto.setId(getId());
        log.info("bookService set id={} for bookDto: {}", bookDto.getId(), bookDto);
        Book book = bookMapper.bookDtoToBook(bookDto);
        log.info("bookService mapped bookDto to Book: {}", book);
        book.setUser(userStorage.getById(bookDto.getUserId()));
        log.info("bookService set User with id={} for book: {}", book.getUser().getId(), book);
        return bookMapper.bookToBookDto(bookStorage.save(book));
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long id) {
        log.info("bookService got id={} and bookDto for create: {}", id, bookDto);
        Book bookForUpdate = bookMapper.bookDtoToBook(bookDto);
        log.info("bookService mapped bookDto to Book: {}", bookForUpdate);
        return bookMapper.bookToBookDto(bookStorage.update(bookForUpdate, id));
    }

    @Override
    public BookDto getBookById(Long id) {
        log.info("bookService got id={} for Get", id);
        return bookMapper.bookToBookDto(bookStorage.getById(id));
    }

    @Override
    public void deleteBookById(Long id) {
        log.info("bookService got book id={} for delete", id);
        bookStorage.delete(id);
    }

    @Override
    public List<BookDto> getAllByUserId(Long userId) {
        log.info("BookService got user id={} for list of books", userId);
        return bookStorage.getAllByUserId(userId).stream()
                .map(bookMapper::bookToBookDto)
                .peek(bookDto -> log.info("mapped book to bookDto: {}", bookDto))
                .toList();
    }

    private long getId() {
        return ++idForBooks;
    }
}
