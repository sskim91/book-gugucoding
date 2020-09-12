package org.zerock.sample;

import lombok.Data;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component  //스프링에게 해당 클래스가 스프링에서 관리해야 하는 대상임을 표시
@Data
public class Restaurant {

    @Setter(onMethod_ = @Autowired)
    private Chef chef;
}
