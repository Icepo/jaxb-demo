package com.cattsoft.fun.jaxbdemo.util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.ParserConfigurationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by liuzhangjun on 2015-11-30.
 */
public class XmlUtil {

    /**
     * 对象变Element 默认使用element的方式 TODO attr的格式后续添加
     *
     * @param document
     * @param obj
     * @return
     * @throws ParserConfigurationException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public static Element beanToXml(Document document, Object obj) throws ParserConfigurationException, InvocationTargetException, IllegalAccessException {
        String name = obj.getClass().getName();
        Element rootElement = document.createElement(name);
        Method[] methods = obj.getClass().getMethods();
        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            String methodName = method.getName();
            String fieldName = analyzeFieldName(methodName);
            Element fieldElement = document.createElement(fieldName);
            if (methodName.startsWith("get") && !"getClass".equals(methodName)) {
                Object value = method.invoke(obj);
                if (value != null && !"".equals(value.toString())) {
                    fieldElement.setTextContent(value.toString());
                } else {
                    continue;
                }
            } else {
                continue;
            }
            rootElement.appendChild(fieldElement);
        }
        return rootElement;
    }

    /**
     * 把node对象转成对象 node对象的name应该是类全名
     *
     * @param beanNode
     * @return
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    public static Object xmlToBean(Node beanNode) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        String className = beanNode.getNodeName();
        System.out.println(className);
        Class clazz = Class.forName(className);
        Object bean = clazz.newInstance();
        NodeList fieldNodeList = beanNode.getChildNodes();
        for (int i = 0; i < fieldNodeList.getLength(); i++) {
            Node fieldNode = fieldNodeList.item(i);
            if (fieldNode.getNodeType() == Node.ELEMENT_NODE) {
                String fieldName = fieldNode.getNodeName();
                if (!fieldName.contains(".")) {
                    String getName = analyzeMethodName(fieldName, "get");
                    String setName = analyzeMethodName(fieldName, "set");
                    System.out.println(setName);
                    clazz.getMethod(setName, clazz.getMethod(getName).getReturnType()).invoke(bean, fieldNode.getTextContent());
                }
            }
        }
        System.out.println(bean);
        return bean;
    }

    /**
     * 从get set方法名解析出属性名
     *
     * @param methodName
     * @return
     */
    private static String analyzeFieldName(String methodName) {
        return String.valueOf(methodName.charAt(3)).toLowerCase() + methodName.substring(4, methodName.length());
    }

    /**
     * 属性名获得get set方法
     *
     * @param fieldName
     * @param methodType
     * @return
     */
    private static String analyzeMethodName(String fieldName, String methodType) {
        StringBuilder getName = new StringBuilder(methodType);
        return getName.append(String.valueOf(fieldName.charAt(0)).toUpperCase() + fieldName.substring(1)).toString();
    }

}
