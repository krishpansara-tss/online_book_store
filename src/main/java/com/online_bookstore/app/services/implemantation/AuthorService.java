package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.authors.AuthorRequestDTO;
import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.exceptions.AuthorNotFoundException;
import com.online_bookstore.app.exceptions.DuplicateResourceException;
import com.online_bookstore.app.exceptions.InvalidOperationException;
import com.online_bookstore.app.mappers.AuthorMapper;
import com.online_bookstore.app.models.Author;
import com.online_bookstore.app.repositories.AuthorRepository;
import com.online_bookstore.app.services.interfaces.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthorService.class);

    @Override
    public AuthorResponseDTO addNewAuthor(AuthorRequestDTO dto) {
        Author author = authorMapper.toEntity(dto);

        if(authorRepository.existsByNameIgnoreCase(author.getName())){
            logger.error("Author having name: {} already been deleted." + author.getName());
            throw new DuplicateResourceException("Author name already exists.");
        }

        Author saved_author = authorRepository.save(author);
        logger.info("Author has been added Successfully with ID: " + saved_author.getAuthorId());
        return authorMapper.toResponse(saved_author);
    }

    @Override
    public PageResponse<AuthorResponseDTO> getAllAuthors(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Author> authorPage = authorRepository.findAll(pageable);

        List<AuthorResponseDTO> content = authorPage.
                map(authorMapper::toResponse).
                toList();

        return PageResponse.<AuthorResponseDTO>builder().
                content(content).
                page(authorPage.getNumber()).
                size(authorPage.getSize()).
                totalElements(authorPage.getNumberOfElements()).
                totalPages(authorPage.getTotalPages()).
                last(authorPage.isLast()).
                build();
    }

    @Override
    public AuthorResponseDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(
                () -> {
                    logger.error("Author having ID: {} doesn't exists." + id);
                    return new AuthorNotFoundException(id);
                }
        );

        return authorMapper.toResponse(author);
    }

    @Override
    public List<Author> findMultipleAuthorsById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }

    @Override
    public PageResponse<AuthorResponseDTO> getAllActiveAuthors(Integer page, Integer size, String sortBy, String direction) {
        Sort sort = direction.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Author> authorPage = authorRepository.findByIsActiveTrue(pageable);

        List<AuthorResponseDTO> content = authorPage.stream()
                .map(authorMapper::toResponse)
                .toList();

        return PageResponse.<AuthorResponseDTO>builder()
                .content(content).
                page(authorPage.getNumber())
                .size(authorPage.getSize())
                .totalElements(authorPage.getTotalElements())
                .totalPages(authorPage.getTotalPages())
                .last(authorPage.isLast())
                .build();
    }

    @Override
    public void deleteAuthor(Long authorId) {
        Author author = getAuthorEntityById(authorId);

        if(!author.isActive()){
            logger.info("Author having ID: {} has been already deleted.", authorId);
            throw new InvalidOperationException("Author is already deleted");
        }

        author.setActive(false);
        logger.info("Author has been deleted Successfully with ID: " + author.getAuthorId());
        authorRepository.save(author);
    }

    @Override
    public Author getAuthorEntityById(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> {
                    logger.error("Author having ID: {} doesn't exists." + id);
                    return new AuthorNotFoundException(id);
                }
        );
    }

    @Override
    public void activeAuthor(Long authorId) {
        Author author = getAuthorEntityById(authorId);
        if(author.isActive()){
            logger.info("Author having ID: {} has been already in active state.", authorId);
            throw new InvalidOperationException("Author is already activate");
        }
        author.setActive(true);

        logger.info("Author has been activated Successfully with ID: " + author.getAuthorId());

        authorRepository.save(author);

    }

    @Override
    public AuthorResponseDTO updateAuthorById(Long authorId, AuthorRequestDTO dto) {
        Author author = getAuthorEntityById(authorId);

        author.setName(dto.getName());
        Author updated_author = authorRepository.save(author);

        logger.info("Author has been updated Successfully with ID: " + updated_author.getAuthorId());

        return authorMapper.toResponse(author);
    }
}
