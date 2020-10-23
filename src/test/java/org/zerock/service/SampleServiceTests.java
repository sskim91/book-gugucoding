package org.zerock.service;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * @author sskim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {org.zerock.config.RootConfig.class})
@Log4j
public class SampleServiceTests {

    @Setter(onMethod_ = @Autowired)
    private SampleService service;

    @Test
    public void testClass() {
        log.info("service = " + service);
        log.info("service.getClass().getName() = " + service.getClass().getName());
    }

    @Test
    public void testAdd() throws Exception {
        log.info(service.doAdd("123","456"));
    }

    @Test
    public void testAddError() throws Exception {
        log.info("service.doAdd(\"123\",\"ABC\") = " + service.doAdd("123", "ABC"));
    }
}