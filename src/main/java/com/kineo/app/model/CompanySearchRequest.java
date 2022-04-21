package com.kineo.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class CompanySearchRequest {

	private Long companyId;
	@Pattern(regexp = "^[a-zA-Z '-]+$", message = "Can contain only the following characters: A-Z, a-z, apostrophe ('), hyphen (-) and white space")
	@NotNull(message = "Company name cannot be null")
	private String name;

	public CompanySearchRequest(Long companyId, String name) {
		super();
		this.companyId = companyId;
		this.name = name;
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

}
