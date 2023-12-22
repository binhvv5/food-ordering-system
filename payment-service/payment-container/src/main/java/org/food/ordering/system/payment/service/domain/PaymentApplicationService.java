package org.food.ordering.system.payment.service.domain;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"org.food.ordering.system.payment.service.dataaccess", "org.food.ordering.system.dataaccess"})
@EnableJpaRepositories(basePackages = {"org.food.ordering.system.payment.service.dataaccess", "org.food.ordering.system.dataaccess"})
@SpringBootApplication(scanBasePackages = "org.food.ordering.system")
public class PaymentApplicationService {

    public static void main(String[] args) {
        SpringApplication.run(PaymentApplicationService.class, args);
    }
}
