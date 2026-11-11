package com.pedroafonso.first_spring_app.domain;

import java.time.LocalDateTime;

public record Leitura(String placa, LocalDateTime dataHora, Double velocidadeKmh, Integer limiteKmh) {
}
