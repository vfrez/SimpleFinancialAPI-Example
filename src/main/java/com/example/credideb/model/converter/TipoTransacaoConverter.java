package com.example.credideb.model.converter;

import com.example.credideb.model.enumeration.TipoTransacaoEnum;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = false)
public class TipoTransacaoConverter implements AttributeConverter<TipoTransacaoEnum, String> {

    @Override
    public String convertToDatabaseColumn(TipoTransacaoEnum tipoTransacaoEnum) {
        if (tipoTransacaoEnum == null) {
            return null;
        }
        return tipoTransacaoEnum.getCodigo();
    }

    @Override
    public TipoTransacaoEnum convertToEntityAttribute(String codigo) {
        if (codigo == null) {
            return null;
        }
        return TipoTransacaoEnum.fromCodigo(codigo);
    }
}
