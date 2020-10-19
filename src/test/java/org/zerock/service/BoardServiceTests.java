package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {org.zerock.config.RootConfig.class})
@Log4j
public class BoardServiceTests {

    @Setter(onMethod_ = {@Autowired})
    private BoardService service;

    @Test
    public void testExist() {
        log.info(service);
        assertNotNull(service);
    }

    @Test
    public void testRegister() {
        BoardVO boardVO = new BoardVO();
        boardVO.setTitle("새로 작성하는 글");
        boardVO.setContent("새로 작성하는 내용");
        boardVO.setWriter("newbie");

        service.register(boardVO);

        log.info("생성된 게시물의 번호: " + boardVO.getBno());
    }

    @Test
    public void testGetList() {
//        boardService.getList().forEach(board -> log.info(board));
        service.getList(new Criteria(2, 10)).forEach(board -> log.info(board));
    }

    @Test
    public void testGet() {
        log.info(service.get(1L));
    }

    @Test
    public void testDelete() {
        //게시물의 번호의 존재 여부를 확인하고 테스트 할 것
        log.info("REMOVE RESULT: "+ service.remove(2L));
    }

    @Test
    public void testUpdate() {
        BoardVO boardVO = service.get(1L);

        if (boardVO == null) {
            return;
        }

        boardVO.setTitle("제목을 수정합니다.");
        log.info("MODIFY RESULT: "+ service.modify(boardVO));
    }
}
