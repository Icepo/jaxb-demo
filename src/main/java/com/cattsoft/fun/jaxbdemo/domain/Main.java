package com.cattsoft.fun.jaxbdemo.domain;

import com.cattsoft.fun.jaxbdemo.entity.RfHeaderMVO;
import com.cattsoft.fun.jaxbdemo.entity.RfReport;
import com.cattsoft.fun.jaxbdemo.util.XsdUtil;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuzhangjun on 2015-11-30.
 */
public class Main {

    private static final String XML_PATH = "resources/xml/data.xml";

    private static final String XSD_PATH = "resources/xsd/";

    private static String charSet = "UTF-8";

    public static void main(String[] args) throws IllegalAccessException, JAXBException, InstantiationException, IOException, SAXException {
        //获取数据
        RfReport rfReport = new RfReport();
        rfReport.setRfReportId("id456");
        rfReport.setRfReportCode("code123");
        rfReport.setHeader(createHeader());
        rfReport.setHeaders(createHeaders());
        //验证出错会抛异常
//        System.out.println(XsdUtil.validateXml(new File(XML_PATH),new File(XSD_PATH+"RfReport.xsd")));
//        //写xsd TODO 对于未知的约束放弃 需要手动修改
//        XsdUtil.createXsdFile(rfReport.getClass(),XSD_PATH);

//        //类写xml
//        beanToXml(rfReport);
//        String xmlStr = readXml();
//        System.out.println(xmlStr);
//        //xml变成类
//        RfReport rfReport1 = (RfReport) xmlToBean(RfReport.class);
//        System.out.println(rfReport1.getHeaders());
    }

    /**
     * 获取单层测试用列表
     *
     * @return
     */
    private static List<RfHeaderMVO> createHeader() {
        return new ArrayList<RfHeaderMVO>() {
            {
                for (int i = 0; i < 5; i++) {
                    add(new RfHeaderMVO("id" + i, "title" + i, "extend" + i));
                }
            }
        };
    }

    /**
     * 获取测试用双层列表
     *
     * @return
     */
    private static List<List<RfHeaderMVO>> createHeaders() {
        return new ArrayList<List<RfHeaderMVO>>() {
            {
                for (int i = 0; i < 2; i++) {
                    add(new ArrayList<RfHeaderMVO>() {
                        {
                            for (int i = 0; i < 3; i++) {
                                add(new RfHeaderMVO("id" + i, "title" + i, "extend" + i));
                            }
                        }
                    });
                }
            }
        };
    }

    /**
     * 读文件
     *
     * @return
     */
    private static String readXml() {
        StringBuilder xmlStr = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(XML_PATH)), charSet));
            String line = "";
            while ((line = br.readLine()) != null) {
                xmlStr.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return xmlStr.toString();
    }

    /**
     * 类变xml
     *
     * @param obj
     * @return
     */
    private static void beanToXml(Object obj) throws JAXBException, IllegalAccessException, InstantiationException, FileNotFoundException {
        JAXBContext jaxbContext = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(obj, new BufferedWriter(new OutputStreamWriter(new FileOutputStream(new File(XML_PATH)))));
    }

    /**
     * xml变类 需要给类对象
     *
     * @param clazz
     * @return
     * @throws JAXBException
     */
    private static Object xmlToBean(Class clazz) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        Object obj = unmarshaller.unmarshal(new File(XML_PATH));
        return obj;
    }

}
