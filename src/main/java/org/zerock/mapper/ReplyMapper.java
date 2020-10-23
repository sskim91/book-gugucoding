package org.zerock.mapper;

import org.apache.ibatis.annotations.Param;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVo;

import java.util.List;

public interface ReplyMapper {

    public int insert(ReplyVo vo);

    public ReplyVo read(Long rno);

    public int delete(Long rno);

    public int update(ReplyVo vo);

    public List<ReplyVo> getListWithPaging(@Param("cri") Criteria cri, @Param("bno") Long bno);

}
