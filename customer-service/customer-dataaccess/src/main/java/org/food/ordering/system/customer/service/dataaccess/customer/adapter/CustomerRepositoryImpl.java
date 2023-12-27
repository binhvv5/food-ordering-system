package org.food.ordering.system.customer.service.dataaccess.customer.adapter;

import lombok.RequiredArgsConstructor;
import org.food.ordering.system.customer.service.dataaccess.customer.mapper.CustomerDataAccessMapper;
import org.food.ordering.system.customer.service.dataaccess.customer.repository.CustomerJpaRepository;
import org.food.ordering.system.customer.service.domain.entity.Customer;
import org.food.ordering.system.customer.service.domain.ports.output.repository.CustomerRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final CustomerJpaRepository customerJpaRepository;

    private final CustomerDataAccessMapper customerDataAccessMapper;


    @Override
    public Customer createCustomer(Customer customer) {
        return customerDataAccessMapper.customerEntityToCustomer(
                customerJpaRepository.save(customerDataAccessMapper.customerToCustomerEntity(customer)));
    }
}
