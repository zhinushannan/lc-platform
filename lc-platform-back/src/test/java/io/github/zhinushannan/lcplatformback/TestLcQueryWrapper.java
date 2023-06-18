package io.github.zhinushannan.lcplatformback;

import io.github.zhinushannan.lcplatformback.service.LcQueryService;
import io.github.zhinushannan.lcplatformback.util.query.LcQueryWrapper;
import io.github.zhinushannan.lcplatformback.util.query.LcQueryWrapperBuilder;
import io.github.zhinushannan.lcplatformback.util.query.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class TestLcQueryWrapper {

    @Autowired
    private LcQueryService lcQueryService;

    @Test
    void test() {
        LcQueryWrapper test = new LcQueryWrapperBuilder("test")
                .eq("test1", 4)
                .or()
                .eq("test1", 1)
                .orderByDesc("test1")
                .build();

        Page page = lcQueryService.page(Page.of(1, 1), test);
        System.out.println(page);


    }

}
