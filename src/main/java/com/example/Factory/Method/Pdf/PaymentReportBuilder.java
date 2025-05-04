package com.example.Factory.Method.Pdf;

import com.example.Factory.Method.Domains.Payments.Payment;

public class PaymentReportBuilder {

    private Payment payment;
    private boolean includeLogo;
    private byte[] logoBytes;
    private String title;
    private boolean includePaymentDetails;
    private boolean includeUserInfo;
    private ReportOptions.Theme theme;
    private boolean includeTimestamp;
    private String footerMessage;
    private ReportOptions.Format format;

    public PaymentReportBuilder(Payment payment) {
        this.payment = payment;
    }

    public PaymentReportBuilder() {

    }

    public PaymentReportBuilder withLogo(boolean includeLogo) {
        this.includeLogo = includeLogo;
        return this;
    }

    public PaymentReportBuilder withLogoBytes(byte[] logoBytes) {
        this.logoBytes = logoBytes;
        return this;
    }

    public PaymentReportBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public PaymentReportBuilder withPaymentDetails(boolean includePaymentDetails) {
        this.includePaymentDetails = includePaymentDetails;
        return this;
    }

    public PaymentReportBuilder withUserInfo(boolean includeUserInfo) {
        this.includeUserInfo = includeUserInfo;
        return this;
    }

    public PaymentReportBuilder withTheme(ReportOptions.Theme theme) {
        this.theme = theme;
        return this;
    }

    public PaymentReportBuilder withTimestamp(boolean includeTimestamp) {
        this.includeTimestamp = includeTimestamp;
        return this;
    }

    public PaymentReportBuilder withFooterMessage(String footerMessage) {
        this.footerMessage = footerMessage;
        return this;
    }

    public PaymentReportBuilder withFormat(ReportOptions.Format format) {
        this.format = format;
        return this;
    }

    public ReportOptions build() {
        return new ReportOptions(
                includeLogo,
                logoBytes,
                title,
                includePaymentDetails,
                includeUserInfo,
                theme,
                includeTimestamp,
                footerMessage,
                format
        );
    }

    public byte[] generatePdf(PdfGeneratorService pdfGeneratorService) {
        ReportOptions options = this.build();
        return pdfGeneratorService.generateReport(payment, options);
    }
}

