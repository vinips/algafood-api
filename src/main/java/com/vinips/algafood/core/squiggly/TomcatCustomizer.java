package com.vinips.algafood.core.squiggly;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

//Essa classe serve para configurar o Tomcat que vem dentro do Spring boot. 
//Com isso fazemos com que que o tomcat aceite conchetes "[]" dentro dos params que recebemos na requisição http
@Component
public class TomcatCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

    @Override
    public void customize(TomcatServletWebServerFactory factory) {
        factory.addConnectorCustomizers(connector -> connector.setAttribute("relaxedQueryChars", "[]"));
    }
    
}