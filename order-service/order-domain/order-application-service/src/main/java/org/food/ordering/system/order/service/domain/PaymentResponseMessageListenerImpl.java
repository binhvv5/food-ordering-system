package org.food.ordering.system.order.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.domain.DomainConstants;
import org.food.ordering.system.order.service.domain.dto.message.PaymentResponse;
import org.food.ordering.system.order.service.domain.ports.input.message.listener.payment.PaymentResponseMessageListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Service
public class PaymentResponseMessageListenerImpl implements PaymentResponseMessageListener {

    @Override
    public void paymentCompleted(PaymentResponse paymentResponse) {
        log.info("Order Payment Saga process operation is completed for order id: {}", paymentResponse.getOrderId());
    }

    @Override
    public void paymentCancelled(PaymentResponse paymentResponse) {
        log.info("Order is roll backed for order id: {} with failure messages: {}",
                paymentResponse.getOrderId(),
                String.join(DomainConstants.FAILURE_MESSAGE_DELIMITER, paymentResponse.getFailureMessages()));
    }
}
