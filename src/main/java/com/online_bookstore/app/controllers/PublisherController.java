package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.publishers.PublisherRequestDTO;
import com.online_bookstore.app.dtos.publishers.PublisherResponseDTO;
import com.online_bookstore.app.services.implemantation.PublisherService;
import jakarta.validation.Valid;
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
    public ResponseEntity<PublisherResponseDTO> addNewPublisher(@Valid @RequestBody PublisherRequestDTO dto){
        PublisherResponseDTO response = publisherService.addNewPublisher(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

    @DeleteMapping("/delete/{publisher_id}")
    public ResponseEntity<Void> deletePublisherById(@PathVariable Long publisher_id){
        publisherService.deletePublisher(publisher_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/active/{publisher_id}")
    public ResponseEntity<Void> activatePublisherById(@PathVariable Long publisher_id){
        publisherService.activePublisher(publisher_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all/active")
    public ResponseEntity<PageResponse<PublisherResponseDTO>> getAllActivePublishers(@RequestParam(defaultValue = "0") Integer page,
                                                                                  @RequestParam(defaultValue = "5") Integer size){
        PageResponse<PublisherResponseDTO> response = publisherService.getAllActivePublishers(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update/{publisher_id}")
    public ResponseEntity<PublisherResponseDTO> updatePublisherById(@PathVariable Long publisher_id, @Valid @RequestBody PublisherRequestDTO dto){
        PublisherResponseDTO response = publisherService.updatePublisherById(publisher_id, dto);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PageResponse<PublisherResponseDTO>> searchPublisherByName(@RequestParam(defaultValue = "") String name,
                                                                                    @RequestParam(defaultValue = "0") Integer page,
                                                                                    @RequestParam(defaultValue = "5") Integer size){
        PageResponse<PublisherResponseDTO> response = publisherService.searchPublisherByName(name, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
