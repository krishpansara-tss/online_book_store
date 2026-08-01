package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.authors.AuthorRequestDTO;
import com.online_bookstore.app.dtos.authors.AuthorResponseDTO;
import com.online_bookstore.app.exceptions.AuthorNotFoundException;
import com.online_bookstore.app.mappers.AuthorMapper;
import com.online_bookstore.app.models.Author;
import com.online_bookstore.app.repositories.AuthorRepository;
import com.online_bookstore.app.services.interfaces.IAuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService implements IAuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public AuthorResponseDTO addNewAuthor(AuthorRequestDTO dto) {
        Author author = authorMapper.toEntity(dto);
        Author saved_author = authorRepository.save(author);
        return authorMapper.toResponse(saved_author);
    }

    @Override
    public PageResponse<AuthorResponseDTO> getAllAuthors(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
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
                () -> new AuthorNotFoundException(id)
        );

        return authorMapper.toResponse(author);
    }

    @Override
    public List<Author> findMultipleAuthorsById(List<Long> ids) {
        return authorRepository.findAllById(ids);
    }
}
