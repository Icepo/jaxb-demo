package com.cattsoft.fun.jaxbdemo.entity;

import com.cattsoft.fun.jaxbdemo.util.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.parsers.DocumentBuilderFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhangjun on 2015-11-30.
 */
public class DoubleListAdapter extends XmlAdapter<Object, List<List<?>>> {

    /**
     * xml变成指定格式对象 手动解析节点
     *
     * @param v
     * @return
     * @throws Exception
     */
    @Override
    public List<List<?>> unmarshal(Object v) throws Exception {
        final Element rootElement = (Element) v;
        return new ArrayList<List<?>>() {
            {
                NodeList nodeList = rootElement.getChildNodes();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    final Node node = nodeList.item(i);
                    add(new ArrayList<Object>() {
                        {
                            NodeList beanNodeList = node.getChildNodes();
                            for (int j = 0; j < beanNodeList.getLength(); j++) {
                                Node beanNode = beanNodeList.item(j);
                                //判定下是否是元素节点 过滤空格和换行
                                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                                    add(XmlUtil.xmlToBean(beanNode));
                                }
                            }
                        }
                    });
                }
            }
        };
    }

    /**
     * 自定义规则解析数据结构为xml
     *
     * @param v
     * @return
     * @throws Exception
     */
    @Override
    public Object marshal(List<List<?>> v) throws Exception {
        Element rootElement = null;
        if (v != null && v.get(0) != null && v.get(0).get(0) != null) {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            rootElement = document.createElement("root");
            for (int i = 0; i < v.size(); i++) {
                List<?> list = v.get(i);
                Element listElement = document.createElement("list");
                for (int j = 0; j < list.size(); j++) {
                    listElement.appendChild(XmlUtil.beanToXml(document, list.get(j)));
                }
                rootElement.appendChild(listElement);
            }
        }
        return rootElement;
    }
}
