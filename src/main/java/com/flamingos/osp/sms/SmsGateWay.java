package com.flamingos.osp.sms;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.flamingos.osp.util.AppConstants;

public class SmsGateWay {

  @Value("${sms.url}")
  private String smsUrl;
  @Value("${sms.userid}")
  private String userId;
  @Value("${sms.password}")
  private String password;
  private VelocityEngine velocityEngine;


  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }


  @SuppressWarnings({"unchecked", "rawtypes"})
  public String sendSms(SmS sms) {
    String responseMessage;

    Map model = new HashMap();
    model.put(AppConstants.VTEMP_QUALIFIER, sms);
    String htmlBody =
        VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, sms.getTemplateName(), "UTF-8",
            model);

    sms.setContent(htmlBody);


    try {

      String requestUrl =
          smsUrl + "?user=" + URLEncoder.encode(userId, "UTF-8") + "&" + "password="
              + URLEncoder.encode(password, "UTF-8") + "&mobiles="
              + URLEncoder.encode(sms.getRecipient(), "UTF-8") + "&sms="
              + URLEncoder.encode(sms.getContent(), "UTF-8") + "&senderid=CCGSPL";
      URL url = new URL(requestUrl);
      HttpURLConnection uc = (HttpURLConnection) url.openConnection();
      responseMessage = uc.getResponseMessage();

      // Using DocumentBuilderFactory to read the xml from response

      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = (Document) db.parse(uc.getInputStream());
      uc.disconnect();

      Element docEle = doc.getDocumentElement(); // Root element of the
      // xml
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
