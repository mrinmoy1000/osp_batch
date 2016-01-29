package com.flamingos.osp.email;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.flamingos.osp.util.AppConstants;
import com.flamingos.osp.util.EncryptionUtil;

public class EmailGateway {
  private JavaMailSender mailSender;
  private VelocityEngine velocityEngine;

  @Value("${osp.batch.newsletter.max.mail.count.per.batch}")
  private int MAX_MAIL_COUNT_PER_BATCH;

  public void setMailSender(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }

  public void sendMail(final Mail mail) throws Exception {

    MimeMessagePreparator preparator = new MimeMessagePreparator() {

      @SuppressWarnings({"unchecked", "rawtypes"})
      public void prepare(MimeMessage message) throws Exception {
        message.setFrom(new InternetAddress(mail.getMailFrom()));
        InternetAddress[] toAddresses = {new InternetAddress(mail.getMailTo())};
        message.setRecipients(Message.RecipientType.TO, toAddresses);
        message.setSubject(mail.getMailSubject());
        Map model = new HashMap();
        model.put(AppConstants.VTEMP_QUALIFIER, mail);
        String htmlBody =
            VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mail.getTemplateName(),
                "UTF-8", model);
        Map<String, String> mapInlineImages = mail.getMapInlineImages();

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
          Set<String> setImageID = mapInlineImages.keySet();

          for (String contentId : setImageID) {
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setHeader("Content-ID", "<" + contentId + ">");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            String imageFilePath = mapInlineImages.get(contentId);
            try {
              imagePart.attachFile(imageFilePath);
            } catch (IOException ex) {
              ex.printStackTrace();
            }
            multipart.addBodyPart(imagePart);
          }
        }
        message.setContent(multipart);
      }
    };

    mailSender.send(preparator);
  }

  /**
   * This method sends emails in a bulk.
   * 
   * @param mails
   */
  public void sendBatchMail(List<Mail> mails) throws Exception {
    if (null != mails && !mails.isEmpty()) {
      List<MimeMessagePreparator> lstMimeMessage = new ArrayList<MimeMessagePreparator>();
      for (Mail oMail : mails) {
        MimeMessagePreparator prepartor = createMimeMessagePreparator(oMail);
        lstMimeMessage.add(prepartor);
        if (lstMimeMessage.size() >= MAX_MAIL_COUNT_PER_BATCH) {
          mailSender.send(lstMimeMessage.toArray(new MimeMessagePreparator[lstMimeMessage.size()]));
          lstMimeMessage.clear();
        }
      }
      if (!lstMimeMessage.isEmpty()) {
        mailSender.send(lstMimeMessage.toArray(new MimeMessagePreparator[lstMimeMessage.size()]));
      }
    }
  }

  public MimeMessagePreparator createMimeMessagePreparator(final Mail mail) {

    MimeMessagePreparator preparator = new MimeMessagePreparator() {

      @SuppressWarnings({"unchecked", "rawtypes"})
      public void prepare(MimeMessage message) throws Exception {

        message.setFrom(new InternetAddress(mail.getMailFrom()));
        InternetAddress[] toAddresses = {new InternetAddress(mail.getMailTo())};
        message.setRecipients(Message.RecipientType.TO, toAddresses);
        message.setSubject(mail.getMailSubject());
        Map model = new HashMap();
        model.put(AppConstants.VTEMP_QUALIFIER, mail);
        String htmlBody =
            VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, mail.getTemplateName(),
                "UTF-8", model);
        Map<String, String> mapInlineImages = mail.getMapInlineImages();

        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");

        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
          Set<String> setImageID = mapInlineImages.keySet();

          for (String contentId : setImageID) {
            MimeBodyPart imagePart = new MimeBodyPart();
            imagePart.setHeader("Content-ID", "<" + contentId + ">");
            imagePart.setDisposition(MimeBodyPart.INLINE);

            String imageFilePath = mapInlineImages.get(contentId);
            try {
              imagePart.attachFile(imageFilePath);
            } catch (IOException ex) {
              ex.printStackTrace();
            }
            multipart.addBodyPart(imagePart);
          }
        }
        message.setContent(multipart);
      }
    };

    return preparator;
  }

}
