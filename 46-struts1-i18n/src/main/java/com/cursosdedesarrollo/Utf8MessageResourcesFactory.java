package com.cursosdedesarrollo;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.PropertyMessageResourcesFactory;

public class Utf8MessageResourcesFactory extends PropertyMessageResourcesFactory {

    @Override
    public MessageResources createResources(String config) {
        return new Utf8MessageResources(this, config, this.returnNull);
    }
}
