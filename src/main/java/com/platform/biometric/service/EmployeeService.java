package com.platform.biometric.service;


import com.platform.biometric.model.entity.Employee;
import com.platform.biometric.model.EmployeeDTO;

import java.util.List;


public interface EmployeeService {
    List<EmployeeDTO> addEmployees(List<EmployeeDTO> employeeList);

    List<Employee> findAllEmployees();
}
