export type Gravidade = 'LEVE' | 'GRAVE' | 'GRAVISSIMA';

export interface Resumo {
  linhasLidas: number;
  linhasValidas: number;
  totalEpisodios: number;
}

export interface Episodio {
  placa: string;
  inicio: string;
  fim: string;
  duracaoSegundos: number;
  velocidadeMaxima: number;
  excessoMaximoPercentual: number;
  gravidade: Gravidade;
}

export interface RankingVeiculo {
  placa: string;
  totalPontos: number;
  velocidadeMaxima: number;
  totalEpisodios: number;
}

export interface LinhaInvalida {
  numeroLinha: number;
  motivo: string;
}

export interface ResultadoAnalise {
  resumo: Resumo;
  episodios: Episodio[];
  ranking: RankingVeiculo[];
  linhasInvalidas: LinhaInvalida[];
}
