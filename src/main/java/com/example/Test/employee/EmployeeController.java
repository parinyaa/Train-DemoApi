package com.example.Test.employee;

import com.example.Test.exception.ErrorCode;
import com.example.Test.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public ResponseEntity<?> getEmployees() {
        return ResponseEntity.ok(this.employeeService.getEmployees());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getIdEmployee(@PathVariable long id) {
        log.info("getEmployee id: {}", id);
        return ResponseEntity.ok(this.employeeService.getEmployeeById(id));
    }

    @PostMapping
    public ResponseEntity<?> saveEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("saveEmployee employeeDto: {}", employeeDto);

        this.employeeService.saveEmployee(employeeDto);
        return ResponseEntity.created(null).build();
    }

    @PutMapping
    public ResponseEntity<?> updateEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("updateEmployee employeeDto: {}", employeeDto);

        this.employeeService.updateEmployee(employeeDto);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<?> patchEmployee(@RequestBody EmployeeDto employeeDto) {
        log.info("patchEmployee employeeDto: {}", employeeDto);

        this.employeeService.patchEmployee(employeeDto);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id)  {

        this.employeeService.deleteEmployee(id);
        return  ResponseEntity.noContent().build();
    }

//    @PostMapping("/insert")
//    public ResponseEntity<ResponseModel> insertEmployee(@RequestBody EmployeeReq req){
//
//        return ResponseEntity.ok(this.employeeService.insertEmployee(req));
//    }
//
//    @GetMapping("/delete/{id}")
//    public ResponseEntity<ResponseModel> deleteEmployee(@PathVariable Long id) throws Exception{
//        log.info("id: {}",id);
//        return ResponseEntity.ok(this.employeeService.deleteEmployee(id));
//    }
//
//    @PostMapping("/update")
//    public ResponseEntity<ResponseModel> updateEmployee(@RequestBody EmployeeReqUpdate reqUpdate) throws Exception{
//
//        return ResponseEntity.ok(this.employeeService.updateEmployee(reqUpdate));
//    }




}
