package com.pedroafonso.first_spring_app.domain;

public enum Gravidade {
    LEVE(1),
    GRAVE(2),
    GRAVISSIMA(5);

    private final int pontos;

    private Gravidade(int pontos) {
        this.pontos = pontos;
    }

    public int pontos() {
        return pontos;
    }

    public static Gravidade de(double excessoPercentual) {
        if(excessoPercentual > 50) {
            return GRAVISSIMA;
        }

        if(excessoPercentual > 20) {
            return GRAVE;
        }

        return LEVE;
    }
}
