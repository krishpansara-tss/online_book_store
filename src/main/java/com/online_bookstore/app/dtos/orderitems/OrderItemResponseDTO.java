package com.online_bookstore.app.dtos.orderitems;

import com.online_bookstore.app.dtos.books.BookResponseDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class OrderItemResponseDTO {
    private Long orderItemId;
    private Long quantity;
    private Double priceAtPurchase;
    private String title;
}
