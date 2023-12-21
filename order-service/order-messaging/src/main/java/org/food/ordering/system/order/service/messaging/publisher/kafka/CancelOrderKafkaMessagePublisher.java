package org.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import org.food.ordering.system.kafka.producer.service.KafkaProducer;
import org.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import org.food.ordering.system.order.service.domain.event.OrderCancelledEvent;
import org.food.ordering.system.order.service.domain.ports.output.message.publisher.payment.OrderCancelledPaymentMessagePublisher;
import org.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelOrderKafkaMessagePublisher implements OrderCancelledPaymentMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final OrderKafkaMessageHelper orderKafkaMessageHelper;


    @Override
    public void publish(OrderCancelledEvent domainEvent) {

        String orderId = domainEvent.getOrder().getId().getValue().toString();
        log.info("Received OrderCancelledEvent for order id: {}", orderId);
        try {
            PaymentRequestAvroModel paymentRequestAvroModel = orderMessagingDataMapper.orderCancelledEventToPaymentRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getPaymentRequestTopicName(),
                    orderId,
                    paymentRequestAvroModel,
                    orderKafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getPaymentRequestTopicName(),
                            paymentRequestAvroModel,
                            orderId,
                            "PaymentRequestAvroModel"));
            log.info("PaymentRequestAvroModel sent to Kafka for order id: {}", paymentRequestAvroModel.getOrderId());
        } catch (Exception e) {
            log.error("Error while sending PaymentRequestAvroModel message" +
                    " to Kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
