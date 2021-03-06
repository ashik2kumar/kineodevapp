package com.kineo.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "employee")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "employee_id")
	private Long employeeId;

	@Pattern(regexp = "^[a-zA-Z '-]+$", message = "Can contain only the following characters: A-Z, a-z, apostrophe ('), hyphen (-) and white space")
	private String firstName;

	@Pattern(regexp = "^[a-zA-Z '-]+$", message = "Can contain only the following characters: A-Z, a-z, apostrophe ('), hyphen (-) and white space")
	private String lastName;
	@Column(name = "company_id")
	private Long companyId;

	public Employee() {
	}

	public Employee(Long employeeId,
			@Pattern(regexp = "^[a-zA-Z '-]+$", message = "Can contain only the following characters: A-Z, a-z, apostrophe ('), hyphen (-) and white space") String firstName,
			@Pattern(regexp = "^[a-zA-Z '-]+$", message = "Can contain only the following characters: A-Z, a-z, apostrophe ('), hyphen (-) and white space") String lastName,
			Long companyId) {
		super();
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.companyId = companyId;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	@Override
	public String toString() {
		return "Employee [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", companyId=" + companyId + "]";
	}

}
