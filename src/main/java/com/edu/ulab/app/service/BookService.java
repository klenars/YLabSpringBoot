package com.edu.ulab.app.service;


import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.entity.User;

import java.util.List;

public interface BookService {
    BookDto createBook(BookDto userDto);

    BookDto updateBook(BookDto userDto, Long id);

    BookDto getBookById(Long id);

    void deleteBookById(Long id);

    void setUser(Long bookId, User user);

    List<BookDto> getAllByUserId(Long userId);
}
