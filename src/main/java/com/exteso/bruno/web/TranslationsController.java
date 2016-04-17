package com.exteso.bruno.web;

import java.io.IOException;
import java.util.Locale;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exteso.bruno.configuration.support.SerializableResourceBundleMessageSource;

@RestController
public class TranslationsController {
    
    //based on https://gist.github.com/rvillars/6422287
    private final SerializableResourceBundleMessageSource messageBundle;

    @Autowired
    public TranslationsController(SerializableResourceBundleMessageSource messageBundle) {
        this.messageBundle = messageBundle;
    }
    

    // TODO: add caching for prod mode
    @RequestMapping("/api/translations")
    public Properties getTranslations(Locale locale) throws IOException {
        return messageBundle.getAllProperties(locale);
    }
}
