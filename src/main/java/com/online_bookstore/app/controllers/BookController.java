package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.books.*;
import com.online_bookstore.app.services.implemantation.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/books")
public class BookController {
    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> addNewBook(@Valid @RequestBody BookRequestDTO dto){
        BookResponseDTO response =  bookService.addNewBook(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<BookBasicInformationResponseDTO>> getAllBooks(@RequestParam(defaultValue = "0") Integer page,
                                                                     @RequestParam(defaultValue = "5") Integer size,
                                                                     @RequestParam(defaultValue = "title") String sortBy,
                                                                     @RequestParam(defaultValue = "asc") String direction){
        PageResponse<BookBasicInformationResponseDTO> response = bookService.getAllBooks(page, size, sortBy, direction);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{book_id}")
    public ResponseEntity<BookResponseDTO> getBookById(@PathVariable Long book_id){
        BookResponseDTO response = bookService.getBookById(book_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/active")
    public ResponseEntity<PageResponse<BookBasicInformationResponseDTO>> getAllActiveBooks(@RequestParam(defaultValue = "0") Integer page,
                                                                           @RequestParam(defaultValue = "5") Integer size,
                                                                           @RequestParam(defaultValue = "title") String sortBy,
                                                                           @RequestParam(defaultValue = "asc") String direction){
        PageResponse<BookBasicInformationResponseDTO> response = bookService.getAllActiveBooks(page, size, sortBy,  direction);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{book_id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long book_id){
        bookService.deleteBook(book_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active/{book_id}")
    public ResponseEntity<Void> activateBookById(@PathVariable Long book_id){
        bookService.activeBook(book_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addstock/{book_id}")
    public ResponseEntity<Void> updateStock(@PathVariable Long book_id,
                                            @Valid @RequestBody UpdateBookStockDTO dto){
        bookService.addStock(book_id, dto);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/update/{book_id}")
    public ResponseEntity<BookResponseDTO> updateBook(@PathVariable Long book_id,
                                            @RequestBody BookUpdateDTO dto){
        BookResponseDTO book = bookService.updateBook(book_id, dto);

        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<BookResponseDTO>> searchBooks(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String isbn,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String publisher,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Double minRating,

            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size
    ) {

        BookSearchRequestDTO dto = new BookSearchRequestDTO();

        dto.setTitle(title);
        dto.setIsbn(isbn);
        dto.setCategory(category);
        dto.setAuthor(author);
        dto.setPublisher(publisher);
        dto.setMinPrice(minPrice);
        dto.setMaxPrice(maxPrice);
        dto.setMinRating(minRating);

        return ResponseEntity.ok(
                bookService.searchBooks(dto, page, size)
        );
    }
}
