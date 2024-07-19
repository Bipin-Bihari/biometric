package com.platform.biometric.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    @NotEmpty
    @NotNull(message = "First Name can not be null")
    private String firstName;
    @NotEmpty
    @NotNull(message = "First Name can not be null")
    private String lastName;
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE)
    private String email;
    @Size(min = 10, max = 12, message = "Phone number must be greater then 9 digit")
    private String contactNo;
    private String gender;
    private String department;
    private String position;
}
