package com.pedroafonso.first_spring_app.domain.records;

import com.pedroafonso.first_spring_app.domain.Gravidade;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public record Episodio(
        String placa,
        LocalDateTime inicio,
        LocalDateTime fim,
        long duracaoSegundos,
        int velocidadeMaxima,
        double excessoMaximoPercentual,
        Gravidade gravidade) {

    public static Episodio de(List<Leitura> leituras) {
        Leitura primeira = leituras.get(0);
        Leitura ultima = leituras.get(leituras.size() - 1);

        int velocidadeMaxima = 0;
        double excessoMaximo = 0;
        for(Leitura leitura : leituras) {
            velocidadeMaxima = (int) Math.max(velocidadeMaxima, leitura.velocidadeKmh());
            excessoMaximo = Math.max(excessoMaximo, leitura.excessoPercentual());
        }

        long duracao = Duration.between(primeira.dataHora(), ultima.dataHora()).toSeconds();

        return new Episodio(
                primeira.placa(),
                primeira.dataHora(),
                ultima.dataHora(),
                duracao,
                velocidadeMaxima,
                excessoMaximo,
                Gravidade.de(excessoMaximo));
    }
}
