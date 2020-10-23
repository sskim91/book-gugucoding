package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.domain.Criteria;
import org.zerock.domain.ReplyVO;
import org.zerock.mapper.ReplyMapper;

import java.util.List;

/**
 * @author sskim
 */
@Service
@Log4j
public class ReplyServiceImpl implements ReplyService {

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper mapper;

    @Override
    public int register(ReplyVO vo) {
        log.info("register = " + vo);
        return mapper.insert(vo);
    }

    @Override
    public ReplyVO get(Long rno) {
        log.info("get rno = " + rno);
        return mapper.read(rno);
    }

    @Override
    public int remove(Long rno) {
        log.info("remove rno = " + rno);
        return mapper.delete(rno);
    }

    @Override
    public int modify(ReplyVO vo) {
        log.info("modify = " + vo);
        return mapper.update(vo);
    }

    @Override
    public List<ReplyVO> getList(Criteria cri, Long bno) {
        log.info("get reply list of board = " + bno);
        return mapper.getListWithPaging(cri, bno);
    }
}
