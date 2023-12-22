package org.food.ordering.system.payment.service.domain.ports.output.message.publisher;

import org.food.ordering.system.domain.event.publisher.DomainEventPublisher;
import org.food.ordering.system.payment.service.domain.event.PaymentFailedEvent;

public interface PaymentFailMessagePublisher extends DomainEventPublisher<PaymentFailedEvent> {
}
