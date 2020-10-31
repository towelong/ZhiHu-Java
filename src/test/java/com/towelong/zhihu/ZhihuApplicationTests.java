package com.towelong.zhihu;

import com.towelong.zhihu.common.core.SingleJWT;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ZhihuApplicationTests {

    @Autowired
    private SingleJWT singleJWT;

    @Test
    void contextLoads() {
    }

    @Test
    void JwtTest() {
        String token = singleJWT.generateToken("access", 1, "welong");
        System.out.println(token);
    }

}
