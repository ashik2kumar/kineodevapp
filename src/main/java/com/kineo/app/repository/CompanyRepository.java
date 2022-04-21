package com.kineo.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kineo.app.model.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

	List<Company> findByNameOrderByName(String name);

	List<Company> findByNameContainingIgnoreCaseOrderByName(String name);

}
