package com.bay.ms.notification.services.impl;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.bay.common.dto.AttachmentDTO;
import com.bay.common.dto.EmailDTO;
import com.bay.common.exceptions.CustomException;
import com.bay.ms.notification.services.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void send(EmailDTO email) throws CustomException {
		LOGGER.debug("Datos de entrada para enviar el correo: {}", email);
		try {
			if (email.getAttachment() != null && !email.getAttachment().isEmpty()) {
				sendEmailWithAttachment(email);
			} else {
				sendEmail(email);
			}
		} catch (Exception e) {
			LOGGER.error("ERROR generado al enviar el correo.", e);
			throw new CustomException(e.getMessage(), e);
		}
	}

	void sendEmail(EmailDTO email) throws MessagingException, IOException {
		// StringBuilder b = new StringBuilder();
		// list.forEach(b::append);
		String to = email.getTo().stream().map(e -> e.toString()).reduce(";", String::concat);
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody(), email.isHtml());
		javaMailSender.send(msg);
	}

	void sendEmailWithAttachment(EmailDTO email) throws MessagingException, IOException {
		String to = email.getTo().stream().map(e -> e.toString()).reduce(";", String::concat);
		MimeMessage msg = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(msg, true);
		helper.setTo(to);
		helper.setSubject(email.getSubject());
		helper.setText(email.getBody(), email.isHtml());
		if (email.getAttachment() != null && !email.getAttachment().isEmpty()) {
			for (AttachmentDTO attachment : email.getAttachment()) {
				// javax.activation.DataSource dataSource = new
				// ByteArrayDataSource(attachment.getBytes(), "application/pdf");
				// helper.addAttachment("testattachment", dataSource);
				final InputStreamSource atta = new ByteArrayResource(attachment.getBytes());
				// attachment.getName() debe tener la extension
				helper.addAttachment(attachment.getName(), atta);
			}
		}
		javaMailSender.send(msg);
	}
}
