package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.exceptions.PublisherNotFoundException;
import com.online_bookstore.app.mappers.PublisherMapper;
import com.online_bookstore.app.models.Publisher;
import com.online_bookstore.app.repositories.PublisherRepository;
import com.online_bookstore.app.services.interfaces.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PublisherService implements IPublisherService {
    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Override
    public PublisherResponseDTO addNewPublisher(PublisherRequestDTO dto) {
        Publisher publisher = publisherMapper.toEntity(dto);
        Publisher added_publisher = publisherRepository.save(publisher);
        return publisherMapper.toResponse(added_publisher);
    }

    @Override
    public PageResponse<PublisherResponseDTO> getAllPublishers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Publisher> publisherPage = publisherRepository.findAll(pageable);

        List<PublisherResponseDTO> content = publisherPage
                .getContent()
                .stream()
                .map(publisherMapper::toResponse)
                .toList();

        return PageResponse.<PublisherResponseDTO>builder().
                content(content).
                page(publisherPage.getNumber()).
                size(publisherPage.getSize()).
                totalElements(publisherPage.getNumberOfElements()).
                totalPages(publisherPage.getTotalPages()).
                last(publisherPage.isLast()).
                build();
    }

    @Override
    public PublisherResponseDTO getPublisherById(Long id) {
        Publisher publisher = publisherRepository.findById(id).orElseThrow(
                () -> new PublisherNotFoundException(id)
        );

        return publisherMapper.toResponse(publisher);
    }

    @Override
    public Publisher getPublisherEntityById(Long id) {
        return publisherRepository.findById(id).orElseThrow(
                () -> new PublisherNotFoundException(id)
        );
    }
    
}
