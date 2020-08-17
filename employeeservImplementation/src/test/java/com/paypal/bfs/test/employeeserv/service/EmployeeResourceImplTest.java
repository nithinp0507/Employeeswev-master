package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.impl.EmployeeResourceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.constants.StringConstants.DUPLICATE;
import static com.paypal.bfs.test.employeeserv.constants.StringConstants.FIELDS_MISSING;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
public class EmployeeResourceImplTest {

    @InjectMocks
    EmployeeResourceImpl employeeResource;

    @Mock
    EmployeeServiceImpl employeeService;

    @Test
    public void getEmployeeForSuccessResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("id",String.valueOf(1));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Employee emp = new Employee();
        emp.setId(1);
        Address address = new Address();
        emp.setAddress(address);

        when(employeeService.getEmployeeById(any(String.class))).thenReturn(emp);
        ResponseEntity<Employee> persistedEmployee = employeeResource.employeeGetById(request.getParameter("id"));

        Assert.assertTrue("Return Response Expected",
                HttpStatus.OK.value() == persistedEmployee.getStatusCodeValue());
    }

    @Test
    public void getEmployeeForNoResource() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Employee emp = new Employee();
        emp.setId(1);
        Address address = new Address();
        emp.setAddress(address);

        when(employeeService.getEmployeeById(any(String.class))).thenReturn(emp);
        ResponseEntity<Employee> persistedEmployee = employeeResource.employeeGetById(request.getParameter("id"));

        Assert.assertTrue("Return Response Expected",
                HttpStatus.NO_CONTENT.value() == persistedEmployee.getStatusCodeValue());
    }

    @Test
    public void createEmployeeForOKResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("emp",String.valueOf(new Employee()));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Employee emp = new Employee();
        emp.setId(1);
        Address address = new Address();
        emp.setAddress(address);

        when(employeeService.saveEmployee(any())).thenReturn(emp);
        ResponseEntity<Employee> persistedEmployee = employeeResource.createEmployee(emp);
        Assert.assertTrue("Return Response Expected",
                HttpStatus.OK.value() == persistedEmployee.getStatusCodeValue());
    }

    @Test
    public void createEmployeeForUnProcessableResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("emp",String.valueOf(new Employee()));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Employee emp = new Employee();
        emp.setId(1);
        emp.setAdditionalProperty(FIELDS_MISSING,"Mandatory fields missing");
        Address address = new Address();
        emp.setAddress(address);

        when(employeeService.saveEmployee(any())).thenReturn(emp);
        ResponseEntity<Employee> persistedEmployee = employeeResource.createEmployee(emp);
        Assert.assertTrue("Return Response Expected",
                HttpStatus.UNPROCESSABLE_ENTITY.value() == persistedEmployee.getStatusCodeValue());
    }

    @Test
    public void createEmployeeForConflictResponse() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addParameter("emp",String.valueOf(new Employee()));
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));

        Employee emp = new Employee();
        emp.setId(1);
        emp.setAdditionalProperty(DUPLICATE,"Entity already exists");
        Address address = new Address();
        emp.setAddress(address);

        when(employeeService.saveEmployee(any())).thenReturn(emp);
        ResponseEntity<Employee> persistedEmployee = employeeResource.createEmployee(emp);
        Assert.assertTrue("Return Response Expected",
                HttpStatus.CONFLICT.value() == persistedEmployee.getStatusCodeValue());
    }
}
