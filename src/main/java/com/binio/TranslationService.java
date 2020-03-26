package com.binio;

import java.util.Locale;

import org.springframework.stereotype.Service;

@Service
public class TranslationService {
    public String translate(String word, Locale a, Locale b){
        return "Dzien dobry " + word;
    }
}
