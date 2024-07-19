package com.platform.biometric.controller;

import com.platform.biometric.model.entity.Employee;
import com.platform.biometric.model.EmployeeDTO;
import com.platform.biometric.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    EmployeeService employeeService;

    @PostMapping()
    public List<EmployeeDTO> addEmployees(@Valid @RequestBody List<EmployeeDTO> employeeList) {
        return employeeService.addEmployees(employeeList);
    }

    @GetMapping(value= "/all")
    public List<Employee> getAllEmployees() {

        return employeeService.findAllEmployees();
    }
}
