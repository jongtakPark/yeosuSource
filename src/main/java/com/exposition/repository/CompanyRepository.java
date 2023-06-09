package com.exposition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exposition.entity.Company;

public interface CompanyRepository extends JpaRepository<Company, Long>, CompanyRepositoryCustom {
	
	Company findByCom(String com);
	boolean existsByCom(String com);
	Company findByEmail(String email);
	Company findByComAndEmail(String com, String email);

}
