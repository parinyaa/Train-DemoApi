package com.example.Test.Customer;


import com.example.Test.exception.ErrorCode;
import com.example.Test.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;

    public void customerLogin(CustomerReqModel customerReqModel) {

        int counts = 0;
        Customer customerByUserName = new Customer();
        Customer customer = new Customer();

        try {
            customerByUserName = this.customerRepository.reqCustomerByUserName(customerReqModel)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "user not found"));

            customer = this.customerRepository.inquiryCustomer(customerReqModel)
                    .orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "Invalid password"));

            if (customerByUserName.getCountInvalid() > 0 && customerByUserName.getCountInvalid() < 3 && customerByUserName
                    .getUserName().equals(customer.getUserName())) {
                this.customerRepository.UpdateCountCustomer(customerByUserName.getId(), counts);
            }
        } catch (NotFoundException e) {
            if (e.getMessage().equals("Invalid password")) {
                counts = customerByUserName.getCountInvalid() + 1;
                if (counts == 3) {
                    this.customerRepository.lockCustomer(customerByUserName.getId());
                    throw new NotFoundException("lock customer");
                }
                this.customerRepository.UpdateCountCustomer(customerByUserName.getId(), counts);
            }
            log.info("count : {}", counts);

            throw new NotFoundException(e.getMessage());
        }
    }
}
