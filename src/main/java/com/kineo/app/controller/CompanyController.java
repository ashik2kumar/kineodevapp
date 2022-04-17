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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kineo.app.model.Company;
import com.kineo.app.model.CompanySearchRequest;
import com.kineo.app.repository.CompanyRepository;
import com.kineo.app.repository.EmployeeRepository;

@Controller
@RequestMapping("/company")
public class CompanyController {

	Logger logger = LogManager.getLogger(CompanyController.class);

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@GetMapping("/companies")
	public ResponseEntity<List<Company>> getAllCompanies() {
		logger.debug("Enter getAllCompanies");
		try {
			List<Company> companies = companyRepository.findAll();
			logger.debug("companies " + companies);
			if (companies.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(companies, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{companyId}")
	public ResponseEntity<Company> getCompany(@PathVariable Long companyId) {
		logger.debug("Enter getCompany companyId" + companyId);
		Optional<Company> company = Optional.ofNullable(companyRepository.getOne(companyId));
		logger.debug("company " + company);
		if (company.isPresent()) {
			return new ResponseEntity<>(company.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/{companyId}")
	public ResponseEntity deleteCompany(@PathVariable("companyId") Long companyId) {
		logger.debug("Enter deleteCompany companyId " + companyId);
		try {
			Company company = companyRepository.getOne(companyId);
			logger.debug("company[" + company + "]");
			if (company.getEmployees() == null || company.getEmployees().size() == 0) {
				companyRepository.delete(companyId);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_MODIFIED)
						.body("Cannot delete company id " + companyId + " as employees are still present");
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/save")
	public ResponseEntity saveCompany(@RequestBody Company company) {
		Company cmp = companyRepository.save(company);
		return new ResponseEntity<>(cmp, HttpStatus.OK);
	}

	@PostMapping("/search")
	public ResponseEntity companySearch(@RequestBody CompanySearchRequest companySearchRequest) {
		logger.debug("Enter companySearch id[" + companySearchRequest.getCompanyId() + "]name["
				+ companySearchRequest.getName() + "]");
		List<Company> companies = null;
		if (companySearchRequest.getCompanyId() != null) {
			companies = new ArrayList<>(Arrays.asList(companyRepository.getOne(companySearchRequest.getCompanyId())));
		} else if (companySearchRequest.getName() != null) {
			companies = companyRepository.findByNameOrderByName(companySearchRequest.getName());
		}
		return new ResponseEntity<>(companies, HttpStatus.OK);
	}
}
