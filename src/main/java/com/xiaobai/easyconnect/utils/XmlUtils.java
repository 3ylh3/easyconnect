package com.xiaobai.easyconnect.utils;

import com.xiaobai.easyconnect.common.Entity;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.File;
import java.util.Iterator;

/**
 * xml解析工具类
 *
 * @author yin_zhj
 * @date 2020/5/29
 */
public class XmlUtils {
    private static final String START = "$";

    public static String parseReqXml(Element element, Entity entity) {
        Document result = DocumentHelper.createDocument();
        Element root = result.addElement("root");
        Iterator iterator = element.elementIterator();
        while(iterator.hasNext()) {
            Element e = (Element)iterator.next();
            String name = e.getName();
            Element tmp = root.addElement(name);
            String value = "";
            if(e.elements().size() > 1) {
                value = parseReqXml(e, entity);
                tmp.setText(value);
            } else {
                value = e.getText();
                if(value.startsWith(START)) {
                    String val = entity.get(value.substring(1));
                    if(null == val) {
                        val = "";
                    }
                    tmp.setText(val);
                } else {
                    tmp.setText(value);
                }
            }
        }
        String str = StringEscapeUtils.unescapeXml(result.getRootElement().asXML());
        return StringUtils.substring(str, 6, str.length() - 7);
    }

    public static void parseRspStr(Element element, String str, Entity entity) throws DocumentException {
        Document document = DocumentHelper.parseText(str);
        Element root = document.getRootElement();
        Iterator iterator = element.elementIterator();
        while(iterator.hasNext()) {
            Element e = (Element)iterator.next();
            String name = e.getName();
            if(e.elements().size() > 1) {
                parseRspStr(e, StringEscapeUtils.unescapeXml(root.element(name).asXML()), entity);
                String value = root.element(name).asXML();
                if(null == value) {
                    value = "";
                }
                entity.put(name, StringEscapeUtils.unescapeXml(value));
            } else {
                String value = e.getText();
                if(value.startsWith(START)) {
                    String val = root.element(value.substring(1)).getText();
                    if(null == val) {
                        val = "";
                    }
                    entity.put(name, val);
                } else {
                    entity.put(name, value);
                }
            }
        }
    }
}
