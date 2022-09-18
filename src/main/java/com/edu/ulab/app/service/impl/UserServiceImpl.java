package com.edu.ulab.app.service.impl;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.mapper.UserMapper;
import com.edu.ulab.app.service.UserService;
import com.edu.ulab.app.storage.BookStorage;
import com.edu.ulab.app.storage.UserStorage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private static long idForUsers = 0;

    private final UserStorage userStorage;
    private final BookStorage bookStorage;
    private final UserMapper userMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        log.info("userService got userDto for create: {}", userDto);
        userDto.setId(getId());
        log.info("userService set new id to userDto for create: {}", userDto);
        User user = userMapper.userDtoToUser(userDto);
        log.info("userService mapped userDto to User for save: {}", user);
        return userMapper.userToUserDto(userStorage.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        log.info("userService got userDto for update: {}", userDto);
        User userForUpdate = userMapper.userDtoToUser(userDto);
        log.info("userService mapped userDto to User for update: {}", userForUpdate);
        userForUpdate.setId(id);
        log.info("userService set id to user for update: {}", userDto);
        return userMapper.userToUserDto(userStorage.update(userForUpdate, id));
    }

    @Override
    public UserDto getUserById(Long id) {
        log.info("userService got id {} to get user from storage", id);
        return userMapper.userToUserDto(userStorage.getById(id));
    }

    @Override
    public void deleteUserById(Long id) {
        log.info("userService got id {} to delete user from storage", id);
        userStorage.delete(id);
        log.info("userService deleted user with id {}", id);
        bookStorage.getAllByUserId(id).stream()
                .peek(book -> log.info("delete book id={} owner id={}", book.getId(), book.getUser().getId()))
                .forEach(book -> bookStorage.delete(book.getId()));
        log.info("user's id {} books deleted", id);
    }

    @Override
    public void setListBooks(Long userId) {
        log.info("userService got id {} to add book's list", userId);
        userStorage.getById(userId).setBooks(bookStorage.getAllByUserId(userId));
    }

    private long getId() {
        return ++idForUsers;
    }
}
