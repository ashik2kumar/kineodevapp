package com.kineo.app.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kineo.app.model.Company;
import com.kineo.app.model.CompanySearchRequest;
import com.kineo.app.repository.EmployeeRepository;
import com.kineo.app.service.ICompanyService;

@Controller
@RequestMapping("/company")
public class CompanyController {

	Logger logger = LogManager.getLogger(CompanyController.class);

	@Autowired
	private ICompanyService companyService;

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/companies")
	public ResponseEntity<Object> getAllCompanies() {
		logger.debug("Enter getAllCompanies");
		List<Company> companies = companyService.findAll();
		logger.debug("Exit getAllCompanies");
		return new ResponseEntity<>(companies, HttpStatus.OK);
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<Object> getCompany(@PathVariable Long companyId) {
		logger.debug("Enter getCompany companyId" + companyId);
		Company company = companyService.findById(companyId);
		logger.debug("Exit getCompany");
		return new ResponseEntity<>(company, HttpStatus.OK);
	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity<Object> deleteCompany(@PathVariable("companyId") Long companyId) {
		logger.debug("Enter deleteCompany companyId " + companyId);
		companyService.delete(companyId);
		Map<String, Object> body = new LinkedHashMap<>();
		body.put("timestamp", LocalDateTime.now());
		body.put("message", "Company with id " + companyId + " deleted successfully");
		logger.debug("Exit deleteCompany");
		return new ResponseEntity<>(body, HttpStatus.OK);
	}

	@PostMapping("/save")
	public ResponseEntity<Object> saveCompany(@RequestBody @Valid Company company) {
		logger.debug("Enter saveCompany");
		Company result = companyService.save(company);
		logger.debug("Exit saveCompany");
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}

	@PostMapping("/search")
	public ResponseEntity<Object> companySearch(@RequestBody @Valid CompanySearchRequest companySearchRequest) {
		logger.debug("Enter companySearch id[" + companySearchRequest.getCompanyId() + "]name["
				+ companySearchRequest.getName() + "]");
		List<Company> companies = companyService.searchCompanyByIdOrName(companySearchRequest);
		logger.debug("Exit companySearch");
		return new ResponseEntity<>(companies, HttpStatus.OK);
	}
}
