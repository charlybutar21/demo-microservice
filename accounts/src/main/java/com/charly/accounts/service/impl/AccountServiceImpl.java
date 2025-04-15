package com.charly.accounts.service.impl;

import com.charly.accounts.dto.AccountDto;
import com.charly.accounts.dto.CustomerDto;
import com.charly.accounts.entity.Account;
import com.charly.accounts.entity.Customer;
import com.charly.accounts.exception.CustomerAlreadyExistsException;
import com.charly.accounts.exception.ResourceNotFoundException;
import com.charly.accounts.mapper.AccountMapper;
import com.charly.accounts.mapper.CustomerMapper;
import com.charly.accounts.repository.AccountRepository;
import com.charly.accounts.repository.CustomerRepository;
import com.charly.accounts.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static com.charly.accounts.constant.AccountConstant.ADDRESS;
import static com.charly.accounts.constant.AccountConstant.SAVINGS;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());
        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
        }
        customer.setCreatedAt(LocalDateTime.now());
        customer.setCreatedBy("Anonymous");
        Customer savedCustomer = customerRepository.save(customer);

        createNewAccount(savedCustomer);
    }

    @Override
    public CustomerDto getAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        Account account = accountRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(() -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString()));
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountDto(AccountMapper.mapToAccountsDto(account, new AccountDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdated = false;
        AccountDto accountDto = customerDto.getAccountDto();
        if (accountDto != null) {
            Account accounts = accountRepository.findById(accountDto.getAccountNumber()).orElseThrow(() -> new ResourceNotFoundException("Account", "AccountNumber", accountDto.getAccountNumber().toString()));
            AccountMapper.mapToAccounts(accountDto, accounts);
            accounts = accountRepository.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerID", customerId.toString()));
            CustomerMapper.mapToCustomer(customerDto, customer);
            customerRepository.save(customer);
            isUpdated = true;
        }
        return isUpdated;
    }

    @Override
    @Transactional
    public boolean deleteAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber));
        accountRepository.deleteByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());
        return true;
    }

    private void createNewAccount(Customer customer) {
        Account newAccount = new Account();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);
        newAccount.setAccountType(SAVINGS);
        newAccount.setBranchAddress(ADDRESS);
        newAccount.setCreatedAt(LocalDateTime.now());
        newAccount.setCreatedBy("Anonymous");

        accountRepository.save(newAccount);
    }

}
