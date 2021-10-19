package com.abdel.os.api.service;

import com.abdel.os.api.common.Payment;
import com.abdel.os.api.common.TransactionRequest;
import com.abdel.os.api.common.TransactionResponse;
import com.abdel.os.api.entity.Order;
import com.abdel.os.api.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RefreshScope
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    @Lazy
    private RestTemplate template;

    @Value("${microservice.payment-service.endpoints.endpoint.uri}")
    private String ENDPOINT_PAYMENT_URL;

    public TransactionResponse saveOrder(TransactionRequest transactionRequest){
        String response="";
        Order order = transactionRequest.getOrder();
        Payment payment = transactionRequest.getPayment();
        payment.setOrderId(order.getId());
        payment.setAmount(order.getPrice());
        Payment paymentResponse = template.postForObject(ENDPOINT_PAYMENT_URL, payment,Payment.class);
        response=paymentResponse.getPaymentStatus().equals("success")?"payment successfull":"there is failure in payment api";
        orderRepository.save(order);
        return  new TransactionResponse(order,paymentResponse.getAmount(),paymentResponse.getTransactionId(), response);
    }
    public String orderFallBack(Exception e){
        return "Order service may be doawn, please try it later";
    }

}
