package com.moong.notice.service.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import com.moong.notice.domain.board.BoardType;

import lombok.Getter;
import lombok.ToString;

@Getter @ToString
public class SearchParam {

	@NotNull // null 허용 x, "", " " 허용
	private String type;
	
	@NotNull // null 허용 x, "", " " 허용
	private SelectOptions option_date;
	
	@NotNull // null 허용 x, "", " " 허용
	private SelectOptions option_keyword;

	@NotNull // null 허용 x, "", " " 허용
	private LocalDateTime sta_ymd;
	
	@NotNull // null 허용 x, "", " " 허용
	private LocalDateTime end_ymd;
	
	private String keyword;

	// setter
	public void setType(Integer type) {
		this.type = BoardType.of(type).name();
	}

	public void setSta_ymd(String sta_ymd) {
		this.sta_ymd = SearchParam.toLocalDateTime(sta_ymd);
	}

	public void setEnd_ymd(String end_ymd) {
		this.end_ymd = SearchParam.toLocalDateTime(end_ymd, false);
	}

	public void setOption_date(Integer option_date) {
//		this.option_date = SelectOptions.of(option_date).name();
		this.option_date = SelectOptions.of(option_date);
	}

	public void setOption_keyword(Integer option_keyword) {
//		this.option_keyword = SelectOptions.of(option_keyword).name();
		this.option_keyword = SelectOptions.of(option_keyword);
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	// LocalDateTime 유틸 메소드
	public static LocalDateTime toLocalDateTime(String date) {
		return LocalDate.parse(date).atStartOfDay();
	}
	
	public static LocalDateTime toLocalDateTime(String date, boolean isStartYmd) {
		return LocalDate.parse(date)
						.atTime( isStartYmd ? LocalTime.MIN : LocalTime.MAX );
	}
}
