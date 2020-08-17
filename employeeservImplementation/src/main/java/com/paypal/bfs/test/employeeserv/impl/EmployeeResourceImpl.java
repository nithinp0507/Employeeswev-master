package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.constants.StringConstants;
import com.paypal.bfs.test.employeeserv.service.EmployeeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;

import static com.paypal.bfs.test.employeeserv.constants.StringConstants.*;

/**
 * Implementation class for {@link EmployeeResource} .
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private EmployeeServiceImpl employeeService;

    @Autowired
    public EmployeeResourceImpl(EmployeeServiceImpl employeeService) {
       this.employeeService = employeeService;
    }

    /**
     * Retrieves {@link com.paypal.bfs.test.employeeserv.model.Employee} for given {@code String}
     * <ul>
     *     <li>Returns {@link HttpStatus#NO_CONTENT} when requested resource not found</li>
     *     <li>Returns {@link HttpStatus#OK} when requested resource is found</li>
     * </ul>
     * @param id employee id.
     * @return
     */
    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

        Employee employee = employeeService.getEmployeeById(id);
        if(employee == null) {
            MultiValueMap<String,String> map = new HttpHeaders();
            map.add(EMPLOYEE_DOES_NOT_EXIST,"Requested employee does not exist");
            return new ResponseEntity<Employee>(map, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    /**
     * Below method creates {@link com.paypal.bfs.test.employeeserv.model.Employee} based on request received
     * creates resources if validation is successful and if there is no duplication
     * @param employee
     * @return
     */
    @Override
    public ResponseEntity<Employee> createEmployee(Employee employee) {
        Employee persistedEmployee = employeeService.saveEmployee(employee);

        if(!persistedEmployee.getAdditionalProperties().isEmpty()
            && persistedEmployee.getAdditionalProperties().get(FIELDS_MISSING) != null) {
            return new ResponseEntity<>(persistedEmployee, HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if(!persistedEmployee.getAdditionalProperties().isEmpty()
                && persistedEmployee.getAdditionalProperties().get(DUPLICATE) != null) {
            return new ResponseEntity<>(persistedEmployee, HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(persistedEmployee, HttpStatus.OK);
    }
}
