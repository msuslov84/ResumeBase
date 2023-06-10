package com.suslov.basejava.web;

public enum WebTheme {
    DARK("dark"),
    LIGHT("light");

    private final String name;

    WebTheme(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

