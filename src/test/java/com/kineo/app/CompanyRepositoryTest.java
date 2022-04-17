package com.kineo.app;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.kineo.app.repository.CompanyRepository;
import com.kineo.app.repository.EmployeeRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CompanyRepositoryTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	CompanyRepository companyRepository;

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void should_find_no_companies_if_repository_is_empty() {
		Iterable companies = companyRepository.findAll();
		assertThat(companies).isEmpty();
	}

}
