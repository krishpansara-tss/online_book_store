package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.authors.AuthorRequestDTO;
import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.services.implemantation.AuthorService;
import jakarta.validation.Valid;
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
    public ResponseEntity<AuthorResponseDTO> addNewAuthor(@Valid @RequestBody AuthorRequestDTO dto){
        AuthorResponseDTO response = authorService.addNewAuthor(dto);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<AuthorResponseDTO>> getAllAuthor(@RequestParam(defaultValue = "0") Integer page,
                                                                        @RequestParam(defaultValue = "5") Integer size,
                                                                        @RequestParam(defaultValue = "name") String sortBy,
                                                                        @RequestParam(defaultValue = "asc") String direction){
        PageResponse<AuthorResponseDTO> response = authorService.getAllAuthors(page, size, sortBy, direction);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{author_id}")
    public ResponseEntity<AuthorResponseDTO> getAuthorById(@PathVariable Long author_id){
        AuthorResponseDTO response = authorService.getAuthorById(author_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PutMapping("/update/{author_id}")
    public ResponseEntity<AuthorResponseDTO> updateAuthorById(@PathVariable Long author_id, @Valid @RequestBody AuthorRequestDTO dto){
        AuthorResponseDTO response = authorService.updateAuthorById(author_id, dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{author_id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable Long author_id){
        authorService.deleteAuthor(author_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active/{author_id}")
    public ResponseEntity<Void> activeAuthorById(@PathVariable Long author_id){
        authorService.activeAuthor(author_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/active")
    public ResponseEntity<PageResponse<AuthorResponseDTO>> getAllActiveAuthors(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "5") Integer size,
                                                                               @RequestParam(defaultValue = "name") String sortBy,
                                                                               @RequestParam(defaultValue = "asc") String direction){
        PageResponse<AuthorResponseDTO> response = authorService.getAllActiveAuthors(page, size, sortBy, direction);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
