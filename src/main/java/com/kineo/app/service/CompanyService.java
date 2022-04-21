package com.kineo.app.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kineo.app.exception.CompanyNotFoundException;
import com.kineo.app.exception.EmployeesExistException;
import com.kineo.app.exception.NoDataFoundException;
import com.kineo.app.model.Company;
import com.kineo.app.model.CompanySearchRequest;
import com.kineo.app.repository.CompanyRepository;

@Service
public class CompanyService implements ICompanyService {

	Logger logger = LogManager.getLogger(CompanyService.class);

	@Autowired
	private CompanyRepository companyRepository;

	@Override
	public List<Company> findAll() {
		logger.debug("Enter findAll");
		List<Company> companies = companyRepository.findAll();
		if (companies.isEmpty()) {
			throw new NoDataFoundException();
		}
		logger.debug("Exit findAll");
		return companies;
	}

	@Override
	public Company findById(Long id) {
		logger.debug("Enter findById");
		Optional<Company> company = companyRepository.findById(id);
		if (!company.isPresent()) {
			throw new CompanyNotFoundException(id);
		}
		logger.debug("Exit findById");
		return company.get();
	}

	@Override
	public Company save(Company company) {
		logger.debug("Enter save");
		Company companyData = companyRepository.save(company);
		logger.debug("Exit save");
		return companyData;
	}

	@Override
	public List<Company> searchCompanyByIdOrName(CompanySearchRequest companySearchRequest) {
		logger.debug("Enter searchCompanyByIdOrName");
		List<Company> companies = null;
		if (companySearchRequest.getCompanyId() != null) {
			companies = new ArrayList<>(Arrays.asList(findById(companySearchRequest.getCompanyId())));
		} else if (companySearchRequest.getName() != null) {
			companies = companyRepository.findByNameOrderByName(companySearchRequest.getName());
			//companies = companyRepository.findByNameContainingIgnoreCaseOrderByName(companySearchRequest.getName());
			if (companies == null || companies.isEmpty()) {
				throw new CompanyNotFoundException(companySearchRequest.getName());
			}
		}
		logger.debug("Exit searchCompanyByIdOrName");
		return companies;
	}

	@Override
	public void delete(Long id) {
		logger.debug("Enter delete");
		Optional<Company> company = companyRepository.findById(id);
		if (company.isPresent()) {
			if (company.get().getEmployees() == null || company.get().getEmployees().size() == 0) {
				companyRepository.deleteById(id);
			} else {
				throw new EmployeesExistException(id, company.get().getEmployees().size());
			}
		} else {
			throw new CompanyNotFoundException(id);
		}
		logger.debug("Exit delete");
	}

}
