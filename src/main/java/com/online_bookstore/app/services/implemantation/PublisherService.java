package com.online_bookstore.app.services.implemantation;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.exceptions.DuplicateResourceException;
import com.online_bookstore.app.exceptions.InvalidOperationException;
import com.online_bookstore.app.exceptions.PublisherNotFoundException;
import com.online_bookstore.app.mappers.PublisherMapper;
import com.online_bookstore.app.models.Publisher;
import com.online_bookstore.app.repositories.PublisherRepository;
import com.online_bookstore.app.services.interfaces.IPublisherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(PublisherService.class);

    @Override
    public PublisherResponseDTO addNewPublisher(PublisherRequestDTO dto) {
        Publisher publisher = publisherMapper.toEntity(dto);

        if(publisherRepository.existsByNameIgnoreCase(publisher.getName())){
            logger.error("Publisher Having name: {} already exists.", publisher.getName());
            throw new DuplicateResourceException("Publisher already exists.");
        }

        Publisher added_publisher = publisherRepository.save(publisher);
        logger.info("Publisher having ID: {}  added successfully.", added_publisher.getPublisherId());

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
                () -> {
                    logger.error("Publisher having ID: {}  doesn't exists.", id);
                    return new PublisherNotFoundException(id);
                }
        );

        return publisherMapper.toResponse(publisher);
    }

    @Override
    public Publisher getPublisherEntityById(Long id) {
        return publisherRepository.findById(id).orElseThrow(
                () -> {
                    logger.error("Publisher having ID: {}  doesn't exists.", id);
                    return new PublisherNotFoundException(id);
                }
        );
    }

    @Override
    public PageResponse<PublisherResponseDTO> getAllActivePublishers(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Publisher> publisherPage = publisherRepository.findByIsActiveTrue(pageable);

        List<PublisherResponseDTO> content = publisherPage.stream()
                .map(publisherMapper::toResponse)
                .toList();


        return PageResponse.<PublisherResponseDTO>builder()
                .content(content).
                page(publisherPage.getNumber())
                .size(publisherPage.getSize())
                .totalElements(publisherPage.getTotalElements())
                .totalPages(publisherPage.getTotalPages())
                .last(publisherPage.isLast())
                .build();
    }

    @Override
    public void deletePublisher(Long publisherId) {
        Publisher publisher = getPublisherEntityById(publisherId);

        if(!publisher.isActive()){
            logger.error("Publisher having ID: {} is already deleted.", publisherId);
            throw new InvalidOperationException("Publisher is already deleted");
        }

        publisher.setActive(false);
        logger.info("Publisher having ID: {} is has been deleted successfully.", publisherId);

        publisherRepository.save(publisher);

    }

    @Override
    public void activePublisher(Long publisherId) {
        Publisher publisher = getPublisherEntityById(publisherId);
        if(publisher.isActive()){
            logger.error("Publisher having ID: {} is already in the active state.", publisherId);
            throw new InvalidOperationException("Publisher is already activate");
        }
        publisher.setActive(true);
        logger.info("Publisher having ID: {} is has been activated successfully.", publisherId);
        publisherRepository.save(publisher);
    }

    @Override
    public PageResponse<PublisherResponseDTO> searchPublisherByName(String name, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Publisher> publisherPage = publisherRepository.findByNameContainingIgnoreCase(name, pageable);

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
    public PublisherResponseDTO updatePublisherById(Long publisherId, PublisherRequestDTO dto) {
        Publisher publisher = getPublisherEntityById(publisherId);

        publisher.setName(dto.getName());
        Publisher updated_publisher = publisherRepository.save(publisher);

        logger.info("Publisher having ID: {} is has been updated successfully.", publisherId);

        return publisherMapper.toResponse(updated_publisher);
    }


}
