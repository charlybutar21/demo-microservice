package com.charly.accounts.controller;

import com.charly.accounts.dto.CustomerDetailDto;
import com.charly.accounts.dto.ErrorResponseDto;
import com.charly.accounts.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Pattern;
import org.apache.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "REST API for Customers in EazyBank", description = "REST APIs in EazyBank to FETCH customer details")
@RestController
@RequestMapping(path = "/api/customers", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class CustomerController {

    private final CustomerService customersService;

    public CustomerController(CustomerService iCustomersService){
        this.customersService = iCustomersService;
    }

    @Operation(summary = "Fetch Customer Details REST API", description = "REST API to fetch Customer details based on a mobile number")
    @ApiResponses({@ApiResponse(responseCode = "200", description = "HTTP Status OK"), @ApiResponse(responseCode = "500", description = "HTTP Status Internal Server Error", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))})
    @GetMapping("/{mobileNumber}")
    public ResponseEntity<CustomerDetailDto> getCustomerDetail(@PathVariable("mobileNumber") @Pattern(regexp = "(^$|[0-9]{10,13})", message = "Mobile number must be 10 to 13 digits") String mobileNumber) {
        CustomerDetailDto customerDetailsDto = customersService.getCustomerDetail(mobileNumber);
        return ResponseEntity.status(HttpStatus.SC_OK).body(customerDetailsDto);

    }


}
