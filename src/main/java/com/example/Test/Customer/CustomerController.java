package com.example.Test.Customer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")

@RequiredArgsConstructor
@Slf4j
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/login")
    public ResponseEntity<?> customerLogin(@RequestBody CustomerReqModel customerReqModel) {

        this.customerService.customerLogin(customerReqModel);
        return ResponseEntity.ok("login success");
    }


}
