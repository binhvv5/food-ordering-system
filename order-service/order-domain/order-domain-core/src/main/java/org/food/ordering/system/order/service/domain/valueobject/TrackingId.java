package org.food.ordering.system.order.service.domain.valueobject;

import org.food.ordering.system.domain.valueoject.BaseId;

import java.util.UUID;

public class TrackingId extends BaseId<UUID> {
    public TrackingId(UUID value) {
        super(value);
    }
}
