package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.constants.StringConstants;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.constants.StringConstants.DUPLICATE;
import static com.paypal.bfs.test.employeeserv.constants.StringConstants.FIELDS_MISSING;

/**
 * Interacts with {@link EmployeeRepository}
 * to save/fetch {@link com.paypal.bfs.test.employeeserv.model.Employee}
 */
@Service
public class EmployeeServiceImpl {

    private EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Get {@link Employee} based on {@Code String}
     * @param id
     * @return
     */
    public Employee getEmployeeById(String id) {
        Optional<com.paypal.bfs.test.employeeserv.model.Employee> employee = employeeRepository.findById(Integer.parseInt(id));
        if (employee.isPresent()) {
             Employee emp = mapEmployeeEntityToModel(employee);
             Address address = mapEmployeeAddressToModel(employee);
             emp.setAddress(address);
            return emp;
        }
        return null;
    }

    /**
     * Save {@link com.paypal.bfs.test.employeeserv.model.Employee} and returns
     * {@link org.springframework.http.HttpStatus} based on response
     *
     * @param employee
     * @return
     */
    public Employee saveEmployee(Employee employee) {
        try {
            if (getEmployeeById(employee.getId().toString()) != null) {
                employee.setAdditionalProperty(DUPLICATE,
                        "Entity Already exists");
                return employee;
            }
            com.paypal.bfs.test.employeeserv.model.Employee employee1 = mapModelToEmployeeEntity(employee);
            com.paypal.bfs.test.employeeserv.model.Address address1 = mapModelToAddressEntity(employee);
            employee1.setAddress(address1);
            employeeRepository.save(employee1);
        } catch (org.springframework.dao.DataIntegrityViolationException exception) {
            employee.setAdditionalProperty(FIELDS_MISSING,
                    "One/More mandatory properties are missing");
        }
        return employee;
    }

    public Employee mapEmployeeEntityToModel(Optional<com.paypal.bfs.test.employeeserv.model.Employee> employee) {

        Employee emp = new Employee();
        emp.setId(employee.get().getId());
        emp.setFirstName(employee.get().getFirstName());
        emp.setLastName(employee.get().getLastName());
        emp.setDateOfBirth(employee.get().getDateOfBirth());

        return emp;
    }

    public Address mapEmployeeAddressToModel(Optional<com.paypal.bfs.test.employeeserv.model.Employee> employee) {

        Address address = new Address();
        address.setLine1(employee.get().getAddress().getLine1());
        address.setLine2(employee.get().getAddress().getLine2());
        address.setCity(employee.get().getAddress().getCity());
        address.setState(employee.get().getAddress().getState());
        address.setCountry(employee.get().getAddress().getCountry());
        address.setZipcode(employee.get().getAddress().getZipcode());

        return address;
    }

    public com.paypal.bfs.test.employeeserv.model.Employee mapModelToEmployeeEntity(Employee employee) {

        com.paypal.bfs.test.employeeserv.model.Employee emp = new com.paypal.bfs.test.employeeserv.model.Employee();
        emp.setId(employee.getId());
        emp.setFirstName(employee.getFirstName());
        emp.setLastName(employee.getLastName());
        emp.setDateOfBirth(employee.getDateOfBirth());
        return emp;
    }

    public com.paypal.bfs.test.employeeserv.model.Address mapModelToAddressEntity(Employee employee) {
        com.paypal.bfs.test.employeeserv.model.Address address = new com.paypal.bfs.test.employeeserv.model.Address();
        Address address1 = employee.getAddress();
        address.setLine1(address1.getLine1());
        address.setLine2(address1.getLine2());
        address.setCity(address1.getCity());
        address.setState(address1.getState());
        address.setCountry(address1.getCountry());
        address.setZipcode(address1.getZipcode());
        return address;
    }
}
