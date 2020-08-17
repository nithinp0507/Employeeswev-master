package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.model.Address;
import com.paypal.bfs.test.employeeserv.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.Optional;

@RunWith(SpringRunner.class)
public class EmployeeServiceImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Test
    public void testGetEmployeeById() {
        Employee emp = new Employee();
        emp.setAddress(new Address());
        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.of(emp));
        Assert.notNull(employeeService.getEmployeeById("1"));
    }

    @Test
    public void testGetEmployeeByIdForNull() {
        Mockito.when(employeeRepository.findById(Mockito.any())).thenReturn(Optional.empty());
        Assert.isNull(employeeService.getEmployeeById("1"));
    }
}
