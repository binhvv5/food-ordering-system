package org.food.ordering.system.order.service.domain.valueobject;

import org.food.ordering.system.domain.valueoject.BaseId;

import java.util.UUID;

public class OrderItemId extends BaseId<Long> {
    public OrderItemId(Long value) {
        super(value);
    }
}