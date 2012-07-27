package com.server.cx.util;

import com.server.cx.constants.Constants;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParserFactory;
import java.io.StringReader;
import java.util.HashMap;

public class XMLParser {
    private static XMLParser instance;

    public static XMLParser getInstance() {
        if (instance == null) {
            instance = new XMLParser();
        }
        return instance;
    }


    public class MyXMLParser extends DefaultHandler {
        private HashMap<String, String> params;
        private String tagName = null;
        private String imageStream = "";
        @SuppressWarnings("unused")
        private boolean hasAttributes;

        public void parser(String sourceStr, HashMap<String, String> params) {
            System.out.println("sourthStr = " + sourceStr);
            // 创建SAXParserFactory解析器工厂
            System.out.println(XMLParser.class.getName() + "----------parser() begin-------");
            this.params = params;
            SAXParserFactory parserFactory = SAXParserFactory.newInstance();
            try {
                // 创建XMLReader对象，xml文件解析器
                XMLReader xmlReader = parserFactory.newSAXParser().getXMLReader();
                // SAXParser saxParser = parserFactory.newSAXParser();
                // 注册内容事件处理器（设置xml文件解析器的解析方式）
                xmlReader.setContentHandler(this);
                // 开始解析xml格式文件
                hasAttributes = false;
                xmlReader.parse(new InputSource(new StringReader(sourceStr)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(XMLParser.class.getName() + "----------parser() end-------");
        }

        @Override
        public void startDocument() throws SAXException {
            super.startDocument();
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            tagName = qName;
            if (attributes != null) {
                for (int i = 0; i < attributes.getLength(); i++) {
                    String aName = attributes.getQName(i);
                    String aValue = attributes.getValue(aName);
                    System.out.println("aName = " + aName + "----aValue = " + aValue);
                    params.put(aName, aValue);
                }
            }
        }

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {

            if (tagName != null) {
                String value = new String(ch, start, length);
                // if return sucess, get the body info
                if (!"cx_image".equals(tagName)) {
                    if (tagName.equals(Constants.IMAGE_ID_STR)) {
                        //
                    } else {
                        params.put(tagName, value);
                    }
                } else {
                    imageStream += value;
                }
            }
        }

        @Override
        public void endDocument() throws SAXException {
            super.endDocument();
        }

        @Override
        public void endElement(String uri, String localName, String qName) throws SAXException {
            tagName = qName;
            if (tagName.equals("cx_image")) {
                params.put(tagName, imageStream);
                imageStream = "";
            }
            tagName = "";
        }

    }


    public Object parser(String sourceStr, HashMap<String, String> params, String url) {
        new MyXMLParser().parser(sourceStr, params);
        return null;
    }

}
