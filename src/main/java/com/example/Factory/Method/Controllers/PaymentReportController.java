package com.example.Factory.Method.Controllers;

import com.example.Factory.Method.Domains.Payments.Payment;
import com.example.Factory.Method.Pdf.PaymentReportBuilder;
import com.example.Factory.Method.Pdf.PdfGeneratorService;
import com.example.Factory.Method.Pdf.ReportOptions;
import com.example.Factory.Method.Services.PaymentService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
@RequestMapping("/api/pdf")
public class PaymentReportController {

    private final PdfGeneratorService pdfGeneratorService;
    private final PaymentService paymentService;

    public PaymentReportController(PdfGeneratorService pdfGeneratorService, PaymentService paymentService) {
        this.pdfGeneratorService = pdfGeneratorService;
        this.paymentService = paymentService;
    }

    @PostMapping(value = "/generate", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> generatePaymentReport(
            @RequestParam Long paymentId,
            @RequestParam(defaultValue = "Reporte de Pago") String title,
            @RequestParam(defaultValue = "false") boolean includeLogo,
            @RequestParam(required = false) MultipartFile logo,
            @RequestParam(defaultValue = "true") boolean includePaymentDetails,
            @RequestParam(defaultValue = "false") boolean includeUserInfo,
            @RequestParam(defaultValue = "LIGHT") String reportTheme,
            @RequestParam(defaultValue = "true") boolean includeTimestamp,
            @RequestParam(defaultValue = "Gracias por su compra") String footerMessage,
            @RequestParam(defaultValue = "A4") String format
    ) throws IOException {

        Payment payment = paymentService.getPaymentById(paymentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pago no encontrado"));

        byte[] logoBytes = (includeLogo && logo != null && !logo.isEmpty()) ? logo.getBytes() : null;

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
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("reporte_pago_" + paymentId + ".pdf").build());

        return new ResponseEntity<>(pdf, headers, HttpStatus.OK);
    }
}

