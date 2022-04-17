package com.kineo.app.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kineo.app.model.Employee;
import com.kineo.app.model.EmployeeSearchRequest;
import com.kineo.app.repository.CompanyRepository;
import com.kineo.app.repository.EmployeeRepository;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	Logger logger = LogManager.getLogger(EmployeeController.class);

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/employees")
	public ResponseEntity<List<Employee>> getAllEmployees() {
		logger.debug("Enter getAllEmployees");
		try {
			List<Employee> employees = employeeRepository.findAll();
			if (employees.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(employees, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{employeeId}")
	public ResponseEntity<Employee> getEmployee(@PathVariable Long employeeId) {
		logger.debug("Enter getEmployee employeeId" + employeeId);
		Optional<Employee> employee = Optional.ofNullable(employeeRepository.getOne(employeeId));
		logger.debug("employee " + employee);
		if (employee.isPresent()) {
			return new ResponseEntity<>(employee.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{employeeId}")
	public ResponseEntity deleteEmployee(@PathVariable("employeeId") Long employeeId) {
		logger.debug("Enter deleteEmployee employeeId " + employeeId);
		try {
			employeeRepository.delete(employeeId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/save")
	public ResponseEntity saveEmployee(@RequestBody Employee employee) {
		logger.debug("Enter saveEmployee");
		Employee emp = employeeRepository.save(employee);
		return new ResponseEntity<>(emp, HttpStatus.OK);
	}

	@PutMapping("/save/{employeeId}")
	public ResponseEntity updateEmployee(@RequestBody Employee employee, @PathVariable("employeeId") Long employeeId) {
		logger.debug("Enter updateEmployee employeeId " + employeeId);
		Employee emp = employeeRepository.findOne(employeeId);
		emp.setFirstName(employee.getFirstName());
		emp.setLastName(employee.getLastName());
		emp.setCompanyId(employee.getCompanyId());
		return new ResponseEntity<>(employeeRepository.save(emp), HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity employeeSearch(@RequestBody EmployeeSearchRequest employeeSearchRequest) {
		logger.debug("Enter employeeSearch id[" + employeeSearchRequest.getId() + "]firstName["
				+ employeeSearchRequest.getFirstName() + "]lastName[" + employeeSearchRequest.getLastName() + "]");
		List<Employee> employees = null;
		if (employeeSearchRequest.getId() != null) {
			employees = new ArrayList<>(Arrays.asList(employeeRepository.getOne(employeeSearchRequest.getId())));
		} else if (employeeSearchRequest.getFirstName() != null) {
			employees = employeeRepository.findByFirstNameOrderByFirstName(employeeSearchRequest.getFirstName());
		} else if (employeeSearchRequest.getLastName() != null) {
			employees = employeeRepository.findByLastNameOrderByFirstName(employeeSearchRequest.getLastName());
		}
		return new ResponseEntity<>(employees, HttpStatus.OK);
	}
}
