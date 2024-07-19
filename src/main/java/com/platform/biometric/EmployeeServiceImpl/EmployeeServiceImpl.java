package com.platform.biometric.EmployeeServiceImpl;

import com.platform.biometric.model.EmployeeDTO;
import com.platform.biometric.model.entity.Employee;
import com.platform.biometric.repository.EmployeeRepository;
import com.platform.biometric.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<EmployeeDTO> addEmployees(List<EmployeeDTO> employeeList) {
        List<Employee> empList = new ArrayList<>();
        Employee employee = null;
        for (EmployeeDTO emp : employeeList) {
            employee = new Employee();
            employee.setContactNo(emp.getContactNo());
            employee.setEmail(emp.getEmail());
            employee.setFirstName(emp.getFirstName());
            employee.setLastName(emp.getLastName());
            employee.setPosition(emp.getPosition());
            employee.setGender(emp.getGender());
            //employee.setDepartment(emp.getDepartment());
            empList.add(employee);
        }
        employeeRepository.saveAll(empList);
        return employeeList;
    }

    @Override
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }
}
