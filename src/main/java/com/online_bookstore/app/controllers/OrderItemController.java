package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.orderitems.OrderItemResponseDTO;
import com.online_bookstore.app.services.implemantation.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/orderitems")
public class OrderItemController {
    private final OrderItemService orderItemService;


    @GetMapping("/order/{order_id}")
    public ResponseEntity<PageResponse<OrderItemResponseDTO>> getOrderItemByOrderId(@PathVariable Long order_id,
                                                                                    @RequestParam(defaultValue = "0") Integer page,
                                                                                    @RequestParam(defaultValue = "5") Integer size){
        PageResponse<OrderItemResponseDTO> response = orderItemService.getOrderItemByOrderId(order_id, page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
