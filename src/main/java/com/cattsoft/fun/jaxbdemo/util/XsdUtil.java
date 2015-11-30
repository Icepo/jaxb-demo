package com.cattsoft.fun.jaxbdemo.util;

import com.cattsoft.fun.jaxbdemo.entity.RfReport;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.Binder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by liuzhangjun on 2015-11-30.
 */
public class XsdUtil {

    /**
     * 指定类和
     * @param clazz
     * @param filePath
     * @throws JAXBException
     * @throws IOException
     */
    public static void createXsdFile(final Class clazz, final String filePath) throws JAXBException, IOException {
        JAXBContext context = JAXBContext.newInstance(clazz);
        Binder binder = context.createBinder(RfReport.class);
        Schema schema = binder.getSchema();
        context.generateSchema(new SchemaOutputResolver() {
            @Override
            public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
                return new StreamResult(new File(filePath + clazz.getSimpleName() + ".xsd"));
            }
        });
    }

    /**
     * 用xsd文件验证xml文件
     * @param xmlFile
     * @param xsdFile
     * @return
     */
    public static boolean validateXml(File xmlFile,File xsdFile) throws SAXException, IOException {
        SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = schemaFactory.newSchema(xsdFile);
        Validator validator = schema.newValidator();
        validator.validate(new SAXSource(new InputSource(new FileInputStream(xmlFile))));
        return true;
    }
}
