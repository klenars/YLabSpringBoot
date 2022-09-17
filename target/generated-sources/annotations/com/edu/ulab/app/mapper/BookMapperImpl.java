package com.edu.ulab.app.mapper;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.web.request.BookRequest;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-09-17T10:12:09+0300",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
@Component
public class BookMapperImpl implements BookMapper {

    @Override
    public BookDto bookRequestToBookDto(BookRequest bookRequest) {
        if ( bookRequest == null ) {
            return null;
        }

        BookDto bookDto = new BookDto();

        bookDto.setTitle( bookRequest.getTitle() );
        bookDto.setAuthor( bookRequest.getAuthor() );
        bookDto.setPageCount( bookRequest.getPageCount() );

        return bookDto;
    }

    @Override
    public BookRequest bookDtoToBookRequest(BookDto bookDto) {
        if ( bookDto == null ) {
            return null;
        }

        BookRequest bookRequest = new BookRequest();

        bookRequest.setTitle( bookDto.getTitle() );
        bookRequest.setAuthor( bookDto.getAuthor() );
        bookRequest.setPageCount( bookDto.getPageCount() );

        return bookRequest;
    }
}
