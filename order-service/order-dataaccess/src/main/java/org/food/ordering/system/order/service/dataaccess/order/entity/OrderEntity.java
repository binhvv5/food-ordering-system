package org.food.ordering.system.order.service.dataaccess.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.food.ordering.system.domain.valueoject.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    private UUID id;

    private UUID customerId;
    private UUID restaurantId;
    private UUID trackingId;
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
    private String failureMessages;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private OrderAddressEntity address;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItemEntity> items;
}
