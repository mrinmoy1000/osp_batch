package com.flamingos.osp.util;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:osp.properties")
public class MailSend {
	/**
	 * Sends an HTML e-mail with inline images.
	 * 
	 * @param host
	 *            SMTP host
	 * @param port
	 *            SMTP port
	 * @param userName
	 *            e-mail address of the sender's account
	 * @param password
	 *            password of the sender's account
	 * @param toAddress
	 *            e-mail address of the recipient
	 * @param subject
	 *            e-mail subject
	 * @param htmlBody
	 *            e-mail content with HTML tags
	 * @param mapInlineImages
	 *            key: Content-ID value: path of the image file
	 * @throws AddressException
	 * @throws MessagingException
	 */
	@Value("${mail.smtp.host}")
	private String host;
	@Value("${mail.smtp.port}")
	private String port;
	@Value("${mail.smtp.auth}")
	private String auth;
	@Value("${mail.smtp.starttls.enable}")
	private String tls;
	@Value("${mail.smtp.socketFactory.port}")
	private String socketFactoryPort;
	@Value("${mail.smtp.socketFactory.class}")
	private String socketFactoryClass;
	@Value("${mail.smtp.socketFactory.fallback}")
	private String fallback;

	public void send(final String userName,
			final String password, String toAddress, String subject,
			String htmlBody, Map<String, String> mapInlineImages)
			throws AddressException, MessagingException {
		// sets SMTP server properties
		Properties properties = new Properties();
		// Properties properties = System.getProperties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", port);
		properties.put("mail.smtp.auth", auth);
		properties.put("mail.smtp.starttls.enable", tls);
		properties.put("mail.smtp.socketFactory.port", socketFactoryPort);
		properties.put("mail.smtp.socketFactory.class", socketFactoryClass);
		properties.put("mail.smtp.socketFactory.fallback", fallback);

		// creates a new session with an authenticator

		Session session = Session.getInstance(properties,
				new javax.mail.Authenticator() {
					@Override
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userName, password);
					}
				});

		// creates a new e-mail message
		Message msg = new MimeMessage(session);

		msg.setFrom(new InternetAddress(userName));
		InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
		msg.setRecipients(Message.RecipientType.TO, toAddresses);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

		// creates message part
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(htmlBody, "text/html");

		// creates multi-part
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

		msg.setContent(multipart);

		Transport.send(msg);
	}
}
