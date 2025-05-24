package com.charly.accounts.service.client;

import com.charly.accounts.dto.LoanDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("loans")
public interface LoanFeignClient {

    @GetMapping("/api/loans/{mobileNumber}")
    ResponseEntity<LoanDto> getLoan(@PathVariable("mobileNumber") String mobileNumber);

}
