package com.binio;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent(value = " hello hello bum bum")
public class TranslationCommands {

    private final TranslationService service;

    @Autowired
    public TranslationCommands(TranslationService service) {
        this.service = service;
    }

    @ShellMethod("Translate text from one language to another.")
    public String translate(
            @ShellOption(defaultValue = "hello") String text,
            @ShellOption(defaultValue = "en_US") Locale from,
            @ShellOption(defaultValue = "en_US") Locale to
    ) {
        // invoke service
        return service.translate(text, from, to);
    }


}
