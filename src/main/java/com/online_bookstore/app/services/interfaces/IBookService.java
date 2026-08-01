package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.books.BookRequestDTO;
import com.online_bookstore.app.dtos.books.BookResponseDTO;
import com.online_bookstore.app.dtos.users.UserRequestDTO;
import com.online_bookstore.app.dtos.users.UserResponseDTO;

public interface IBookService {
    BookResponseDTO addNewBook(BookRequestDTO dto);
    PageResponse<BookResponseDTO> getAllBooks(Integer page, Integer size);
    BookResponseDTO getBookById(Long bookId);
}
