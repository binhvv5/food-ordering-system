package org.food.ordering.system.order.service.domain;

import org.food.ordering.system.order.service.domain.OrderDomainService;
import org.food.ordering.system.order.service.domain.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderBeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService(){
        return new OrderDomainServiceImpl();
    }
}
