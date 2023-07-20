package org.example.util;

import io.jsonwebtoken.Claims;
import org.example.SecurityMain;
import org.example.factory.UserFactory;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = SecurityMain.class)
@RunWith(SpringRunner.class)
class JwtUtilsTest {

    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    public void setup() {
        SpringContextUtil contextUtil = applicationContext.getBean(SpringContextUtil.class);
        System.out.println(contextUtil);
    }

    @Test
    void parseToken() {
        User user = UserFactory.getUsers().get(0);
        String token = JwtUtils.generateToken(user);
        System.out.println(token);
        Claims claims = JwtUtils.parseToken(token);
        System.out.println(claims);
    }
}