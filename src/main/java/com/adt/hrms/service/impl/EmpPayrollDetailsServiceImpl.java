package com.adt.hrms.service.impl;

import com.adt.hrms.model.EmpPayrollDetails;
import com.adt.hrms.model.Employee;
import com.adt.hrms.repository.EmpPayrollDetailsRepo;
import com.adt.hrms.repository.EmployeeRepo;
import com.adt.hrms.service.EmpPayrollDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmpPayrollDetailsServiceImpl implements EmpPayrollDetailsService {

    @Autowired
    private EmployeeRepo employeeRepo;

    @Autowired
    private EmpPayrollDetailsRepo empPayrollDetailsRepo;

    @Override
    public String updateEmpPayrollDetailsByUser(EmpPayrollDetails empPayroll) {
        Optional<Employee> existEmployee = employeeRepo.findById(empPayroll.getEmpId());
        if (existEmployee.isPresent()) {
            Optional<EmpPayrollDetails> opt = empPayrollDetailsRepo.findByEmployeeId(empPayroll.getEmpId());
            if (opt.isPresent()) {
                if (empPayroll.getAccountNumber() != null) {
                    opt.get().setAccountNumber(empPayroll.getAccountNumber());
                }
                if (empPayroll.getBankName() != null) {
                    opt.get().setBankName(empPayroll.getBankName());
                }
                if (empPayroll.getIfscCode() != null) {
                    opt.get().setIfscCode(empPayroll.getIfscCode());
                }
                empPayrollDetailsRepo.save(opt.get());
                return " Employee " + opt.get().getEmpId() + " with payroll Id " + opt.get().getId() + " Updated Successfully";
            } else {
                EmpPayrollDetails newEmpPayrollDetails = new EmpPayrollDetails();
                newEmpPayrollDetails.setAccountNumber(empPayroll.getAccountNumber());
                newEmpPayrollDetails.setBankName(empPayroll.getBankName());
                newEmpPayrollDetails.setIfscCode(empPayroll.getIfscCode());
                newEmpPayrollDetails.setEmpId(empPayroll.getEmpId());
                empPayrollDetailsRepo.save(newEmpPayrollDetails);
                return "New Employee (ID: " + newEmpPayrollDetails.getEmpId() + ") Added Successfully";
            }
        } else {
            return "Employee" + empPayroll.getEmpId() + "does not exist";
        }

    }


    @Override
    public String updateEmpPayroll(EmpPayrollDetails empPayroll) {
        Optional<Employee> existEmployee = employeeRepo.findById(empPayroll.getEmpId());
        if (existEmployee.isPresent()) {
            Optional<EmpPayrollDetails> opt = empPayrollDetailsRepo.findByEmployeeId(empPayroll.getEmpId());
            if (opt.isPresent()) {
                if (empPayroll.getDesignation() != null) {
                    opt.get().setDesignation(empPayroll.getDesignation());
                }
                if (empPayroll.getSalary() != null) {
                    opt.get().setSalary(empPayroll.getSalary());
                }
                if (empPayroll.getJoinDate() != null) {
                    opt.get().setJoinDate(empPayroll.getJoinDate());
                }
                empPayrollDetailsRepo.save(opt.get());
                return " Employee " + opt.get().getEmpId() + " with payroll Id " + opt.get().getId() + " Updated Successfully";
            } else {
                EmpPayrollDetails newEmpPayrollDetails = new EmpPayrollDetails();
                newEmpPayrollDetails.setDesignation(empPayroll.getDesignation());
                newEmpPayrollDetails.setSalary(empPayroll.getSalary());
                newEmpPayrollDetails.setJoinDate(empPayroll.getJoinDate());
                newEmpPayrollDetails.setEmpId(empPayroll.getEmpId());
                empPayrollDetailsRepo.save(newEmpPayrollDetails);
                return "New Employee (ID: " + newEmpPayrollDetails.getEmpId() + ") Added Successfully";
            }
        } else {
            return "Employee" + empPayroll.getEmpId() + "does not exist";
        }

    }


    @Override
    public EmpPayrollDetails getEmpPayrollDetails(Integer empId) {
        Optional<EmpPayrollDetails> opt = empPayrollDetailsRepo.findByEmployeeId(empId);
        return opt.orElse(null);
    }
}
