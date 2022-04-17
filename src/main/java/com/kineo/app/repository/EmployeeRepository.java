package com.kineo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kineo.app.model.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByFirstNameOrderByFirstName(String firstName);

	List<Employee> findByLastNameOrderByFirstName(String lastName);

}
