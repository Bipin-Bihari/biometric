package com.platform.biometric.model;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EmployeeInAndOutTrace {
    @NotNull(message = "Employee Id cannot be null")
    private int employeeId;
    private String status;
}
