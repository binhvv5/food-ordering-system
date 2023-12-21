package org.food.ordering.system.order.service.domain;


import lombok.extern.slf4j.Slf4j;
import org.food.ordering.system.domain.DomainConstants;
import org.food.ordering.system.order.service.domain.dto.message.RestaurantApprovalResponse;
import org.food.ordering.system.order.service.domain.ports.input.message.listener.restaurantapproval.RestaurantApprovalResponseMessageListener;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;


@Slf4j
@Validated
@Service
public class RestaurantApprovalResponseMessageListenerImpl implements RestaurantApprovalResponseMessageListener {

    @Override
    public void orderApproved(RestaurantApprovalResponse restaurantApprovalResponse) {
        log.info("Order is approved for order id: {}", restaurantApprovalResponse.getOrderId());
    }

    @Override
    public void orderRejected(RestaurantApprovalResponse restaurantApprovalResponse) {
          log.info("Order Approval Saga rollback operation is completed for order id: {} with failure messages: {}",
                  restaurantApprovalResponse.getOrderId(),
                  String.join(DomainConstants.FAILURE_MESSAGE_DELIMITER, restaurantApprovalResponse.getFailureMessages()));
    }
}
