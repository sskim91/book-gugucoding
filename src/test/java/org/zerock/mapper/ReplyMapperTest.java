package org.zerock.mapper;

import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author sskim
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {org.zerock.config.RootConfig.class})
@Log4j
public class ReplyMapperTest {

    @Setter(onMethod_ = @Autowired)
    private ReplyMapper mapper;

    @Test
    public void testMapper() {
        log.info(mapper);
    }
}