package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.authors.AuthorRequestDTO;
import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.models.Author;

import java.util.List;

public interface IAuthorService {

    AuthorResponseDTO addNewAuthor(AuthorRequestDTO dto);
    PageResponse<AuthorResponseDTO> getAllAuthors(Integer page, Integer size);
    AuthorResponseDTO getAuthorById(Long id);

    List<Author> findMultipleAuthorsById(List<Long> ids);

}
