package com.example.Factory.Method.Pdf;

import com.example.Factory.Method.Domains.Payments.Payment;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;


import java.awt.*;
import java.io.FileOutputStream;
import java.util.Date;


@Service
public class PdfGeneratorService {

    public byte[] generateReport(Payment payment, ReportOptions options) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(
                    options.getFormat() == ReportOptions.Format.LETTER ? PageSize.LETTER : PageSize.A4
            );

            PdfWriter.getInstance(document, out);
            document.open();

            // Tema de colores
            Color backgroundColor = options.getTheme() == ReportOptions.Theme.DARK ? new Color(30, 30, 30) : Color.WHITE;
            Color textColor = options.getTheme() == ReportOptions.Theme.DARK ? Color.WHITE : Color.BLACK;

            Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, textColor);
            Font normalFont = new Font(Font.HELVETICA, 12, Font.NORMAL, textColor);

            // Logo
            if (options.isIncludeLogo()) {
                try {
                    Image logo = Image.getInstance("classpath:/static/logo.png");
                    logo.scaleToFit(80, 80);
                    logo.setAlignment(Image.ALIGN_RIGHT);
                    document.add(logo);
                } catch (Exception e) {
                    System.err.println("No se encontró el logo: " + e.getMessage());
                }
            }

            // Título
            if (options.getTitle() != null && !options.getTitle().isBlank()) {
                Paragraph title = new Paragraph(options.getTitle(), titleFont);
                title.setSpacingAfter(20);
                document.add(title);
            }

            // Detalles del Pago (esto es siempre obligatorio)
            Paragraph detailsHeader = new Paragraph("Detalles del Pago", titleFont);
            detailsHeader.setSpacingAfter(10);
            document.add(detailsHeader);

            document.add(new Paragraph("ID de Transacción: " + payment.getTransactionId(), normalFont));
            document.add(new Paragraph("Monto: $" + payment.getAmount(), normalFont));
            document.add(new Paragraph("Monto final: $" + payment.getAmountFinal(), normalFont));
            document.add(new Paragraph("Método de Pago: " + payment.getPaymentMethod(), normalFont));
            document.add(new Paragraph("Estado: " + payment.getStatus(), normalFont));
            document.add(new Paragraph(" ", normalFont));

            // Información del usuario (opcional)
            if (options.isIncludeUserInfo()) {
                Paragraph userHeader = new Paragraph("Información del Usuario", titleFont);
                userHeader.setSpacingBefore(15);
                document.add(userHeader);

                document.add(new Paragraph("User: " + payment.getUsername(), normalFont));
                document.add(new Paragraph(" ", normalFont));
            }

            // Timestamp
            if (options.isIncludeTimestamp()) {
                Paragraph timestamp = new Paragraph("Fecha y Hora de Generación: " + new Date(), normalFont);
                timestamp.setSpacingBefore(20);
                document.add(timestamp);
            }

            // Footer personalizado
            if (options.getFooterMessage() != null && !options.getFooterMessage().isBlank()) {
                Paragraph footer = new Paragraph(options.getFooterMessage(), normalFont);
                footer.setAlignment(Element.ALIGN_CENTER);
                footer.setSpacingBefore(30);
                document.add(footer);
            }

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}