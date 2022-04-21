package com.kineo.app.service;

import java.util.List;

import com.kineo.app.model.Company;
import com.kineo.app.model.CompanySearchRequest;

public interface ICompanyService {

	List<Company> findAll();

	Company findById(Long id);

	Company save(Company company);

	void delete(Long id);

	List<Company> searchCompanyByIdOrName(CompanySearchRequest companySearchRequest);
}
