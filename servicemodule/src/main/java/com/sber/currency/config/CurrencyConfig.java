package com.sber.currency.config;

import com.sber.currency.model.ValCurs;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.File;

@Configuration
@ConfigurationPropertiesScan("com.sber.config")
@AllArgsConstructor
public class CurrencyConfig {
    private final CurrencyProperties currencyProperties;

    @Bean
    public void currencyService() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = schemaFactory.newSchema(new File(currencyProperties.getSchemaXSD()));
            Marshaller s_jaxbMarshaller = jaxbContext.createMarshaller();
            Unmarshaller s_jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            s_jaxbUnmarshaller.setSchema(schema);
            s_jaxbMarshaller.setSchema(schema);
        } catch (JAXBException | SAXException ex) {
            throw new IllegalStateException(ex);
        }
    }
}
