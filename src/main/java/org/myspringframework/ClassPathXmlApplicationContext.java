package org.myspringframework;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Itachi
 * @Date: 2023/1/22 17:53
 * @Version: jdk1.8
 * @Description: 从类路径中加载myspring.xml配置
 */
public class ClassPathXmlApplicationContext implements ApplicationContext{
    //采用Map集合存储bean
    private Map<String,Object> beanMap = new HashMap<>();

    /**
     * 在构造方法中解析xml文件，创建所有bean实例，并将bean实例存放到map集合中
     * @param resource 配置文件路径（要求在类路径中）
     */
    public ClassPathXmlApplicationContext(String resource){
        try {
            SAXReader reader = new SAXReader();
            Document document = reader.read(ClassLoader.getSystemClassLoader().getResourceAsStream(resource));
            //获取所有的bean标签
            List<Node> beanNodes = document.selectNodes("//bean");
            //遍历集合
            beanNodes.forEach(beanNode -> {
                Element beanElt = (Element) beanNode;
                String id = beanElt.attributeValue("id");
                String className = beanElt.attributeValue("class");
                try {
                    //通过反射机制创造对象
                    Class<?> aClass = Class.forName(className);
                    Constructor<?> cons = aClass.getConstructor();
                    Object beanObj = cons.newInstance();
                    //存储到map集合中
                    beanMap.put(id,beanObj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            // 再重新遍历集合，这次遍历是为了给Bean的所有属性赋值。
            // 思考：为什么不在上面的循环中给Bean的属性赋值，而在这里再重新遍历一次呢？
            // 通过这里你是否能够想到Spring是如何解决循环依赖的：实例化和属性赋值分开。
            beanNodes.forEach(beanNode->{
                Element beanElt = (Element) beanNode;
                String id = beanElt.attributeValue("id");
                //获取所有带有property的标签
                List<Element> propertyElts = beanElt.elements("property");
                //遍历所有属性
                propertyElts.forEach(propertyElt->{
                    //获取属性名
                    String propertyName = propertyElt.attributeValue("name");
                    try {
                        //获取属性类型
                        Class<?> propertyType = beanMap.get(id).getClass().getDeclaredField(propertyName).getType();
                        //获取set方法名
                        String setMethodName = "set"+propertyName.toUpperCase().charAt(0)+propertyName.substring(1);
                        //获取set方法
                        Method method = beanMap.get(id).getClass().getDeclaredMethod(setMethodName,propertyType);
                        //System.out.println(setMethodName);
                        // 获取属性的值，值可能是value，也可能是ref。
                        // 获取value
                        String propertyValue = propertyElt.attributeValue("value");
                        // 获取ref
                        String propertyRef = propertyElt.attributeValue("ref");
                        Object propertyVal = null;
                        if (propertyValue != null) {
                            // 该属性是简单属性
                            String propertyTypeSimpleName = propertyType.getSimpleName();
                            switch (propertyTypeSimpleName) {
                                case "byte", "Byte" -> propertyVal = Byte.valueOf(propertyValue);
                                case "short", "Short" -> propertyVal = Short.valueOf(propertyValue);
                                case "int", "Integer" -> propertyVal = Integer.valueOf(propertyValue);
                                case "long", "Long" -> propertyVal = Long.valueOf(propertyValue);
                                case "float", "Float" -> propertyVal = Float.valueOf(propertyValue);
                                case "double", "Double" -> propertyVal = Double.valueOf(propertyValue);
                                case "boolean", "Boolean" -> propertyVal = Boolean.valueOf(propertyValue);
                                case "char", "Character" -> propertyVal = propertyValue.charAt(0);
                                case "String" -> propertyVal = propertyValue;
                            }
                            method.invoke(beanMap.get(id),propertyVal);
                        }
                        if (propertyRef != null) {
                            method.invoke(beanMap.get(id),beanMap.get(propertyRef));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public Object getBean(String beanId) {
        return beanMap.get(beanId);
    }
}
