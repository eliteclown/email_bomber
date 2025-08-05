package com.karthik.projects.utils;

import com.karthik.projects.services.GeminiService;
import com.karthik.projects.services.GmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BulkEmailRunner implements CommandLineRunner {


    private final GmailService gmailService;


    private final GeminiService geminiService;

//    @Override
//    public void run(String... args) throws Exception {
//        String recipient = "karthik21cs088@gmail.com";
//
//        for (int i = 1; i <= 40; i++) {
//            String subject = "AI Email #" + i;
//            String body = geminiService.generateEmailBody(i);
//
//            gmailService.sendEmail(recipient, subject, body);
//            System.out.println("Sent email #" + i);
//            Thread.sleep(3000);
//        }
//
//        System.out.println("All emails sent.");
//    }

    @Override
    public void run(String... args) {
        String recipient = "karthik21cs088@gmail.com";

        for (int i = 1; i <= 40; i++) {
            try {
                String subject = "AI Email #" + i;
                System.out.println("Generating email body for #" + i);
                String body = geminiService.generateEmailBody(i);

                System.out.println("Sending email #" + i);
                gmailService.sendEmail(recipient, subject, body);
                System.out.println("Sent email #" + i);

                Thread.sleep(3000);
            } catch (Exception e) {
                System.err.println("Failed to send email #" + i);
                e.printStackTrace();
                break;  // You can remove this if you want to keep going after errors
            }
        }

        System.out.println("Email sending process completed.");
    }
}

