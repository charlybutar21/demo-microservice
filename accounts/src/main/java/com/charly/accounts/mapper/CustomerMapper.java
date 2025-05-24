package com.charly.accounts.mapper;

import com.charly.accounts.dto.CustomerDetailDto;
import com.charly.accounts.dto.CustomerDto;
import com.charly.accounts.entity.Customer;

public class CustomerMapper {

    private CustomerMapper() {
        // Private constructor to prevent instantiation
    }
    public static CustomerDto mapToCustomerDto(Customer customer, CustomerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static Customer mapToCustomer(CustomerDto customerDto, Customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDetailDto mapToCustomerDetailDto(Customer customer, CustomerDetailDto customerDetailDto) {
        customerDetailDto.setName(customer.getName());
        customerDetailDto.setEmail(customer.getEmail());
        customerDetailDto.setMobileNumber(customer.getMobileNumber());
        return customerDetailDto;
    }
}
