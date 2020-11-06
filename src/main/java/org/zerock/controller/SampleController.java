package org.zerock.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.zerock.domain.SampleDTO;
import org.zerock.domain.SampleDTOList;
import org.zerock.domain.TodoDto;

import java.util.ArrayList;
import java.util.Arrays;

@Controller
@RequestMapping("/sample/*")
@Log4j
public class SampleController {

    @RequestMapping("")
    public void basic() {
        log.info("basic.........");
    }

    @GetMapping("/basicOnlyGet")
    public void basicGet2() {
        log.info("basic get only get...");
    }

    //파라미터의 수집과 변환
    @GetMapping("/ex01")
    public String ex01(SampleDTO dto) {
        log.info("" + dto);
        return "ex01";
    }

    //리스트, 배열 처리
    @GetMapping("ex02List")
    public String ex02List(@RequestParam("ids") ArrayList<String> ids) {
        log.info("ids: " + ids);
        return "ex02List";
    }

    @GetMapping("ex02Array")
    public String ex02Array(@RequestParam("ids") String[] ids) {
        log.info("ids: " + Arrays.toString(ids));
        return "ex02Array";
    }

    //객체 리스트
    @GetMapping("/ex02Bean")
    public String ex02Bean(SampleDTOList list) {
        log.info("list dtos: " + list);
        return "ex02Bean";
    }

    //@InitBinder  ex) 2018-01-01 같은 데이터를 Date 타입으로 변환작업
    //@DateTimeForamt 을 사용하면 @InitBinder는 필요하지 않음  TodoDto.java 파일 확인
//    @InitBinder
//    public void initBinder(WebDataBinder binder) {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(dateFormat, false));
//    }

    @GetMapping("/ex03")
    public String ex03(TodoDto todoDto) {
        log.info("todo: " + todoDto);
        return "ex03";
    }

    //Model 이라는 데이터 전달자
    @GetMapping("/ex04")
    public String ex04(SampleDTO dto, @ModelAttribute("page") int page) {
        log.info("dto: " + dto);
        log.info("page: " + page);

        return "/sample/ex04";
    }

    //6.5 Controller의 리턴 타입
    //void 타입 해당 URL의 경로 그대로 JSP 파일의 이름을 사용
    //String 타입, JSP 파일의 이름을 의미
    @GetMapping("/ex05")
    public void ex05() {
        log.info("/ex05.....");
    }

    //6.5.3 객체 타입
    @GetMapping("/ex06")
    public @ResponseBody
    SampleDTO ex06() {
        log.info("/ex06...");

        SampleDTO sampleDTO = new SampleDTO();
        sampleDTO.setAge(10);
        sampleDTO.setName("홍길동");

        return sampleDTO;
    }

    //ResponseEntity 타입
    @GetMapping("/ex07")
    public ResponseEntity<String> ex07() {
        log.info("/ex07..");


        //{"name": "홍길동}
        String msg = "{\"name\": \"홍길동\"}";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json;charset=UTF-8");

        return new ResponseEntity<>(msg, headers, HttpStatus.OK);
    }

    /**
     * Security  부분
     */
    @GetMapping("/all")
    public void doAll() {
        log.info("do all can access everybody");
    }

    @GetMapping("/member")
    public void doMember() {
        log.info("logined member");
    }

    @GetMapping("/admin")
    public void doAdmin() {
        log.info("admin only");
    }
}
