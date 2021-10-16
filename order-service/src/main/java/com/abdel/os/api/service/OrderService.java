package com.abdel.os.api.service;

import com.abdel.os.api.common.Payment;
import com.abdel.os.api.common.TransactionRequest;
import com.abdel.os.api.common.TransactionResponse;
import com.abdel.os.api.entity.Order;
import com.abdel.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private RestTemplate template;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest){
        String response="";
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());

        Payment paymentResponse = template.postForObject("http://PAYMENT-SERVICE/payment/doPayment", payment,Payment.class);
        response=paymentResponse.getPaymentStatus().equals("success")?"payment successfull":"there is failure in payment api";
        orderRepository.save(order);
        return  new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(), response);
    }
    public String orderFallBack(Exception e){
        return "Order service may be doawn, please try it later";
    }

}
