package com.pedroafonso.first_spring_app.domain;


import com.pedroafonso.first_spring_app.domain.records.Episodio;
import com.pedroafonso.first_spring_app.domain.records.Leitura;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MontadorEpisodiosTest {

    private final MontadorEpisodios montador = new MontadorEpisodios();

    private Leitura l(String placa, int minuto, int segundo, double vel, int limite) {
        return new Leitura(placa, LocalDateTime.of(2026, 10, 5, 8, minuto, segundo), vel, limite);
    }

    @Test
    void leiturasConsecutivasEmExcessoFormamUmEpisodio() {
        List<Episodio> r = montador.montar(List.of(
                l("AAA1A11", 0, 0, 80, 60),
                l("AAA1A11", 1, 0, 85, 60),
                l("AAA1A11", 2, 0, 82, 60)));

        assertEquals(1, r.size());
        assertEquals(120, r.get(0).duracaoSegundos());
        assertEquals(85, r.get(0).velocidadeMaxima());
    }
}
