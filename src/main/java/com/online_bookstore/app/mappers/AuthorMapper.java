package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.authors.AuthorRequestDTO;
import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.models.Author;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Author toEntity(AuthorRequestDTO dto);
    AuthorResponseDTO toResponse(Author author);
}
