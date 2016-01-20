package com.flamingos.osp.email;

import java.io.StringWriter;

import javax.mail.internet.AddressException;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

public class EmailGateway {
  private MailSender mailSender;
  private VelocityEngine velocityEngine;

  public void setMailSender(MailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }



  public void sendMail(Mail mail) throws AddressException {
    SimpleMailMessage message = new SimpleMailMessage();

    message.setFrom(mail.getMailFrom());
    String[] toAddresses = {new String(mail.getMailTo())};
    message.setTo(toAddresses);;
    message.setSubject(mail.getMailSubject());

    // FileDataSource myFile = new FileDataSource (new File
    // ("C:\\Users\\adas7\\Desktop\\BLE\\images\\test.jpg"));
    // http://stackoverflow.com/questions/18941773/how-to-add-image-in-email-velocity-transformer-templates-from-classpath
    // message.
    // message.setOutboundProperty("logo", myFile);

    Template template = velocityEngine.getTemplate(mail.getTemplateName());

    VelocityContext velocityContext = new VelocityContext();
    // velocityContext.put("image1", "C:\\Users\\adas7\\Desktop\\BLE\\images\\test.jpg");
    // velocityContext.put("lastName", "Chavan");
    // velocityContext.put("location", "Pune");

    StringWriter stringWriter = new StringWriter();

    template.merge(velocityContext, stringWriter);

    message.setText(stringWriter.toString());

    mailSender.send(message);
  }

}
