package com.adt.hrms.service.impl;

import com.adt.hrms.util.Util;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailService {

	private static final Logger log = LogManager.getLogger(EmailService.class);

	@Value("${spring.mail.username}")
	private String sender;

	@Value("${spring.mail.cc}")
	private String ccEmail;

	@Autowired
	private JavaMailSender javaMailSender;

	public String sendEmail() {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("spyavenger55@gmail.com");
		msg.setTo("spyavenger55@gmail.com");
		msg.setSubject("Test Subject");
		msg.setText("Test Body");

		javaMailSender.send(msg);

		return "Mail Sent Successfully";
	}

	public void sendEmail(String email, String firstName, List<String> documentList) {

		String messageTemplate = Util.DOCUMENT_UPLOAD_REMINDER;

		String message = messageTemplate.replace("[Name]", firstName != null ? firstName : "");

		StringBuilder documentSection = new StringBuilder();
		if (documentList != null && !documentList.isEmpty()) {
			for (int i = 0; i < documentList.size(); i++) {
				documentSection.append(documentList.get(i));
				if (i < documentList.size() - 1) {
					documentSection.append(", ");
				}
			}
		} else {
			documentSection.append("No documents to upload at this time.");
		}

		message = message.replace("[valuesList]", documentSection.toString());
		message = message.replace("[Your Name]", "Team HR");
		message = message.replace("[Company Name]", "Alphadot Technologies");

		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper mimeMessageHelper;
		try {
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setCc(ccEmail);
			mimeMessageHelper.setText(message, false);
			mimeMessageHelper.setSubject("Document Upload Reminder");

			javaMailSender.send(mimeMessage);
			log.info("Document upload reminder email sent successfully to: " + email);
		} catch (MessagingException e) {
			log.error("Failed to send document upload reminder email to: " + email, e);
		}
	}
}
