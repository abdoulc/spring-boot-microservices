package com.abdel.os.api.controller;

import com.abdel.os.api.common.Payment;
import com.abdel.os.api.common.TransactionRequest;
import com.abdel.os.api.common.TransactionResponse;
import com.abdel.os.api.entity.Order;
import com.abdel.os.api.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/bookOrder")
    //@CircuitBreaker(name="orderService", fallbackMethod="orderFallBack")
    public TransactionResponse bookOrder(@RequestBody TransactionRequest transactionRequest){

        return orderService.saveOrder(transactionRequest);
    }



}
