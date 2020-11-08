package org.zerock.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.domain.BoardAttachVO;
import org.zerock.domain.BoardVO;
import org.zerock.domain.Criteria;
import org.zerock.domain.PageDTO;
import org.zerock.service.BoardService;

import lombok.extern.log4j.Log4j;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@Log4j
@RequestMapping("/board/*")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService service;

    @GetMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public void register() {

    }

    // @GetMapping("/list")
    // public void list(Model model) {
    //
    // log.info("list");
    // model.addAttribute("list", service.getList());
    //
    // }

    // @GetMapping("/list")
    // public void list(Criteria cri, Model model) {
    //
    // log.info("list: " + cri);
    // model.addAttribute("list", service.getList(cri));
    //
    // }

    @GetMapping("/list")
    public void list(Criteria cri, Model model) {

        log.info("list: " + cri);
        model.addAttribute("list", service.getList(cri));
        // model.addAttribute("pageMaker", new PageDTO(cri, 123));

        int total = service.getTotal(cri);

        log.info("total: " + total);

        model.addAttribute("pageMaker", new PageDTO(cri, total));

    }

    @PostMapping("/register")
    @PreAuthorize("isAuthenticated()")
    public String register(BoardVO board, RedirectAttributes rttr) {
        log.info("===============================");
        log.info("register: " + board);

        if (board.getAttachList() != null) {
            board.getAttachList().forEach(attach -> log.info("attach = " + attach));
        }
        log.info("===============================");
        service.register(board);

        rttr.addFlashAttribute("result", board.getBno());

        return "redirect:/board/list";
    }

    // @GetMapping({ "/get", "/modify" })
    // public void get(@RequestParam("bno") Long bno, Model model) {
    //
    // log.info("/get or modify ");
    // model.addAttribute("board", service.get(bno));
    // }

    @GetMapping({"/get", "/modify"})
    public void get(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, Model model) {

        log.info("/get or modify");
        model.addAttribute("board", service.get(bno));
    }

    // @PostMapping("/modify")
    // public String modify(BoardVO board, RedirectAttributes rttr) {
    // log.info("modify:" + board);
    //
    // if (service.modify(board)) {
    // rttr.addFlashAttribute("result", "success");
    // }
    // return "redirect:/board/list";
    // }

    @PreAuthorize("principal.username == #board.writer")
    @PostMapping("/modify")
    public String modify(BoardVO board, Criteria cri, RedirectAttributes rttr) {
        log.info("modify:" + board);

        if (service.modify(board)) {
            rttr.addFlashAttribute("result", "success");
        }

        return "redirect:/board/list" + cri.getListLink();
    }

    // @PostMapping("/remove")
    // public String remove(@RequestParam("bno") Long bno, RedirectAttributes rttr)
    // {
    //
    // log.info("remove..." + bno);
    // if (service.remove(bno)) {
    // rttr.addFlashAttribute("result", "success");
    // }
    // return "redirect:/board/list";
    // }

    @PreAuthorize("principal.username == #writer")
    @PostMapping("/remove")
    public String remove(@RequestParam("bno") Long bno, Criteria cri, RedirectAttributes rttr, String writer) {

        log.info("remove..." + bno);

        List<BoardAttachVO> attachList = service.getAttachList(bno);

        if (service.remove(bno)) {
            // delete Attach Files
            deleteFiles(attachList);

            rttr.addFlashAttribute("result", "success");
        }

        return "redirect:/board/list" + cri.getListLink();
    }


    @GetMapping(value = "/getAttachList", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno) {

        log.info("getAttachList " + bno);

        return new ResponseEntity<>(service.getAttachList(bno), HttpStatus.OK);

    }

    private void deleteFiles(List<BoardAttachVO> attachList) {

        if (attachList == null || attachList.size() == 0) {
            return;
        }

        log.info("delete attach files...................");
        log.info(attachList);

        attachList.forEach(attach -> {
            try {
                Path file = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\" + attach.getUuid() + "_" + attach.getFileName());

                Files.deleteIfExists(file);

                if (Files.probeContentType(file).startsWith("image")) {

                    Path thumbNail = Paths.get("C:\\upload\\" + attach.getUploadPath() + "\\s_" + attach.getUuid() + "_" + attach.getFileName());

                    Files.delete(thumbNail);
                }

            } catch (Exception e) {
                log.error("delete file error" + e.getMessage());
            }//end catch
        });//end foreachd
    }
}
