package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {org.zerock.config.RootConfig.class})
@Log4j
public class BoardMapperTest {

    @Setter(onMethod_ = @Autowired)
    private BoardMapper mapper;

    @Test
    public void testGetList() {
        mapper.getList().forEach(board -> log.info(board));
    }

    @Test
    public void testInsert() {
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("새로 작성하는 글");
        boardVO.setContent("새로 작성하는 내용");
        boardVO.setWriter("newbie");

        mapper.insert(boardVO);
        log.info(boardVO);
    }

    @Test
    public void testInsertSelectKey() {
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("새로 작성하는 글 Select key");
        boardVO.setContent("새로 작성하는 내용 Select key");
        boardVO.setWriter("newbie");

        mapper.insertSelectKey(boardVO);
        log.info(boardVO);
    }

    @Test
    public void testRead() {
        //존재하는 게시물 번호로 테스트
        BoardVO boardVO = mapper.read(5L);
        log.info(boardVO);
    }

    @Test
    public void testDelete() {
        log.info("DELETE COUNT: " + mapper.delete(3L));
    }

    @Test
    public void testUpdate() {
        BoardVO boardVO = new BoardVO();
        //실행전 존재하는 번호인지 확인
        boardVO.setBno(1L);
        boardVO.setTitle("수정된 제목");
        boardVO.setContent("수정된 제목");
        boardVO.setWriter("user00");

        int count = mapper.update(boardVO);
        log.info("UPDATE COUNT: " + count);
    }

    @Test
    public void testPaging() {
        Criteria cri = new Criteria();

        //10개씩 3페이지
        cri.setPageNum(3);
        cri.setAmount(10);

        List<BoardVO> list = mapper.getListWithPaging(cri);
        list.forEach(board -> log.info(board));
    }

    @Test
    public void testSearch() {

        Criteria cri = new Criteria();
        cri.setKeyword("키워드");
        cri.setType("TCW");

        List<BoardVO> list = mapper.getListWithPaging(cri);

        list.forEach(board -> log.info(board));
    }
}