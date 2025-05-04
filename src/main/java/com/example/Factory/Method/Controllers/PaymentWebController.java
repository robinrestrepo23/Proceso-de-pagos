package com.example.Factory.Method.Controllers;

import com.example.Factory.Method.Domains.Factorys.*;
import com.example.Factory.Method.Domains.Notifications.*;
import com.example.Factory.Method.Domains.Payments.*;
import com.example.Factory.Method.Pdf.*;
import com.example.Factory.Method.Services.*;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.result.view.RedirectView;

import java.io.IOException;

@Controller
public class PaymentWebController {

    @Autowired private PaymentProcessorFactory paymentProcessorFactory;
    @Autowired private ThemeFactoryProvider themeFactoryProvider;
    @Autowired private NotificationFactory notificationFactory;
    @Autowired private EmailSenderService emailSenderService;
    @Autowired private SmsSenderService smsSenderService;
    @Autowired private WhatsAppSenderService whatsAppSenderService;
    @Autowired private PdfGeneratorService pdfGeneratorService;

    private Payment payment;

    @GetMapping("/")
    public RedirectView redirectToPayment() {
        return new RedirectView("/payment");
    }

    @GetMapping("/payment")
    public String showForm(Model model) {
        model.addAttribute("bgColor", "#ffffff");
        model.addAttribute("textColor", "#000000");
        model.addAttribute("buttonStyle", "background-color: blue; color: white;");
        return "payment";
    }

    @PostMapping("/process-payment")
    public String processPayment(
            @RequestParam String paymentMethod,
            @RequestParam double amount,
            @RequestParam String theme,
            @RequestParam String notificationType,
            @RequestParam String recipient,
            @RequestParam(required = false) String subject,
            @RequestParam(required = false) String body,
            @RequestParam(required = false) int priority,
            Model model) {

        try {

            PaymentProcessor processor = paymentProcessorFactory.getPaymentProcessor(paymentMethod);
            double result = processor.processPayment(amount);

            String finalBody = buildNotificationBody(paymentMethod, amount, result, body);
            String finalSubject = (subject == null || subject.isBlank()) ? "Confirmación de pago" : subject;

            Notification notification = notificationFactory.createNotification(
                    notificationType, recipient, finalSubject, finalBody, priority
            );
            sendNotification(notificationType, notification, model);

            this.payment = new Payment(paymentMethod, amount, result, recipient, "Exitoso");

        } catch (Exception e) {
            model.addAttribute("mensaje", "Pago procesado, pero error al enviar la notificación: " + e.getMessage());
        }

        return "payment";
    }

    private String buildNotificationBody(String method, double amount, double result, String userMessage) {
        StringBuilder sb = new StringBuilder();
        sb.append("✅ Detalles del pago:\n")
                .append("- Método de pago: ").append(method).append("\n")
                .append("- Valor: $").append(amount).append("\n")
                .append("- Valor final: $").append(result).append("\n")
                .append("- Resultado: Pago exitoso\n\n");

        if (userMessage != null && !userMessage.isBlank()) {
            sb.append("📣 Mensaje adicional:\n").append(userMessage);
        }

        return sb.toString();
    }

    private void sendNotification(String type, Notification notification, Model model) throws MessagingException {
        switch (type.toLowerCase()) {
            case "email" -> {
                if (notification instanceof EmailNotification email) {
                    emailSenderService.sendEmail(email);
                    model.addAttribute("mensaje", "Pago procesado y correo enviado correctamente.");
                }
            }
            case "sms" -> {
                if (notification instanceof SmsNotification sms) {
                    smsSenderService.sendSms(sms);
                    model.addAttribute("mensaje", "Pago procesado y SMS enviado correctamente.");
                }
            }
            case "whatsapp" -> {
                if (notification instanceof WhatsappNotification wa) {
                    whatsAppSenderService.sendWhatsApp(wa);
                    model.addAttribute("mensaje", "Pago procesado y WhatsApp enviado correctamente.");
                }
            }
            default -> model.addAttribute("mensaje", "Pago procesado, pero tipo de notificación no reconocido.");
        }
    }

    @PostMapping("/generate-pdf")
    public ResponseEntity<byte[]> generateReport(
            @RequestParam(name = "includeLogo", defaultValue = "false") boolean includeLogo,
            @RequestParam(name = "logo", required = false) MultipartFile logoFile,
            @RequestParam(defaultValue = "Reporte de Pago") String title,
            @RequestParam(defaultValue = "false") boolean includePaymentDetails,
            @RequestParam(defaultValue = "false") boolean includeUserInfo,
            @RequestParam(defaultValue = "LIGHT") String reportTheme,
            @RequestParam(defaultValue = "false") boolean includeTimestamp,
            @RequestParam(defaultValue = "") String footerMessage,
            @RequestParam(defaultValue = "A4") String format) throws IOException {

        if (payment == null) return ResponseEntity.badRequest().build();

        byte[] logoBytes = (includeLogo && logoFile != null && !logoFile.isEmpty()) ? logoFile.getBytes() : null;

        ReportOptions options = new PaymentReportBuilder()
                .withLogo(includeLogo)
                .withLogoBytes(logoBytes)
                .withTitle(title)
                .withPaymentDetails(includePaymentDetails)
                .withUserInfo(includeUserInfo)
                .withTheme(ReportOptions.Theme.valueOf(reportTheme.toUpperCase()))
                .withTimestamp(includeTimestamp)
                .withFooterMessage(footerMessage)
                .withFormat(ReportOptions.Format.valueOf(format.toUpperCase()))
                .build();

        byte[] pdf = pdfGeneratorService.generateReport(payment, options);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.attachment().filename("reporte_pago.pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}

