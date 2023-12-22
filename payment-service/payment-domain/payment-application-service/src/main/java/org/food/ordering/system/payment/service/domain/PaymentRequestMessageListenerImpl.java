package org.food.ordering.system.payment.service.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.payment.service.domain.dto.PaymentRequest;
import org.food.ordering.system.payment.service.domain.event.PaymentCancelledEvent;
import org.food.ordering.system.payment.service.domain.event.PaymentCompletedEvent;
import org.food.ordering.system.payment.service.domain.event.PaymentEvent;
import org.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;
import org.food.ordering.system.payment.service.domain.ports.input.message.listener.PaymentRequestMessageListener;
import org.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCancelledMessagePublisher;
import org.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentCompletedMessagePublisher;
import org.food.ordering.system.payment.service.domain.ports.output.message.publisher.PaymentFailMessagePublisher;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentRequestMessageListenerImpl implements PaymentRequestMessageListener {

    private final PaymentRequestHelper paymentRequestHelper;


    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {
        PaymentEvent paymentEvent = paymentRequestHelper.persistCancelPayment(paymentRequest);
        fireEvent(paymentEvent);
    }

    private void fireEvent(PaymentEvent paymentEvent) {
        log.info("Publishing payment event with payment id: {} and order id: {}",
                paymentEvent.getPayment().getOrderId().getValue(),
                paymentEvent.getPayment().getOrderId().getValue());
        paymentEvent.fire();
    }


}
