package com.example.Factory.Method.Domains.Themes;

import com.example.Factory.Method.Domains.Factorys.ThemeComponentFactory;
import org.springframework.stereotype.Component;

@Component("lightThemeFactory")
public class LightThemeFactory implements ThemeComponentFactory {

    @Override
    public String getBackgroundColor() {
        return "#ffffff";
    }

    @Override
    public String getTextColor() {
        return "#000000";
    }

    @Override
    public String getButtonStyle() {
        return "background-color: #e0e0e0; color: #000;";
    }
}
