package com.server.cx.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageHelp {

    private ReloadableResourceBundleMessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = (ReloadableResourceBundleMessageSource) messageSource;
    }

    public String getZhMessage(String messageName) {
        String messageValue = messageSource.getMessage(messageName, null, Locale.CHINA);
        return messageValue;
    }

}
