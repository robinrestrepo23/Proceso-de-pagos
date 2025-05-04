package com.example.Factory.Method.Domains.Themes;

import com.example.Factory.Method.Domains.Factorys.ThemeComponentFactory;
import org.springframework.stereotype.Component;

@Component("darkThemeFactory")
public class DarkThemeFactory implements ThemeComponentFactory {
    @Override
    public String getBackgroundColor() {
        return "#121212";
    }

    @Override
    public String getTextColor() {
        return "#ffffff";
    }

    @Override
    public String getButtonStyle() {
        return "background-color: #333333; color: #fff;";
    }
}
