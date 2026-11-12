package com.pedroafonso.first_spring_app.domain;

import com.pedroafonso.first_spring_app.domain.records.*;
import com.pedroafonso.first_spring_app.exceptions.ArquivoInvalidoException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class AnalisadorArquivo {

    private static final String CABECALHO_ESPERADO = "placa;dataHora;velocidadeKmh;limiteKmh";

    private final ParserLinha parser = new ParserLinha();
    private final MontadorEpisodios montador = new MontadorEpisodios();
    private final CalculadoraRanking calculadora = new CalculadoraRanking();

    public ResultadoAnalise analisar(Reader reader) {
        List<Leitura> leituras = new ArrayList<>();
        List<LinhaInvalida> linhasInvalidas = new ArrayList<>();
        int linhasLidas = 0;

        try (BufferedReader br = new BufferedReader(reader)) {
            String cabecalho = br.readLine();
            if(cabecalho == null || cabecalho.isBlank()) {
                throw new ArquivoInvalidoException("Cabeçalho inválido");
            }
            validarCabecalho(cabecalho);

            int numeroLinha = 1; // o cabeçalho foi a primeira linha
            String linha;
            while ((linha = br.readLine()) != null) {
                numeroLinha++;
                linhasLidas++;

                ResultadoLinha resultadoLinha = parser.parse(numeroLinha, linha);
                if (resultadoLinha.valida()) {
                    leituras.add(resultadoLinha.leitura());
                } else {
                    linhasInvalidas.add(resultadoLinha.invalida());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Episodio> episodios = montador.montar(leituras);
        List<RankingVeiculo> ranking = calculadora.calcular(episodios);
        Resumo resumo = new Resumo(linhasLidas, linhasInvalidas.size(), episodios.size());

        return new ResultadoAnalise(resumo, episodios, ranking, linhasInvalidas);
    }

    private void validarCabecalho(String cabecalho) {
        String limpo = cabecalho.replace("\uFEFF", "").trim();
        if (!limpo.equals(CABECALHO_ESPERADO)) {
            throw new ArquivoInvalidoException(
                    "Cabeçalho inválido. Esperado: " + CABECALHO_ESPERADO);
        }
    }
}
