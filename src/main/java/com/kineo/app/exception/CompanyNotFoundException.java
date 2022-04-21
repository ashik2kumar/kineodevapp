package com.kineo.app.exception;

public class CompanyNotFoundException extends RuntimeException {

	public CompanyNotFoundException(Long companyId) {
		super(String.format("Company with Id %d not found", companyId));
	}

	public CompanyNotFoundException(String name) {
		super(String.format("Company with name %s not found", name));
	}
}
