package org.food.ordering.system;

import org.food.ordering.system.restaurant.service.domain.RestaurantDomainService;
import org.food.ordering.system.restaurant.service.domain.RestaurantDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public RestaurantDomainService restaurantDomainService() {
        return new RestaurantDomainServiceImpl();
    }

}
