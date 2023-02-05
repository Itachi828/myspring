package com.myspringframework.test;

import org.myspringframework.ApplicationContext;
import org.myspringframework.ClassPathXmlApplicationContext;


/**
 * @Author: Itachi
 * @Date: 2023/1/22 18:14
 * @Version: jdk1.8
 * @Description:
 */
public class MySpringTest {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("myspring.xml");
        Object user = applicationContext.getBean("user");
        System.out.println(user);
        System.out.println("测试git提交");
        System.out.println("第一次测试合并merge");
        System.out.println("第二次测试合并——冲突合并-main");
        System.out.println("第二次测试合并merge-冲突合并-git_text");
        System.out.println("第一次测试Github-main");
        System.out.println("第二次测试github-main:");
    }

}
