package com.exposition.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.exposition.dto.CompanyFormDto;
import com.exposition.dto.QCompanyFormDto;
import com.exposition.entity.QCompany;
import com.exposition.entity.QReservation;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;

public class CompanyRepositoryCustomImpl implements CompanyRepositoryCustom {

private JPAQueryFactory queryFactory;
	
	public CompanyRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	//업체등록 신청한 기업 조회
	@Override
	public Page<CompanyFormDto> getApprovalCom(CompanyFormDto companyFormDto, Pageable pageable){
		QCompany company = QCompany.company;
		QReservation reservation = QReservation.reservation;
		QueryResults<CompanyFormDto> result = queryFactory
				.select(new QCompanyFormDto(company.com, company.name, company.email , reservation.location, reservation.startDay, reservation.endDay))
				.from(reservation)
				.join(reservation.company, company)
				.where(company.approval.eq("예약신청중"))
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetchResults();
		
		List<CompanyFormDto> list = result.getResults();
		Long total = result.getTotal();
		return new PageImpl<>(list, pageable, total);
	}
	
	//업체등록 신청한 기업 승인
	@Override
	public void updateApp(String com) {
		QCompany company = QCompany.company;
		
		queryFactory.update(company)
		.set(company.approval, "예약완료")
		.where(company.com.eq(com))
		.execute();
	}
	
	//기업 회원 모두 조회(예약 테이블과 join)
	@Override
	public Page<CompanyFormDto> findAllCom(Pageable pageable){
		QCompany company = QCompany.company;
		QReservation reservation = QReservation.reservation;
		
		List<CompanyFormDto> results = queryFactory
				.select(new QCompanyFormDto(company.com, company.name, company.email, reservation.location, reservation.startDay, reservation.endDay, company.approval))
				.from(reservation)
				.rightJoin(reservation.company, company)
				.offset(pageable.getOffset())
				.limit(pageable.getPageSize())
				.fetch();
		
		return new PageImpl<>(results, pageable, results.size());
	}
	
	//예약 완료된 기업 갯수 조회
	@Override
	public Long findSucReservationCom() {
		QCompany company = QCompany.company;
				
			Long count = queryFactory.selectFrom(company).where(company.approval.eq("예약완료")).fetchCount();
			
			return count;
	}
	
	//기업회원 갯수 조회
	@Override
	public Long findAllComCount() {
		QCompany company = QCompany.company;
		
		Long count = queryFactory
				.selectFrom(company)
				.fetchCount();
		return count;
	}
}
