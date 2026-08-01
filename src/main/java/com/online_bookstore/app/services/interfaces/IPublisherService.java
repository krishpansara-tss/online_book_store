package com.online_bookstore.app.services.interfaces;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.models.Publisher;

public interface IPublisherService {

    PublisherResponseDTO addNewPublisher(PublisherRequestDTO dto);
    PageResponse<PublisherResponseDTO> getAllPublishers(Integer page, Integer size);
    PublisherResponseDTO getPublisherById(Long id);
    Publisher getPublisherEntityById(Long id);
}
