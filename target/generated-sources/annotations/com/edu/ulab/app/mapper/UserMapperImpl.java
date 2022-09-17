package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.User;
import com.edu.ulab.app.web.request.UserRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-17T21:00:09+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto userRequestToUserDto(UserRequest userRequest) {
        if ( userRequest == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setFullName( userRequest.getFullName() );
        userDto.setTitle( userRequest.getTitle() );
        userDto.setAge( userRequest.getAge() );

        return userDto;
    }

    @Override
    public UserRequest userDtoToUserRequest(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        UserRequest userRequest = new UserRequest();

        userRequest.setFullName( userDto.getFullName() );
        userRequest.setTitle( userDto.getTitle() );
        userRequest.setAge( userDto.getAge() );

        return userRequest;
    }

    @Override
    public User userDtoToUser(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = new User();

        user.setId( userDto.getId() );
        user.setFullName( userDto.getFullName() );
        user.setTitle( userDto.getTitle() );
        user.setAge( userDto.getAge() );

        return user;
    }

    @Override
    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDto userDto = new UserDto();

        userDto.setId( user.getId() );
        userDto.setFullName( user.getFullName() );
        userDto.setTitle( user.getTitle() );
        userDto.setAge( user.getAge() );

        return userDto;
    }
}
