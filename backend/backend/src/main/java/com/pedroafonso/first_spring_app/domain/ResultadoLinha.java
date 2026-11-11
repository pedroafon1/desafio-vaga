package com.pedroafonso.first_spring_app.domain;

public record ResultadoLinha(Leitura leitura, LinhaInvalida invalida) {

    public static ResultadoLinha ok(Leitura leitura) {
        return new ResultadoLinha(leitura, null);
    }

    public static ResultadoLinha erro(int numeroLinha, String motivo) {
        return new ResultadoLinha(null, new LinhaInvalida(numeroLinha, motivo));
    }

    public boolean valida() {
        return invalida == null;
    }
}
