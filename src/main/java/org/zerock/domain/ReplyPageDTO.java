package org.zerock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author sskim
 */
@Data
@AllArgsConstructor
public class ReplyPageDTO {

    private int replyCnt;
    private List<ReplyVO> list;
}
