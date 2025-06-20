package com.hexaware.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.assetmanagement.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	Employee findByUserName(String userName);

}