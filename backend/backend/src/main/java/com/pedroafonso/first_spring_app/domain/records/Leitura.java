package com.pedroafonso.first_spring_app.domain.records;

import java.time.LocalDateTime;

public record Leitura(String placa, LocalDateTime dataHora, Double velocidadeKmh, Integer limiteKmh) {

    public boolean emExcesso() {
        return velocidadeKmh > limiteKmh + 5;
    }

    public double excessoPercentual() {
        double pct = (velocidadeKmh - limiteKmh) / limiteKmh * 100;
        return Math.round(pct * 10) / 10.0;
    }
}
