package org.food.ordering.system.order.service.messaging.publisher.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.food.ordering.system.kafka.order.avro.model.PaymentRequestAvroModel;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.function.BiConsumer;

@Slf4j
@Component
public class OrderKafkaMessageHelper {
    public <T> BiConsumer<SendResult<String, T>, Throwable>
    getKafkaCallBack(String topicName, T requestAvroModel, String orderId, String requestAvroModelName) {
        return (result, ex) -> {
            if (ex == null) {
                RecordMetadata metadata = result.getRecordMetadata();
                log.info("Received new metadata. Topic: {}; Partition {}; Offset {}; Timestamp {}, at time {}",
                        metadata.topic(),
                        metadata.partition(),
                        metadata.offset(),
                        metadata.timestamp(),
                        System.nanoTime());
            } else {
                log.error("Error while sending message {} to topic {}", requestAvroModel.toString(), topicName, ex);
            }
        };
    }
}
