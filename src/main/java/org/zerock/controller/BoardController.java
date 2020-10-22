package org.zerock.controller;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor
public class BoardController {

    private BoardService service;

//    @GetMapping("/list")
//    public void list(Model model) {
//        log.info("list");
//        model.addAttribute("list", boardService.getList());
//    }

    @GetMapping("/list")
    public void list(Criteria cri, Model model) {

        log.info("list: " + cri);
        model.addAttribute("list", service.getList(cri));
//        model.addAttribute("pageMaker", new PageDTO(cri, 123));

        int total = service.getTotal(cri);

        log.info("total: " + total);
        model.addAttribute("pageMaker", new PageDTO(cri, total));

    }

    @PostMapping("/register")
    public String register(BoardVO board, RedirectAttributes redirectAttributes) {
        log.info("register: " + board);
        service.register(board);
        redirectAttributes.addFlashAttribute("result", board.getBno());
        return "redirect:/board/list";
    }

    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {
        log.info("/get or modify");
        model.addAttribute("board", service.get(bno));
    }

    @PostMapping("/modify")
    public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri, RedirectAttributes redirectAttributes) {
        log.info("modify: " + board);

        if (service.modify(board)) {
            redirectAttributes.addFlashAttribute("result", "success");
        }

        redirectAttributes.addAttribute("pageNum", cri.getPageNum());
        redirectAttributes.addAttribute("amount", cri.getAmount());

        return "redirect:/board/list";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes redirectAttributes) {
        log.info("remove..." + bno);
        if (service.remove(bno)) {
            redirectAttributes.addFlashAttribute("result", "success");
        }

        redirectAttributes.addAttribute("pageNum", cri.getPageNum());
        redirectAttributes.addAttribute("amount", cri.getAmount());

        return "redirect:/board/list";
    }
}
