package com.karthik.projects.services;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.Properties;

@Service
public class GmailService {

    @Value("${gmail.clientId}")
    private String clientId;

    @Value("${gmail.clientSecret}")
    private String clientSecret;

    @Value("${gmail.refreshToken}")
    private String refreshToken;

    @Value("${gmail.senderEmail}")
    private String senderEmail;

    public Gmail getGmailService() throws Exception {
        GoogleCredential credential = new GoogleCredential.Builder()
                .setClientSecrets(clientId, clientSecret)
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(JacksonFactory.getDefaultInstance())
                .build()
                .setRefreshToken(refreshToken);

        credential.refreshToken();

        return new Gmail.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JacksonFactory.getDefaultInstance(),
                credential
        ).setApplicationName("GmailSenderApp").build();
    }

    public void sendEmail(String toEmail, String subject, String body) throws Exception {
        Gmail service = getGmailService();

        MimeMessage email = createEmail(toEmail, senderEmail, subject, body);
        Message message = createMessageWithEmail(email);

        service.users().messages().send("me", message).execute();
    }

    private MimeMessage createEmail(String to, String from, String subject, String bodyText) throws MessagingException {
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);

        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(from));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO, new InternetAddress(to));

        email.setSubject(subject);
        email.setText(bodyText);
        return email;
    }

    private Message createMessageWithEmail(MimeMessage emailContent) throws Exception {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        emailContent.writeTo(buffer);
        byte[] raw = buffer.toByteArray();
        String encodedEmail = Base64.getUrlEncoder().encodeToString(raw);
        Message message = new Message();
        message.setRaw(encodedEmail);  // ✅ Correct method
        return message;
    }
}

