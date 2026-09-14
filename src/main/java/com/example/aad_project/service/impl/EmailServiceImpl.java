package com.example.aad_project.service.impl;

import com.example.aad_project.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromAddress;

    @Override
    @Async("emailExecutor")
    public void sendOrderConfirmationEmail(String toEmail, String customerName,
                                           Long orderId, String pickupAddress,
                                           String dropAddress, double amount) {
        String subject = "Order Confirmed - #" + orderId;
        String body = buildOrderConfirmationHtml(customerName, orderId, pickupAddress, dropAddress, amount);
        sendHtmlEmail(toEmail, subject, body);
    }

    @Override
    @Async("emailExecutor")
    public void sendDriverAssignmentEmail(String toEmail, String driverName,
                                          Long orderId, String pickupAddress,
                                          String dropAddress) {
        String subject = "New Delivery Assigned - Order #" + orderId;
        String body = buildDriverAssignmentHtml(driverName, orderId, pickupAddress, dropAddress);
        sendHtmlEmail(toEmail, subject, body);
    }

    @Override
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(fromAddress);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true);
            mailSender.send(message);
            logger.info("Email sent successfully to {} with subject '{}'", to, subject);
        } catch (MessagingException e) {
            logger.error("Failed to send email to {}: {}", to, e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error sending email to {}: {}", to, e.getMessage());
        }
    }

    @Override
    @Async("emailExecutor")
    public void sendDriverRegistrationEmail(String toEmail, String username) {
        String subject = "Welcome to Courier Service - Driver Account Created";
        String body = """
            <div style="font-family: Arial, sans-serif; max-width:600px; margin:auto;">
                <h2 style="color:#2c3e50;">Welcome, %s! 🚚</h2>
                <p>Your driver account has been created successfully.</p>
                <p>You can now log in and start accepting deliveries.</p>
                <p style="color:#888; font-size:12px;">This is an automated message from the Courier Delivery System.</p>
            </div>
            """.formatted(username);
        sendHtmlEmail(toEmail, subject, body);
    }

    private String buildOrderConfirmationHtml(String customerName, Long orderId,
                                              String pickup, String drop, double amount) {
        return """
            <div style="font-family: Arial, sans-serif; max-width:600px; margin:auto;">
                <h2 style="color:#2c3e50;">Order Confirmed ✅</h2>
                <p>Hi %s,</p>
                <p>Your courier order has been placed successfully.</p>
                <table style="border-collapse: collapse; width:100%%;">
                    <tr><td style="padding:8px;"><b>Order ID</b></td><td style="padding:8px;">#%d</td></tr>
                    <tr><td style="padding:8px;"><b>Pickup</b></td><td style="padding:8px;">%s</td></tr>
                    <tr><td style="padding:8px;"><b>Drop-off</b></td><td style="padding:8px;">%s</td></tr>
                    <tr><td style="padding:8px;"><b>Amount</b></td><td style="padding:8px;">Rs. %.2f</td></tr>
                </table>
                <p>We'll notify you again once a driver is assigned.</p>
                <p style="color:#888; font-size:12px;">This is an automated message from the Courier Delivery System.</p>
            </div>
            """.formatted(customerName, orderId, pickup, drop, amount);
    }

    private String buildDriverAssignmentHtml(String driverName, Long orderId,
                                             String pickup, String drop) {
        return """
            <div style="font-family: Arial, sans-serif; max-width:600px; margin:auto;">
                <h2 style="color:#2c3e50;">New Delivery Assigned 🚚</h2>
                <p>Hi %s,</p>
                <p>You have been assigned a new delivery.</p>
                <table style="border-collapse: collapse; width:100%%;">
                    <tr><td style="padding:8px;"><b>Order ID</b></td><td style="padding:8px;">#%d</td></tr>
                    <tr><td style="padding:8px;"><b>Pickup</b></td><td style="padding:8px;">%s</td></tr>
                    <tr><td style="padding:8px;"><b>Drop-off</b></td><td style="padding:8px;">%s</td></tr>
                </table>
                <p>Please check the app for full delivery details.</p>
                <p style="color:#888; font-size:12px;">This is an automated message from the Courier Delivery System.</p>
            </div>
            """.formatted(driverName, orderId, pickup, drop);
    }
}