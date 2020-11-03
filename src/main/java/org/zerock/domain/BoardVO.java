package org.zerock.domain;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BoardVO {
    private Long bno;
    private String title;
    private String content;
    private String writer;
    private Date regdate;
    private Date updateDate;

    //댓글이 숫자를 의미하는 인스턴스 변수
    private int replyCnt;

    private List<BoardAttachVO> attachList;
}
