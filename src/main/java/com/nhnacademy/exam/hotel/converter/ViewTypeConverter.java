package com.nhnacademy.exam.hotel.converter;

import com.nhnacademy.exam.hotel.domain.ViewType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ViewTypeConverter implements AttributeConverter<ViewType, Byte> {
    @Override
    public Byte convertToDatabaseColumn(ViewType viewType) {
        return viewType == null ? null : (byte) viewType.getDbValue();
    }

    @Override
    public ViewType convertToEntityAttribute(Byte byteData) {
        return byteData == null ? null : ViewType.fromDbValue((int) byteData);
    }
}
