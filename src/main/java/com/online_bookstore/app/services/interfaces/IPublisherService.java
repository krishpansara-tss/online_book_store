package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.models.Publisher;

public interface IPublisherService {

    PublisherResponseDTO addNewPublisher(PublisherRequestDTO dto);
    PageResponse<PublisherResponseDTO> getAllPublishers(Integer page, Integer size);
    PublisherResponseDTO getPublisherById(Long id);
    PublisherResponseDTO updatePublisherById(Long publisherId, PublisherRequestDTO dto);
    Publisher getPublisherEntityById(Long id);
    void deletePublisher(Long publisherId);
    void activePublisher(Long publisherId);
    PageResponse<PublisherResponseDTO> getAllActivePublishers(Integer page, Integer size);
    PageResponse<PublisherResponseDTO> searchPublisherByName(String name, Integer page, Integer size);
}
