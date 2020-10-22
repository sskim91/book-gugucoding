package org.zerock.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author sskim
 */
@Data
public class ReplyVo {

    private Long rno;
    private Long bno;   //fk

    private String reply;
    private String replyer;
    private Date replyDate;
    private Date updateDate;
}
