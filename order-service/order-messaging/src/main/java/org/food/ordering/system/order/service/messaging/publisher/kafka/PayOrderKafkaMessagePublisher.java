package org.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.kafka.order.avro.model.RestaurantApprovalRequestAvroModel;
import org.food.ordering.system.kafka.producer.KafkaMessageHelper;
import org.food.ordering.system.kafka.producer.service.KafkaProducer;
import org.food.ordering.system.order.service.domain.config.OrderServiceConfigData;
import org.food.ordering.system.order.service.domain.event.OrderPaidEvent;
import org.food.ordering.system.order.service.domain.ports.output.message.publisher.restaurantapproval.OrderPaidMessagePublisher;
import org.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PayOrderKafkaMessagePublisher implements OrderPaidMessagePublisher {
    private final OrderMessagingDataMapper orderMessagingDataMapper;
    private final OrderServiceConfigData orderServiceConfigData;
    private final KafkaProducer<String, RestaurantApprovalRequestAvroModel> kafkaProducer;
    private final KafkaMessageHelper kafkaMessageHelper;

    @Override
    public void publish(OrderPaidEvent domainEvent) {
        String orderId = domainEvent.getOrder().getId().getValue().toString();
        try {
            log.info("RestaurantApprovalRequestAvroModel send to kafka for order id: {}", orderId);
            RestaurantApprovalRequestAvroModel restaurantApprovalRequestAvroModel
                    = orderMessagingDataMapper.orderPaidToRestaurantApprovalRequestAvroModel(domainEvent);
            kafkaProducer.send(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                    orderId,
                    restaurantApprovalRequestAvroModel,
                    kafkaMessageHelper.getKafkaCallBack(orderServiceConfigData.getRestaurantApprovalRequestTopicName(),
                            restaurantApprovalRequestAvroModel, orderId, "RestaurantApprovalRequestAvroModel"));
            log.info("RestaurantApprovalRequestAvroModel send to kafka for order id: {}", orderId);
        } catch (Exception e) {
            log.error("Error while sending RestaurantApprovalRequestAvroModel message" +
                    " to Kafka with order id: {}, error: {}", orderId, e.getMessage());
        }
    }
}
