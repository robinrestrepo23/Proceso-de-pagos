package com.example.Factory.Method.Domains.Factorys;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ThemeFactoryProvider {
    private final Map<String, ThemeComponentFactory> factories;

    @Autowired
    public ThemeFactoryProvider(Map<String, ThemeComponentFactory> factories) {
        this.factories = factories;
    }

    public ThemeComponentFactory getFactory(String theme){
        return factories.getOrDefault(theme.toLowerCase()+"ThemeFactory", factories.get("lightFactory"));
    }

}
