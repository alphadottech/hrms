package com.adt.hrms.service;

import com.adt.hrms.model.EmpPayrollDetails;

public interface EmpPayrollDetailsService {
    public String updateEmpPayrollDetailsByUser(EmpPayrollDetails emp);
    public String updateEmpPayroll(EmpPayrollDetails emp);

    public EmpPayrollDetails getEmpPayrollDetails(Integer empId);
}
