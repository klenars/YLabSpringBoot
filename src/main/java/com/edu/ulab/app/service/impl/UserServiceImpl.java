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
        userDto.setId(getId());
        User user = userMapper.userDtoToUser(userDto);
        return userMapper.userToUserDto(userStorage.save(user));
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        User userForUpdate = userMapper.userDtoToUser(userDto);
        return userMapper.userToUserDto(userStorage.update(userForUpdate, id));
    }

    @Override
    public UserDto getUserById(Long id) {
        return userMapper.userToUserDto(userStorage.getById(id));
    }

    @Override
    public void deleteUserById(Long id) {

    }

    @Override
    public void setListBooks(Long userId) {
        userStorage.getById(userId).setBooks(bookStorage.getAllByUserId(userId));
    }

    private long getId() {
        return ++idForUsers;
    }
}
