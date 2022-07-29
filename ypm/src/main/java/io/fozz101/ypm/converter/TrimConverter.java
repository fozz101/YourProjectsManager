package io.fozz101.ypm.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;


@Converter
public class TrimConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return attribute.replaceAll(" ", "");
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return dbData.replaceAll(" ", "");
    }

}
