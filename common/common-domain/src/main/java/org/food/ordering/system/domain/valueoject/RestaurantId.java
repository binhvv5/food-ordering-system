package org.food.ordering.system.domain.valueoject;

import java.util.UUID;

public class RestaurantId extends BaseId<UUID> {
    public RestaurantId(UUID value) {
        super(value);
    }
}
