package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.services.implemantation.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/publishers")
public class PublisherController {
    private final PublisherService publisherService;

    @PostMapping
    public ResponseEntity<PublisherResponseDTO> addNewPublisher(@RequestBody PublisherRequestDTO dto){
        PublisherResponseDTO response = publisherService.addNewPublisher(dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<PublisherResponseDTO>> getAllPublishers(@RequestParam(defaultValue = "0") Integer page,
                                                                            @RequestParam(defaultValue = "5") Integer size){
        PageResponse<PublisherResponseDTO> response = publisherService.getAllPublishers(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{publisher_id}")
    public ResponseEntity<PublisherResponseDTO> getPublisherById(@PathVariable Long publisher_id){
        PublisherResponseDTO response = publisherService.getPublisherById(publisher_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
