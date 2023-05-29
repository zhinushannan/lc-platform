package io.github.zhinushannan.lcplatformback;

import io.github.zhinushannan.lcplatformback.mapper.CreateBusinessMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class LcPlatformBackApplicationTests {

    @Autowired
    private CreateBusinessMapper createBusinessMapper;

    @Test
    void contextLoads() {
        List<String> strings = new ArrayList<String>() {{
            add("`test_field1` varchar(255) NOT NULL,");
            add("`test_field2` varchar(255) NOT NULL,");
        }};
        System.out.println(strings);
        int line = createBusinessMapper.createNewTable("test", strings);
        System.out.println(line);
    }

}
