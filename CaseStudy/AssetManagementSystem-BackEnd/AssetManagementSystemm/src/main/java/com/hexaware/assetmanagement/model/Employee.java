package com.hexaware.assetmanagement.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="Employee")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    @Id
    @Column(name="employee_id")
    private int employeeId;
    
    @Column(name="employee_name", nullable=false)
    private String employeeName;
    
    @Column(name="user_name", unique=true, nullable=false)
    private String userName;
    
    @Column(name="password", nullable=false)
    private String password;
    
    @Column(name="email", unique=true, nullable=false)
    private String email;
    
    @Column(name="contact_number")
    private String contactNumber;
    
    @Column(name="address")
    private String address;
    
    @Column(name="role", nullable=false)
    private String role;
    
    @Column(name="gender")
    private String gender;
}