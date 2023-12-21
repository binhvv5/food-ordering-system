package org.food.ordering.system.domain.valueoject;

import java.util.UUID;

public class OrderId extends BaseId<UUID> {
    public OrderId(UUID value) {
        super(value);
    }
}
