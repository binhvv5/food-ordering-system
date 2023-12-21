package org.food.ordering.system.order.service.dataaccess.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RestaurantEntityId implements Serializable {
    private UUID restaurantId;
    private UUID productID;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RestaurantEntityId that = (RestaurantEntityId) o;
        return Objects.equals(restaurantId, that.restaurantId) && Objects.equals(productID, that.productID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantId, productID);
    }
}
