package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.books.BookRequestDTO;
import com.online_bookstore.app.dtos.books.BookResponseDTO;
import com.online_bookstore.app.exceptions.BookNotFoundException;
import com.online_bookstore.app.mappers.BookMapper;
import com.online_bookstore.app.models.Author;
import com.online_bookstore.app.models.Book;
import com.online_bookstore.app.models.Category;
import com.online_bookstore.app.models.Publisher;
import com.online_bookstore.app.repositories.BookRepository;
import com.online_bookstore.app.services.interfaces.IBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    private final BookMapper bookMapper;

    @Override
    public BookResponseDTO addNewBook(BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        List<Author> authors = authorService.findMultipleAuthorsById(dto.getAuthorIds());

        if(authors.size() != dto.getAuthorIds().size()){
            throw new RuntimeException("Some authors not found");
        }

        book.getAuthors().addAll(authors);

        Category category = categoryService.getCategoryEntityById(dto.getCategoryId());
        if(category != null){
            book.setCategory(category);
        }

        Publisher publisher = publisherService.getPublisherEntityById(dto.getPublisherId());
        if(publisher != null){
            book.setPublisher(publisher);
        }

        Book saved_book = bookRepository.save(book);

        return bookMapper.toResponse(saved_book);
    }

    @Override
    public PageResponse<BookResponseDTO> getAllBooks(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> bookPage = bookRepository.findAll(pageable);
        List<BookResponseDTO> content = bookPage.map(bookMapper::toResponse).toList();

        return PageResponse.<BookResponseDTO>builder()
                .content(content)
                .page(bookPage.getNumber())
                .size(bookPage.getSize())
                .totalElements(bookPage.getNumberOfElements())
                .totalPages(bookPage.getTotalPages())
                .last(bookPage.isLast())
                .build();
    }

    @Override
    public BookResponseDTO getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(
                () -> new BookNotFoundException(bookId)
        );

        return bookMapper.toResponse(book);
    }
}
