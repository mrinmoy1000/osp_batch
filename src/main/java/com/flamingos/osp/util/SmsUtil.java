package com.flamingos.osp.util;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Configuration
@PropertySource("classpath:osp.properties")
public class SmsUtil {
  @Value("${sms.userid}")
  private String userId;
  @Value("${sms.password}")
  private String password;

  public String sendSms(SmsBean smsBean) {


    String responseMessage;
    try {

      String requestUrl =
          "http://sms.ismilez.in/sendsms.jsp?user=" + URLEncoder.encode(userId, "UTF-8") + "&"
              + "password=" + URLEncoder.encode(password, "UTF-8") + "&mobiles="
              + URLEncoder.encode(smsBean.getRecipient(), "UTF-8") + "&sms="
              + URLEncoder.encode(smsBean.getMessage(), "UTF-8") + "&senderid=CCGSPL";
      URL url = new URL(requestUrl);
      HttpURLConnection uc = (HttpURLConnection) url.openConnection();
      responseMessage = uc.getResponseMessage();

      // Using DocumentBuilderFactory to read the xml from response

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = (Document) db.parse(uc.getInputStream());
      uc.disconnect();

      Element docEle = doc.getDocumentElement(); // Root element of the xml
      NodeList nodes = docEle.getChildNodes(); // list of child nodes

      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);

        if (node.getNodeType() == Node.ELEMENT_NODE) {

          // Response updated here if error occurres
          if (node.getNodeName().equals("error")) {
            responseMessage = "Error";
          }

        }
      }



    } catch (Exception ex) {
      responseMessage = ex.getMessage();

    }
    return responseMessage;
  }

}
