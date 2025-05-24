package com.charly.accounts.service.impl;

import com.charly.accounts.dto.AccountDto;
import com.charly.accounts.dto.CardDto;
import com.charly.accounts.dto.CustomerDetailDto;
import com.charly.accounts.dto.LoanDto;
import com.charly.accounts.entity.Account;
import com.charly.accounts.entity.Customer;
import com.charly.accounts.exception.ResourceNotFoundException;
import com.charly.accounts.mapper.AccountMapper;
import com.charly.accounts.mapper.CustomerMapper;
import com.charly.accounts.repository.AccountRepository;
import com.charly.accounts.repository.CustomerRepository;
import com.charly.accounts.service.CustomerService;
import com.charly.accounts.service.client.CardFeignClient;
import com.charly.accounts.service.client.LoanFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;
    private CardFeignClient cardFeignClient;
    private LoanFeignClient loanFeignClient;

    @Override
    public CustomerDetailDto getCustomerDetail(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Account accounts = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        CustomerDetailDto customerDetailDto = CustomerMapper.mapToCustomerDetailDto(customer, new CustomerDetailDto());
        customerDetailDto.setAccountDto(AccountMapper.mapToAccountsDto(accounts, new AccountDto()));

        ResponseEntity<LoanDto> loanDtoResponseEntity = loanFeignClient.getLoan(mobileNumber);
        customerDetailDto.setLoanDto(loanDtoResponseEntity.getBody());

        ResponseEntity<CardDto> cardDtoResponseEntity = cardFeignClient.getCard(mobileNumber);
        customerDetailDto.setCardDto(cardDtoResponseEntity.getBody());

        return customerDetailDto;
    }
}
