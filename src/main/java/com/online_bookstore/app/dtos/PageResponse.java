package com.online_bookstore.app.dtos;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class PageResponse<T> {
    private List<T> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean last;
}
