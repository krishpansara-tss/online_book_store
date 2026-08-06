package com.online_bookstore.app.controllers;

import com.online_bookstore.app.dtos.PageResponse;
import com.online_bookstore.app.dtos.orders.OrderBasicInformationDTO;
import com.online_bookstore.app.dtos.orders.OrderRequestDTO;
import com.online_bookstore.app.dtos.orders.OrderResponseDTO;
import com.online_bookstore.app.services.implemantation.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/app/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/add/{user_id}")
    public ResponseEntity<OrderResponseDTO> checkout(@PathVariable Long user_id,
                                                     @Valid @RequestBody OrderRequestDTO dto){
        OrderResponseDTO response = orderService.addNewOrder(user_id, dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<OrderBasicInformationDTO>> getAllOrders(@RequestParam(defaultValue = "0") Integer page,
                                                                               @RequestParam(defaultValue = "5") Integer size){
        PageResponse<OrderBasicInformationDTO> response = orderService.getAllOrders(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/all/active")
    public ResponseEntity<PageResponse<OrderBasicInformationDTO>> getAllActiveOrders(@RequestParam(defaultValue = "0") Integer page,
                                                                       @RequestParam(defaultValue = "5") Integer size){
        PageResponse<OrderBasicInformationDTO> response = orderService.getAllActiveOrders(page, size);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/get/{order_id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long order_id){
        OrderResponseDTO response = orderService.getOrderById(order_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByUserId(@PathVariable Long user_id){
        List<OrderResponseDTO> response = orderService.getOrdersByUserId(user_id);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/cancel/{order_id}")
    public ResponseEntity<Void> cancelOrderById(@PathVariable Long order_id){
       orderService.cancelOrder(order_id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
