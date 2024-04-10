package com.adt.hrms.service.impl;

import com.adt.hrms.model.Employee;
import com.adt.hrms.repository.EmployeeRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class EmployeeServiceImplTest  {
    private static final int ID = 1;
    private static final String EMAIL = "mock@gmail.com";
    private static final String FIRSTNAME = "Mock";

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    @Mock
    private EmployeeRepo employeeRepo;


//    @Test
//    public void getAllEmps() {
//        Employee employee = givenEmployee();
//        List<Employee> employeeList = Collections.singletonList(employee);
//        when(employeeRepo.findAll()).thenReturn(employeeList);
//        assertEquals(employeeList, employeeService.getAllEmps());
//    }

    @Test
    public void findEmployeeById() {
        Employee employee = givenEmployee();
        when(employeeRepo.findById(ID)).thenReturn(Optional.of(employee));
        assertEquals(employee, employeeService.getEmployeeById(ID));
    }

    @Test
    public void returnNullFindEmployeeById() {
        Employee employee = givenEmployee();
        when(employeeRepo.findById(ID)).thenReturn(Optional.empty());
        assertNull(employeeService.getEmp(ID));
    }

    @Test
    public void saveEmployee() {
        Employee employee = givenEmployee();
        employee.setEmployeeId(0);
        when(employeeRepo.save(employee)).thenReturn(employee);
        assertEquals(employee.getEmployeeId() + " Employee is Saved",employeeService.saveEmp(employee));
    }

    @Test
    public void deleteEmployeeById() {
        Employee employee = givenEmployee();
        when(employeeRepo.findById(employee.getEmployeeId())).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepo).deleteById(ID);
        assertEquals(employee.getEmployeeId()+ " has been Deleted", employeeService.deleteEmpById(ID));
    }

    @Test
    public void returnNullDeleteEmployeeById() {
        Employee employee = givenEmployee();
        when(employeeRepo.findById(employee.getEmployeeId())).thenReturn(Optional.empty());
        doNothing().when(employeeRepo).deleteById(ID);
        assertEquals("Invalid Employe Id :: " +employee.getEmployeeId(), employeeService.deleteEmpById(ID));
    }

    @Test
    public void searchEmployeeByEmail(){
        Employee employee = givenEmployee();
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeRepo.SearchByEmail(EMAIL)).thenReturn(employeeList);
        assertEquals(employeeList, employeeService.SearchByEmail(EMAIL));
    }

    @Test
    public void searchEmployeeByName(){
        Employee employee = givenEmployee();
        List<Employee> employeeList = Collections.singletonList(employee);
        when(employeeRepo.SearchByName(FIRSTNAME)).thenReturn(employeeList);
        assertEquals(employeeList, employeeService.SearchByName(FIRSTNAME));
    }


    private Employee givenEmployee(){
        Employee employee = new Employee();
        employee.setEmployeeId(ID);
        employee.setEmail(EMAIL);
        employee.setFirstName(FIRSTNAME);
        employee.setIsActive(true);
        return employee;
    }
}
