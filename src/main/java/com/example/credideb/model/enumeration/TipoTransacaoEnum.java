package com.example.credideb.model.enumeration;

import lombok.Getter;

@Getter
public enum TipoTransacaoEnum {

    CREDITO("C"),
    DEBITO("D");

    private final String codigo;

    TipoTransacaoEnum(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static TipoTransacaoEnum fromCodigo(String codigo) {
        for (TipoTransacaoEnum tipo : TipoTransacaoEnum.values()) {
            if (tipo.getCodigo().equals(codigo)) {
                return tipo;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + codigo);
    }
}
