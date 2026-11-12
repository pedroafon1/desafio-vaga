package com.pedroafonso.first_spring_app.domain;

import com.pedroafonso.first_spring_app.domain.records.Episodio;
import com.pedroafonso.first_spring_app.domain.records.RankingVeiculo;

import java.util.*;

public class CalculadoraRanking {

    public List<RankingVeiculo> calcular(List<Episodio> episodios) {
        Map<String, List<Episodio>> porPlaca = new HashMap<>();
        for(Episodio e : episodios) {
            porPlaca.computeIfAbsent(e.placa(), k -> new ArrayList<>()).add(e);
        }

        List<RankingVeiculo> ranking = new ArrayList<>();
        for(Map.Entry<String, List<Episodio>> entrada : porPlaca.entrySet()) {
            int pontos = 0;
            double velocidadeMaxima = 0;
            for (Episodio episodio : entrada.getValue()) {
                pontos += episodio.gravidade().pontos();
                velocidadeMaxima = Math.max(velocidadeMaxima, episodio.velocidadeMaxima());
            }
            ranking.add(new RankingVeiculo(entrada.getKey(), pontos, velocidadeMaxima, entrada.getValue().size()));
        }

        ranking.sort(Comparator
                .comparingInt(RankingVeiculo::totalPontos).reversed()
                .thenComparing(Comparator.comparingDouble(RankingVeiculo::velocidadeMaxima).reversed())
                .thenComparing(RankingVeiculo::placa));

        return ranking;
    }
}
