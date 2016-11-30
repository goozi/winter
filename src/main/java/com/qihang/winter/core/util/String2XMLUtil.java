package com.qihang.winter.core.util;

import com.qihang.winter.web.system.service.SystemService;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by LenovoM4550 on 2016-05-10.
 */
public class String2XMLUtil {
    /**
     * @param args
     * @throws IOException
     */

    public Document strChangeXML(String str) throws IOException {
        SAXReader saxReader = new SAXReader();
        Document document = null;
        try {
            str = str.replace("UTF-8","gbk");
            document = saxReader.read(new ByteArrayInputStream(str.getBytes()));
            Element rootElement = document.getRootElement();

            String getXMLEncoding   =   document.getXMLEncoding();
            String rootname   =   rootElement.getName();
            System.out.println("getXMLEncoding>>>"   +   getXMLEncoding   +   ",rootname>>>"   +   rootname);

            OutputFormat format = OutputFormat.createPrettyPrint();
            /** 指定XML字符集编码 */
            format.setEncoding(getXMLEncoding);
            /** 将document中的内容写入文件中 */
            XMLWriter writer = new XMLWriter(new FileWriter(new File("cctv.xml")),format);
            writer.write(document);
            writer.close();
        } catch (DocumentException e) {
            // TODOAuto-generatedcatchblock
            e.printStackTrace();
        }
        return document;
    }

//    public static void main(String[] args) throws IOException{
//        String str="<?xml version='1.0' encoding='GB2312'?>" +
//                "<GESInfo>" +
//                "<City id='苏州' name='苏州' code='1001'>000" +
//                "<Area id='市区' name='市区' code='10011001' LocationSetup='0'>123" +
//               // "<Place id='观前' name='观前' code='100110011001' LocationSetup='1'>" +
//               // "<Monitor id='摄像头1' name='摄像头1' code='11000000000000000011200034800000' gesid='abcde1' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "<Monitor id='摄像头2' name='摄像头2' code='11000000000000000011200034800000' gesid='abcde2' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "</Place>" +
//               // "<Place id='石路' name='石路' code='100110011002' LocationSetup='2'>" +
//               // "<Monitor id='摄像头3' name='摄像头3' code='11000000000000000011200034800000' gesid='abcde3' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "<Monitor id='摄像头4' name='摄像头4' code='11000000000000000011200034800000' gesid='abcde4' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "</Place>" +
//                "</Area>" +
//                "<Area id='园区' name='园区' code='10011002' LocationSetup='0'>456" +
//               // "<Place id='科技园' name='科技园' code='100110011003' LocationSetup='1'>" +
//               // "<Monitor id='摄像头5' name='摄像头5' code='11000000000000000011200034800000' gesid='abcde5' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "<Monitor id='摄像头6' name='摄像头6' code='11000000000000000011200034800000' gesid='abcde6' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "</Place>" +
//               //"<Place id='金鸡湖' name='金鸡湖' code='100110011004' LocationSetup='2'>" +
//               // "<Monitor id='摄像头7' name='摄像头7' code='11000000000000000011200034800000' gesid='abcde7' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "<Monitor id='摄像头8' name='摄像头8' code='11000000000000000011200034800000' gesid='abcde8' channelid='1' VideoScan='1' Capture='1' ManualREC='1' RecPlay ='1' MonControl='1'/>" +
//               // "</Place>" +
//                "</Area>" +
//                "</City>" +
//                "</GESInfo>";
//        Document document = new String2XMLUtil().strChangeXML(str);
//        try {
//            new String2XMLUtil().readXML(document);
//        }catch (DocumentException e){
//            System.out.print("bbb");
//        }
//
//        System.out.print("aaaa");
//    }
}
