package com.example.Test.employee;

import com.example.Test.exception.BadRequestException;
import com.example.Test.exception.ErrorCode;
import com.example.Test.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;

    public List<EmployeeDto> getEmployees(){

        return this.employeeRepository.getEmployees().stream()
                .map(employee -> modelMapper.map(employee, EmployeeDto.class))
                .collect(Collectors.toList());
    }

    public EmployeeDto getEmployeeById(long id) {
        Employee employee = this.employeeRepository.getEmployeeById(id).orElseThrow(() -> new NotFoundException(ErrorCode.ERR_NOT_FOUND.code, "employee not found"));
        return modelMapper.map(employee, EmployeeDto.class);
    }

    @Transactional
    public void saveEmployee(EmployeeDto employeeDto) {
        this.employeeRepository.saveEmployee(modelMapper.map(employeeDto, Employee.class));
    }

    @Transactional
    public void updateEmployee(EmployeeDto employeeDto) {
        if (this.employeeRepository.updateEmployee(modelMapper.map(employeeDto, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PUT.code, "employee ID not found");
        }
    }

    @Transactional
    public void patchEmployee(EmployeeDto employeeDto) {
        if (this.employeeRepository.patchEmployee(modelMapper.map(employeeDto, Employee.class)) == 0) {
            throw new BadRequestException(ErrorCode.ERR_PATCH.code, "employee ID not found");
        }
    }

    @Transactional
    public void deleteEmployee(Long id) {
        this.getEmployeeById(id);
        this.employeeRepository.deleteEmployee(id);
    }

//    public ResponseModel insertEmployee(EmployeeReq req) {
//        log.info("insertEmployeeReq: {}",req);
//        try{
//            this.employeeRepository.insertEmployee(req);
//
//        }catch (Exception e){
//            return ResponseModel.builder()
//                    .message(e.getMessage())
//                    .build();
//        }
//
//        return ResponseModel.builder()
//                .code("200")
//                .message("Success").build();
//    }
//
//    public ResponseModel deleteEmployee(Long id) throws Exception{
//
//        log.info("deleteEmployeeReq: {}",id);
//        try{
//            this.employeeRepository.deleteEmployee(id);
//
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//
//        return ResponseModel.builder()
//                .code("200")
//                .message("Success").build();
//    }
//
//    public ResponseModel updateEmployee(EmployeeReqUpdate reqUpdate) throws Exception{
//        log.info("updateEmployeeReq: {}",reqUpdate);
//        try{
//            this.employeeRepository.updateEmployee(reqUpdate);
//
//        }catch (Exception e){
//            throw new Exception(e.getMessage());
//        }
//
//        return ResponseModel.builder()
//                .code("200")
//                .message("Success").build();
//    }

}
