package com.ayundao;

import com.ayundao.entity.Subject;
import com.ayundao.service.SubjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AyundaoApplicationTests {

    @Autowired
    SubjectService subjectService;

    @Test
    public void contextLoads() {
        String i = "213123123";
            Subject subject = subjectService.find(i);
        System.out.println(subject);
    }

}
