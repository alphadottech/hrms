package com.adt.hrms.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeUpdateByAdminDTO {
    int employeeId;
    boolean isActive;
}
