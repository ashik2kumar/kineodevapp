package com.kineo.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "company")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "company_id")
	private Long companyId;

	@NonNull
	@Pattern(regexp = "^[a-zA-Z '-]+$", message = "Can contain only the following characters: A-Z, a-z, apostrophe ('), hyphen (-) and white space")
	private String name;

	@OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	@OrderBy(value = "firstName")
	private List<Employee> employees;

	public Company() {
	}

	public Company(Long companyId, String name, List<Employee> employees) {
		super();
		this.companyId = companyId;
		this.name = name;
		this.employees = employees;
	}

	public Long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Employee> employees) {
		this.employees = employees;
	}

	@Override
	public String toString() {
		return "Company [companyId=" + companyId + ", name=" + name + ", employees=" + employees + "]";
	}

}
