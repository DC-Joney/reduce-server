package org.example;

import com.netflix.config.*;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.Configuration;

public class DynamicPropertyValueExampe {

    public static void main(String[] args) {

        AbstractConfiguration configInstance = ConfigurationManager.getConfigInstance();

        configInstance.setProperty("1", "2");

        System.out.println(configInstance.getString("1"));


        DynamicPropertyFactory dynamicPropertyFactory = DynamicPropertyFactory.initWithConfigurationSource(configInstance);

        dynamicPropertyFactory.

        DynamicContextualProperty<String> contextualProperty = dynamicPropertyFactory.getContextualProperty("1", new String("111"));


        contextualProperty.addCallback(new Runnable() {
            @Override
            public void run() {
                System.out.println(contextualProperty.getValue());
                System.out.println("callback.......");
            }
        });


        System.out.println(contextualProperty.getValue());
        configInstance.setProperty("1", "3");
        System.out.println(contextualProperty.getValue());
    }
}
