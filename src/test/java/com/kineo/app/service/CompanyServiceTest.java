package com.kineo.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kineo.app.exception.NoDataFoundException;
import com.kineo.app.model.Company;
import com.kineo.app.model.Employee;
import com.kineo.app.repository.CompanyRepository;

@ExtendWith(MockitoExtension.class)
public class CompanyServiceTest {

	@InjectMocks
	CompanyService companyService;

	@Mock
	CompanyRepository companyRepository;

	private Employee employee;
	private Company company;
	private List<Company> companies;

	@BeforeEach
	public void initializeData() {
		employee = new Employee(1L, "Ashik", "Kumar", 1L);
		List<Employee> employees = Arrays.asList(employee);
		company = new Company(1L, "ABC", employees);
		companies = Arrays.asList(company);
	}

	@AfterEach
	public void clearData() {
		employee = null;
		company = null;
		companies = null;
	}

	@Test
	public void findAllTest() {
		when(companyRepository.findAll()).thenReturn(companies);
		List<Company> result = companyService.findAll();
		assertEquals(1, result.size());
		verify(companyRepository, times(1)).findAll();
	}

	@Test()
	public void findAllEmptyTest() {
		List<Company> companies = new ArrayList<>();
		when(companyRepository.findAll()).thenReturn(companies);
		assertThrows(NoDataFoundException.class, () -> companyService.findAll());
	}
}
