package com.flamingos.osp.email;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.Set;

import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailGateway {
	private JavaMailSender mailSender;
	private VelocityEngine velocityEngine;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

	public void sendMail(final Mail mail) throws AddressException {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage message) throws Exception {
				// TODO Auto-generated method stub
				message.setFrom(new InternetAddress(mail.getMailFrom()));
				InternetAddress[] toAddresses = { new InternetAddress(
						mail.getMailTo()) };
				message.setRecipients(Message.RecipientType.TO, toAddresses);
				message.setSubject(mail.getMailSubject());

				Template template = velocityEngine.getTemplate(mail
						.getTemplateName());
				VelocityContext velocityContext = new VelocityContext();
				StringWriter stringWriter = new StringWriter();
				template.merge(velocityContext, stringWriter);
				Map<String, String> mapInlineImages = mail.getMapInlineImages();

				MimeBodyPart messageBodyPart = new MimeBodyPart();
				messageBodyPart
						.setContent(stringWriter.toString(), "text/html");

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(messageBodyPart);

				// adds inline image attachments
				if (mapInlineImages != null && mapInlineImages.size() > 0) {
					Set<String> setImageID = mapInlineImages.keySet();

					for (String contentId : setImageID) {
						MimeBodyPart imagePart = new MimeBodyPart();
						imagePart
								.setHeader("Content-ID", "<" + contentId + ">");
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

				// message.setText(stringWriter.toString());
			}
		};

		// SimpleMailMessage message = new SimpleMailMessage();
		mailSender.send(preparator);
	}

}
