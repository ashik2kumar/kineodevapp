package com.kineo.app.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kineo.app.model.Company;
import com.kineo.app.model.CompanySearchRequest;
import com.kineo.app.model.Employee;
import com.kineo.app.repository.EmployeeRepository;
import com.kineo.app.service.ICompanyService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CompanyController.class)
public class CompanyControllerTest {

	@MockBean
	ICompanyService companyService;

	@MockBean
	EmployeeRepository employeeRepository;

	@Autowired
	MockMvc mockMvc;

	private Employee employee;
	private Company company;

	@BeforeEach
	public void initializeData() {
		employee = new Employee(1L, "Ashik", "Kumar", 1L);
		List<Employee> employees = Arrays.asList(employee);
		company = new Company(1L, "ABC", employees);
	}

	@AfterEach
	public void clearData() {
		employee = null;
		company = null;
	}

	@Test
	public void getAllCompaniesTest() throws Exception {
		List<Company> companies = Arrays.asList(company);
		Mockito.when(companyService.findAll()).thenReturn(companies);
		mockMvc.perform(get("/company/companies")).andExpect(status().isOk())
				.andExpect(jsonPath("$", Matchers.hasSize(1))).andExpect(jsonPath("$[0].name", Matchers.is("ABC")));
	}

	@Test
	public void getCompanyTest() throws Exception {
		Mockito.when(companyService.findById(1L)).thenReturn(company);
		mockMvc.perform(get("/company/{companyId}", 1L)).andExpect(status().isOk())
				.andExpect(jsonPath("name", Matchers.is("ABC")));
	}

	@Test
	public void deleteCompanyTest() throws Exception {
		Mockito.when(companyService.findById(1L)).thenReturn(company);
		mockMvc.perform(delete("/company/{companyId}", 1L)).andExpect(status().isOk());
	}

	@Test
	public void saveCompanyTest() throws Exception {
		Mockito.when(companyService.save(company)).thenReturn(company);
		mockMvc.perform(post("/company/save").content(asJsonString(company)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();
	}

	@Test
	public void companySearchTest() throws Exception {
		CompanySearchRequest companySearchRequest = new CompanySearchRequest(null, "ABC");
		List<Company> companies = Arrays.asList(company);
		Mockito.when(companyService.searchCompanyByIdOrName(companySearchRequest)).thenReturn(companies);
		mockMvc.perform(post("/company/search").content(asJsonString(companySearchRequest))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}

	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
