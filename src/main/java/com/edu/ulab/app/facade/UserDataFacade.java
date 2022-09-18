package com.edu.ulab.app.facade;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.mapper.BookMapper;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.BookService;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.web.request.UserBookRequest;
import com.edu.ulab.app.web.response.UserBookResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class UserDataFacade {
    private final UserService userService;
    private final BookService bookService;
    private final UserMapper userMapper;
    private final BookMapper bookMapper;

    public UserDataFacade(UserService userService,
                          BookService bookService,
                          UserMapper userMapper,
                          BookMapper bookMapper) {
        this.userService = userService;
        this.bookService = bookService;
        this.userMapper = userMapper;
        this.bookMapper = bookMapper;
    }

    public UserBookResponse createUserWithBooks(UserBookRequest userBookRequest) {
        log.info("Got user book create request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request: {}", userDto);

        UserDto createdUser = userService.createUser(userDto);
        log.info("Created user: {}", createdUser);

        List<Long> bookIdList = userBookRequest.getBookRequests().stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(createdUser.getId()))
                .peek(mappedBookDto -> log.info("mapped book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids: {}", bookIdList);

        userService.setListBooks(createdUser.getId());
        log.info("Set list books to user id={}", createdUser.getId());

        return UserBookResponse.builder()
                .userId(createdUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse updateUserWithBooks(UserBookRequest userBookRequest, Long userId) {
        log.info("Got user book update request: {}", userBookRequest);
        UserDto userDto = userMapper.userRequestToUserDto(userBookRequest.getUserRequest());
        log.info("Mapped user request for update: {}", userDto);

        UserDto updatedUser = userService.updateUser(userDto, userId);
        log.info("Got updated user from userService: {}", updatedUser);

        bookService.getAllByUserId(userId)
                .forEach(bookDto -> bookService.deleteBookById(bookDto.getId()));
        log.info("Deleted old books from updated user");

        List<Long> bookIdList = userBookRequest.getBookRequests().stream()
                .filter(Objects::nonNull)
                .map(bookMapper::bookRequestToBookDto)
                .peek(bookDto -> bookDto.setUserId(updatedUser.getId()))
                .peek(mappedBookDto -> log.info("mapped new book: {}", mappedBookDto))
                .map(bookService::createBook)
                .peek(createdBook -> log.info("Created new book: {}", createdBook))
                .map(BookDto::getId)
                .toList();
        log.info("Collected new book ids: {}", bookIdList);

        userService.setListBooks(updatedUser.getId());
        log.info("Set list books to user id={}", userId);

        return UserBookResponse.builder()
                .userId(updatedUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public UserBookResponse getUserWithBooks(Long userId) {
        log.info("GET request with id: {}", userId);
        UserDto calledUser = userService.getUserById(userId);
        log.info("Got userDto from service: {}", calledUser);
        List<Long> bookIdList = bookService.getAllByUserId(userId).stream()
                .map(BookDto::getId)
                .toList();
        log.info("Collected book ids for called user: {}", bookIdList);

        return UserBookResponse.builder()
                .userId(calledUser.getId())
                .booksIdList(bookIdList)
                .build();
    }

    public void deleteUserWithBooks(Long userId) {
        log.info("DELETE request with id: {}", userId);
        userService.deleteUserById(userId);
        log.info("User id={} and his books deleted", userId);
    }
}
