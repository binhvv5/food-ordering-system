package org.food.ordering.system.order.service.messaging.listener.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.domain.DomainConstants;
import org.food.ordering.system.kafka.consumer.KafkaConsumer;
import org.food.ordering.system.kafka.order.avro.model.OrderApprovalStatus;
import org.food.ordering.system.kafka.order.avro.model.RestaurantApprovalResponseAvroModel;
import org.food.ordering.system.order.service.domain.exception.OrderNotFoundException;
import org.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import org.food.ordering.system.order.service.messaging.mapper.OrderMessagingDataMapper;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestaurantApprovalResponseKafkaListener implements KafkaConsumer<RestaurantApprovalResponseAvroModel> {

    private final RestaurantApprovalResponseMessageListener restaurantApprovalRMessageListener;
    private final OrderMessagingDataMapper orderMessagingDataMapper;

    @Override
    @KafkaListener(id = "${kafka-consumer-config.restaurant-approval-consumer-group-id}",
            topics = "${order-service.restaurant-approval-response-topic-name}")
    public void receive(@Payload List<RestaurantApprovalResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {
        log.info("{} number of restaurant approval responses received with keys {}, partitions {} and offsets {}",
                messages.size(),
                keys.toString(),
                partitions.toString(),
                offsets.toString());

        messages.forEach(restaurantApprovalResponseAvroModel -> {
            try {
                if (OrderApprovalStatus.APPROVED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
                    log.info("Processing approved order for order id: {}",
                            restaurantApprovalResponseAvroModel.getOrderId());
                    restaurantApprovalRMessageListener.orderApproved(orderMessagingDataMapper
                            .approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
                } else if (OrderApprovalStatus.REJECTED == restaurantApprovalResponseAvroModel.getOrderApprovalStatus()) {
                    log.info("Processing rejected order for order id: {}, with failure messages: {}",
                            restaurantApprovalResponseAvroModel.getOrderId(),
                            String.join(DomainConstants.FAILURE_MESSAGE_DELIMITER,
                                    restaurantApprovalResponseAvroModel.getFailureMessages()));
                    restaurantApprovalRMessageListener.orderRejected(orderMessagingDataMapper
                            .approvalResponseAvroModelToApprovalResponse(restaurantApprovalResponseAvroModel));
                }
            } catch (OptimisticLockingFailureException e) {
                //NO-OP for optimistic lock. This means another thread finished the work, do not throw error to prevent reading the data from kafka again!
                log.error("Caught optimistic locking exception in RestaurantApprovalResponseKafkaListener for order id: {}",
                        restaurantApprovalResponseAvroModel.getOrderId());
            } catch (OrderNotFoundException e) {
                //NO-OP for OrderNotFoundException
                log.error("No order found for order id: {}", restaurantApprovalResponseAvroModel.getOrderId());
            }
        });
    }
}
