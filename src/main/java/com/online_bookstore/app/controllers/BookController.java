package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.books.BookRequestDTO;
import com.online_bookstore.app.dtos.books.BookResponseDTO;
import com.online_bookstore.app.services.implemantation.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> addNewBook(@RequestBody BookRequestDTO dto){
        BookResponseDTO response =  bookService.addNewBook(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<BookResponseDTO>> getAllBooks(@RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "5") Integer size){
        PageResponse<BookResponseDTO> response = bookService.getAllBooks(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long book_id){
        BookResponseDTO response = bookService.getBookById(book_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
