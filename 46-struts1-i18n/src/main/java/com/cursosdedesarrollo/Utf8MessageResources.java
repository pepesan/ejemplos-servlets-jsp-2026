package com.cursosdedesarrollo;

import org.apache.struts.util.MessageResourcesFactory;
import org.apache.struts.util.PropertyMessageResources;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.PropertyResourceBundle;

public class Utf8MessageResources extends PropertyMessageResources {

    public Utf8MessageResources(MessageResourcesFactory factory, String config, boolean returnNull) {
        super(factory, config, returnNull);
    }

    @Override
    protected synchronized void loadLocale(String localeKey) {
        if (locales.get(localeKey) != null) {
            return;
        }
        locales.put(localeKey, localeKey);

        String name = config.replace('.', '/');
        if (localeKey.length() > 0) {
            name += "_" + localeKey;
        }
        name += ".properties";

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl == null) {
            cl = getClass().getClassLoader();
        }

        InputStream is = cl.getResourceAsStream(name);
        if (is == null) {
            return;
        }
        try {
            PropertyResourceBundle bundle =
                    new PropertyResourceBundle(new InputStreamReader(is, "UTF-8"));
            synchronized (messages) {
                Enumeration<String> keys = bundle.getKeys();
                while (keys.hasMoreElements()) {
                    String key = keys.nextElement();
                    messages.put(localeKey + "." + key, bundle.getString(key));
                }
            }
        } catch (IOException e) {
            log.error("loadLocale(" + localeKey + ")", e);
        } finally {
            try {
                is.close();
            } catch (IOException ignored) {
            }
        }
    }
}
