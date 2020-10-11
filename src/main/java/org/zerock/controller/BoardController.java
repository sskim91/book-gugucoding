package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    private BoardService boardService;

    @GetMapping("/list")
    public void list(Model model) {
        log.info("list");
        model.addAttribute("list", boardService.getList());
    }

    @PostMapping("/register")
    public String register(BoardVO boardVO, RedirectAttributes redirectAttributes) {
        log.info("register: " + boardVO);
        boardService.register(boardVO);
        redirectAttributes.addFlashAttribute("result", boardVO.getBno());
        return "redirect:/board/list";
    }

    @GetMapping("/get")
    public void get(@RequestParam("bno") Long bno, Model model) {
        log.info("/get");
        model.addAttribute("board", boardService.get(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO boardVO, RedirectAttributes redirectAttributes) {
        log.info("modify: " + boardVO);

        if (boardService.modify(boardVO)) {
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, RedirectAttributes redirectAttributes) {
        log.info("remove..." + bno);
        if (boardService.remove(bno)) {
            redirectAttributes.addFlashAttribute("result", "success");
        }
        return "redirect:/board/list";
    }
}
