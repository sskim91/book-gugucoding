package org.zerock.domain;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TodoDto {

    private String title;

    //@DateTimeForamt 을 사용하면 @InitBinder는 필요하지 않음
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date dueDate;
}
