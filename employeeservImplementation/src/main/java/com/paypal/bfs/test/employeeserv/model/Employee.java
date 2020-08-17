package com.paypal.bfs.test.employeeserv.model;

import javax.persistence.*;

@Entity
@Table
public class Employee {

    @Id
    @Column(updatable = false, nullable = false, unique = true)
    private int id;

    @Column(name = "firstName", nullable = false)
    private String firstName;
    @Column(name = "lastName", nullable = false)
    private String lastName;
    @Column(name = "dateofbirth")
    private String dateOfBirth;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "line1", column = @Column(name = "line1", nullable = false)),
            @AttributeOverride(name = "line2", column = @Column(name = "line2")),
            @AttributeOverride(name = "city", column = @Column(name = "city", nullable = false)),
            @AttributeOverride(name = "state", column = @Column(name = "state", nullable = false)),
            @AttributeOverride(name = "country", column = @Column(name = "country", nullable = false)),
            @AttributeOverride(name = "zipcode", column = @Column(name = "zipcode", nullable = false)),
    })
    private Address address;

    public int getId() { return id; }

    public void setId(int id) {this.id = id;}

    public String getFirstName() { return firstName; }

    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }

    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getDateOfBirth() { return dateOfBirth; }

    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public Address getAddress() { return address; }

    public void setAddress(Address address) { this.address = address; }
}
