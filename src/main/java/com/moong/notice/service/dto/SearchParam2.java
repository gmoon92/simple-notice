package com.moong.notice.service.dto;

import com.moong.notice.domain.board.BoardType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Type Safe 형식으로 변경
 * QueryDSL 적용 과정
 * @author gmoon
 */
@Getter
@Setter
@ToString
public class SearchParam2{

    @NotNull // null 허용 x, "", " " 허용
    private BoardType type;

    @NotNull // null 허용 x, "", " " 허용
    private SelectOptions optionDate;

    @NotNull // null 허용 x, "", " " 허용
    private SelectOptions optionKeyword;

    @NotNull // null 허용 x, "", " " 허용
    private LocalDateTime staYmd;

    @NotNull // null 허용 x, "", " " 허용
    private LocalDateTime endYmd;

    private String keyword;

    // LocalDateTime 유틸 메소드
    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDate.parse(date).atStartOfDay();
    }

    public static LocalDateTime toLocalDateTime(String date, boolean isStartYmd) {
        return LocalDate.parse(date)
                .atTime(isStartYmd ? LocalTime.MIN : LocalTime.MAX);
    }
}
