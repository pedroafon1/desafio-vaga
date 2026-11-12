package com.pedroafonso.first_spring_app.domain.records;

import java.util.List;

public record ResultadoAnalise(
        Resumo resumo,
        List<Episodio> episodios,
        List<RankingVeiculo> ranking,
        List<LinhaInvalida> linhasInvalidas) {
}
