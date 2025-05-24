package com.charly.accounts.service;

import com.charly.accounts.dto.CustomerDetailDto;

public interface CustomerService {

    CustomerDetailDto getCustomerDetail(String mobileNumber);
}
