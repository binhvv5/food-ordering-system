package org.food.ordering.system.domain.valueoject;

import java.util.UUID;

public class CustomerId extends BaseId<UUID> {
    public CustomerId(UUID value) {
        super(value);
    }
}
