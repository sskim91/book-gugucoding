package org.zerock.service;

import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;

import java.util.List;

public interface BoardService {

    public void register(BoardVO boardVO);

    public BoardVO get(Long bno);

    public boolean modify(BoardVO boardVO);

    public boolean remove(Long bno);

    // public List<BoardVO> getList();

    public List<BoardVO> getList(Criteria cri);

    //추가
	public int getTotal(Criteria cri);

	public List<BoardAttachVO> getAttachList(Long bno);
}
