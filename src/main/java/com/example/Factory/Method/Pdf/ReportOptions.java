package com.example.Factory.Method.Pdf;


public class ReportOptions implements Cloneable{
    public enum Theme {
        LIGHT,
        DARK,
        BLUE,
        CUSTOM
    }

    public enum Format {
        PDF,
        A4,
        LETTER, TEXT
    }

    private final boolean includeLogo;
    private byte[] logoBytes;
    private final String title;
    private final boolean includePaymentDetails;
    private final boolean includeUserInfo;
    private final Theme theme;
    private final boolean includeTimestamp;
    private final String footerMessage;
    private final Format format;

    public ReportOptions(boolean includeLogo, byte[] logoBytes,
                         String title,
                         boolean includePaymentDetails,
                         boolean includeUserInfo,
                         Theme theme,
                         boolean includeTimestamp,
                         String footerMessage,
                         Format format) {
        this.includeLogo = includeLogo;
        this.logoBytes = logoBytes;
        this.title = title;
        this.includePaymentDetails = includePaymentDetails;
        this.includeUserInfo = includeUserInfo;
        this.theme = theme;
        this.includeTimestamp = includeTimestamp;
        this.footerMessage = footerMessage;
        this.format = format;
    }

    public boolean isIncludeLogo() {
        return includeLogo;
    }
    public byte[] getLogoBytes() {
        return logoBytes;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIncludePaymentDetails() {
        return includePaymentDetails;
    }

    public boolean isIncludeUserInfo() {
        return includeUserInfo;
    }

    public Theme getTheme() {
        return theme;
    }

    public boolean isIncludeTimestamp() {
        return includeTimestamp;
    }

    public String getFooterMessage() {
        return footerMessage;
    }

    public Format getFormat() {
        return format;
    }
    @Override
    public ReportOptions clone() {
        try {
            ReportOptions clone = (ReportOptions) super.clone();
            if (this.logoBytes != null) {
                clone.logoBytes = this.logoBytes.clone();
            }
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Error al clonar ReportOptions", e);
        }
    }
}



