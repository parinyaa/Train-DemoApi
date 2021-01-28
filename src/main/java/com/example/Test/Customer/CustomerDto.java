package com.example.Test.Customer;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerDto {
    private Long id;
    private String userName;
    private String email;
    private int countInvalid;
    private String lockCustomer;
}
