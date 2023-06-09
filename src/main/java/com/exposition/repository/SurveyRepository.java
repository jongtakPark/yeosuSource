package com.exposition.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.exposition.entity.Member;
import com.exposition.entity.Survey;

public interface SurveyRepository extends JpaRepository<Survey, Long> {

	Survey findByMember(Member member);
}
