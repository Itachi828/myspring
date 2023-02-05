package com.myspringframework.test;

import org.junit.Test;
import org.myspringframework.ClassPathXmlApplicationContext;

/**
 * @Author: Itachi
 * @Date: 2023/1/22 18:47
 * @Version: jdk1.8
 * @Description:
 */
public class ClassPathXmlApplicationContextTest {
    @Test
    public void classPathXmlApplicationContextTest() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("myspring.xml");
    }
}