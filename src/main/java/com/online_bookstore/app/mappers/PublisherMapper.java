package com.online_bookstore.app.mappers;

import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.models.Publisher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PublisherMapper {
    Publisher toEntity(PublisherRequestDTO dto);
    PublisherResponseDTO toResponse(Publisher publisher);
}
