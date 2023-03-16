package com.exposition.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.exposition.dto.BoardMainDto;
import com.exposition.dto.EventBoardDto;
import com.exposition.dto.TourBoardDto;
import com.querydsl.core.Tuple;

public interface BoardRepositoryCustom {

	//주변 관광지 게시글 리스트 출력
	Page<BoardMainDto> getBoardMainPage(TourBoardDto tourBoardDto, Pageable pageable);
	//이벤트 페이지에 당첨자 출력
	List<Tuple> eventPrizeMember();
}
