package org.myspringframework;

/**
 * @Author: Itachi
 * @Date: 2023/1/22 17:51
 * @Version: jdk1.8
 * @Description: 提供一个get方法，用于获取bean
 */
public interface ApplicationContext {
    /**
     * 通过bean的id获取bean实例
     * @param beanId bean的ID
     * @return bean实例
     */
    Object getBean(String beanId);
}
