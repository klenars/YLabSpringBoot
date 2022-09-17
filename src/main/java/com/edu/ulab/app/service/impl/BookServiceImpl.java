package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.Book;
import com.edu.ulab.app.entity.User;
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
        bookDto.setId(getId());
        Book book = bookMapper.bookDtoToBook(bookDto);
        book.setUser(userStorage.getById(bookDto.getUserId()));
        return bookMapper.bookToBookDto(bookStorage.save(book));
    }

    @Override
    public BookDto updateBook(BookDto bookDto, Long id) {
        Book bookForUpdate = bookMapper.bookDtoToBook(bookDto);
        return bookMapper.bookToBookDto(bookStorage.update(bookForUpdate, id));
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.bookToBookDto(bookStorage.getById(id));
    }

    @Override
    public void deleteBookById(Long id) {
        bookStorage.delete(id);
    }

    @Override
    public void setUser(Long bookId, User user) {
        bookStorage.getById(bookId).setUser(user);
    }

    @Override
    public List<BookDto> getAllByUserId(Long userId) {
        return bookStorage.getAllByUserId(userId).stream()
                .map(bookMapper::bookToBookDto)
                .toList();
    }

    private long getId() {
        return ++idForBooks;
    }
}
