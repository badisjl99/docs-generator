package com.test.v1;

import java.util.HashMap;
import java.util.Map;

public class TranslationUtil {
    private Map<String, String> translations;

    public TranslationUtil() {
        translations = new HashMap<>();
        loadTranslations();
    }

    private void loadTranslations() {
        // Load translations for each key
        translations.put("Attestation de Reussite", "Certificate of Success");
        translations.put("Ministère de l’Enseignement Supérieur et de la Recherche Scientifique", "Ministry of Higher Education and Scientific Research");
        translations.put("La Republique Tunisienne", "The Tunisian Republic");
        // Add more translations as needed
    }

    public String translate(String text) {
        return translations.getOrDefault(text, text); // Return the translated text or the original if not found
    }
}
