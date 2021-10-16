package com.abdel.ps.api.service;

import com.abdel.ps.api.entity.Payment;
import com.abdel.ps.api.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.UUID;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment savePayment(Payment payment){
        payment.setPaymentStatus(paymentProcessing());
        payment.setTransactionId(UUID.randomUUID().toString());
        return paymentRepository.save(payment);
    }

    public  String paymentProcessing(){
        //payment gateway (paypal..)
        return  new Random().nextBoolean()?"success":"false";
    }

    public Payment finById(int orderId) {
        return paymentRepository.findByOrderId(orderId);
    }
}
