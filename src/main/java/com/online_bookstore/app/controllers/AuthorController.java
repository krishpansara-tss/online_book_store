package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.authors.AuthorRequestDTO;
import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.services.implemantation.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping
    public ResponseEntity<AuthorResponseDTO> addNewAuthor(@RequestBody AuthorRequestDTO dto){
        AuthorResponseDTO response = authorService.addNewAuthor(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<AuthorResponseDTO>> getAllAuthor(@RequestParam(defaultValue = "0") Integer page,
                                                                          @RequestParam(defaultValue = "5") Integer size){
        PageResponse<AuthorResponseDTO> response = authorService.getAllAuthors(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{author_id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long author_id){
        AuthorResponseDTO response = authorService.getAuthorById(author_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
