package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.books.*;
import com.online_bookstore.app.models.Book;

public interface IBookService {
    BookResponseDTO addNewBook(BookRequestDTO dto);
    BookResponseDTO getBookById(Long bookId);
    BookResponseDTO updateBook(Long bookId, BookUpdateDTO dto);

    Book getBookEntityById(Long bookId);

    void deleteBook(Long bookId);
    void activeBook(Long bookId);
    void addStockByBookObj(Book book, Long quantity);
    void addStock(Long bookId, UpdateBookStockDTO dto);

    PageResponse<BookBasicInformationResponseDTO> getAllBooks(Integer page, Integer size,  String sortBy, String  direction);
    PageResponse<BookResponseDTO> searchBooks(BookSearchRequestDTO dto, Integer page, Integer size);
    PageResponse<BookBasicInformationResponseDTO> getAllActiveBooks(Integer page, Integer size,  String sortBy, String  direction);
}
