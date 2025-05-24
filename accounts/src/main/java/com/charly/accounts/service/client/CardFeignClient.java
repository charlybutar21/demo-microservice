package com.charly.accounts.service.client;

import com.charly.accounts.dto.CardDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("cards")
public interface CardFeignClient {

    @GetMapping(value = "/api/cards/{mobileNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<CardDto> getCard(@PathVariable("mobileNumber") String mobileNumber);
}
