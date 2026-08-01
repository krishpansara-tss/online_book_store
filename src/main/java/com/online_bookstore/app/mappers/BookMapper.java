package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.books.BookRequestDTO;
import com.online_bookstore.app.dtos.books.BookResponseDTO;
import com.online_bookstore.app.models.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toEntity(BookRequestDTO dto);
    BookResponseDTO toResponse(Book book);
}
