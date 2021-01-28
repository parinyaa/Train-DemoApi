package com.example.Test.Customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String userName;
    private String email;
    private String password;
    private int countInvalid;
    private String lockCustomer;

}
