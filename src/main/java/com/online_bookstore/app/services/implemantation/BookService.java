package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.books.*;
import com.online_bookstore.app.exceptions.BookNotFoundException;
import com.online_bookstore.app.exceptions.DuplicateResourceException;
import com.online_bookstore.app.exceptions.InvalidOperationException;
import com.online_bookstore.app.mappers.BookMapper;
import com.online_bookstore.app.models.Author;
import com.online_bookstore.app.models.Book;
import com.online_bookstore.app.models.Category;
import com.online_bookstore.app.models.Publisher;
import com.online_bookstore.app.repositories.BookRepository;
import com.online_bookstore.app.services.interfaces.IBookService;
import com.online_bookstore.app.specifications.BookSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class BookService implements IBookService {
    private final BookRepository bookRepository;

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    private final BookMapper bookMapper;

    private static final Logger logger = LoggerFactory.getLogger(BookService.class);

    @Override
    public BookResponseDTO addNewBook(BookRequestDTO dto) {
        Book book = bookMapper.toEntity(dto);
        if(bookRepository.existsByTitleIgnoreCase(book.getTitle())){
            logger.error("Book Having Title: {} already exists.", book.getTitle());
            throw new DuplicateResourceException("Book having the same title already exists.");
        }

        List<Author> authors = authorService.findMultipleAuthorsById(dto.getAuthorIds());

        if(authors.size() != dto.getAuthorIds().size()){
            logger.error("Some authors not found or Repeated authors entered while adding the Book having title: {}", book.getTitle());
            throw new InvalidOperationException("Some authors not found or Repeated authors entered.");
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

        logger.info("Book has been added Successfully with ID: " + saved_book.getBookId());

        return bookMapper.toResponse(saved_book);
    }

    @Override
    public PageResponse<BookBasicInformationResponseDTO> getAllBooks(Integer page, Integer size, String sortBy, String  direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();


        Pageable pageable = PageRequest.of(page, size, sort);
        Page<BookBasicInformationResponseDTO> bookPage = bookRepository.getAllBookBasicInformation(pageable);
        List<BookBasicInformationResponseDTO> content = bookPage.toList();

        return PageResponse.<BookBasicInformationResponseDTO>builder()
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

    @Override
    public Book getBookEntityById(Long bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> {
                logger.error("Book having ID: {} doesn't exists." + bookId);
                return new BookNotFoundException(bookId);
        });
    }

    @Transactional
    public void updateBookRating(Book book, Double averageRating) {
        book.setRatings(averageRating == null ? 0.0 : averageRating);
        logger.info("Book having ID: {} 's rating has been updated to {}", book.getBookId(), book.getRatings());

        bookRepository.save(book);
    }

    @Override
    public PageResponse<BookBasicInformationResponseDTO> getAllActiveBooks(Integer page, Integer size, String sortBy, String  direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<BookBasicInformationResponseDTO> bookPage = bookRepository.getAllActiveBookBasicInformation(pageable);

        List<BookBasicInformationResponseDTO> content = bookPage.stream()
                .toList();


        return PageResponse.<BookBasicInformationResponseDTO>builder()
                .content(content).
                page(bookPage.getNumber())
                .size(bookPage.getSize())
                .totalElements(bookPage.getTotalElements())
                .totalPages(bookPage.getTotalPages())
                .last(bookPage.isLast())
                .build();
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = getBookEntityById(bookId);
        if(!book.isActive()){
            logger.error("Book having ID: {} already been deleted." + bookId);
            throw new InvalidOperationException("Book is already deleted");
        }

        book.setActive(false);

        logger.info("Book has been deleted Successfully with ID: " + book.getBookId());

        bookRepository.save(book);
    }

    @Override
    public void activeBook(Long bookId) {
        Book book = getBookEntityById(bookId);
        if(book.isActive()){
            logger.error("Book having ID: {} already in active state deleted." + bookId);
                        throw new InvalidOperationException("Book is already activate");
        }

        book.setActive(true);
        logger.info("Book has been activated Successfully with ID: " + book.getBookId());

        bookRepository.save(book);
    }

    @Override
    public void addStock(Long bookId, UpdateBookStockDTO dto) {
        Book book = getBookEntityById(bookId);

        addStockByBookObj(book, dto.getStockToAdd());
        logger.info("Book having ID: {} 's stock has been updated to {}", book.getBookId(), book.getStock());

        bookRepository.save(book);
    }

    @Override
    public void addStockByBookObj(Book book, Long quantity) {
        book.setStock(book.getStock() + quantity);

        logger.info("Book having ID: {} 's stock has been updated to {}", book.getBookId(), book.getStock());

        bookRepository.save(book);
    }

    public BookResponseDTO updateBook(Long bookId, BookUpdateDTO dto){
        Book book = getBookEntityById(bookId);

        if(!book.isActive()){
            throw new InvalidOperationException("To update the Book, Please active it first.");
        }

        if(dto.getTitle() != null){
            book.setTitle(dto.getTitle());
        }

        if(dto.getISBN() != null){
            book.setISBN(dto.getISBN());
        }

        if(dto.getPrice() != null){
            book.setPrice(dto.getPrice());
        }

        Book updated_book = bookRepository.save(book);
        logger.info("Book has been updated Successfully with ID: " + book.getBookId());

        return bookMapper.toResponse(updated_book);
    }

    @Override
    public PageResponse<BookResponseDTO> searchBooks(BookSearchRequestDTO dto, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size);

        Specification<Book> specification = Specification.allOf();

        if (dto.getTitle() != null && !dto.getTitle().isBlank()) {
            specification = specification.and(
                    BookSpecification.hasTitle(dto.getTitle())
            );
        }

        if (dto.getCategory() != null && !dto.getCategory().isBlank()) {
            specification = specification.and(
                    BookSpecification.hasCategory(dto.getCategory())
            );
        }

        if (dto.getAuthor() != null && !dto.getAuthor().isBlank()) {
            specification = specification.and(
                    BookSpecification.hasAuthor(dto.getAuthor())
            );
        }

        if (dto.getPublisher() != null && !dto.getPublisher().isBlank()) {
            specification = specification.and(
                    BookSpecification.hasPublisher(dto.getPublisher())
            );
        }

        if (dto.getMinPrice() != null) {
            specification = specification.and(
                    BookSpecification.minPrice(dto.getMinPrice())
            );
        }

        if (dto.getMaxPrice() != null) {
            specification = specification.and(
                    BookSpecification.maxPrice(dto.getMaxPrice())
            );
        }

        if (dto.getMinRating() != null) {
            specification = specification.and(
                    BookSpecification.minRating(dto.getMinRating())
            );
        }

        Page<Book> books = bookRepository.findAll(specification, pageable);

        List<BookResponseDTO> content = books.getContent()
                .stream()
                .map(bookMapper::toResponse)
                .toList();

        return PageResponse.<BookResponseDTO>builder()
                .content(content)
                .page(books.getNumber())
                .size(books.getSize())
                .totalElements(books.getTotalElements())
                .totalPages(books.getTotalPages())
                .last(books.isLast())
                .build();
    }
}
