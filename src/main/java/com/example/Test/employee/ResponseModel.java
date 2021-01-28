package com.example.Test.employee;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseModel {
    private String message;
    private String code;
}
