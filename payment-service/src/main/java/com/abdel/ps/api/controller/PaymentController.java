package com.abdel.ps.api.controller;

import com.abdel.ps.api.entity.Payment;
import com.abdel.ps.api.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/doPayment")
    public Payment doPayment(@RequestBody Payment payment){
        return paymentService.savePayment(payment);
    }

    @GetMapping("/retrievePayment/{id}")
    public Payment findPaymentById(@PathVariable("id") int orderId){
        return paymentService.finById(orderId);
    }

}
