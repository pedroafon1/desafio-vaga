import { Component, inject, signal } from '@angular/core';
import { HttpErrorResponse } from '@angular/common/http';
import { AnaliseService } from './analise.service';
import { ResultadoAnalise } from './modelos';

@Component({
  selector: 'app-root',
  templateUrl: './app.html',
  styleUrl: './app.css',
})
export class App {
  private servico = inject(AnaliseService);

  arquivo = signal<File | null>(null);
  resultado = signal<ResultadoAnalise | null>(null);
  erro = signal<string | null>(null);
  carregando = signal(false);

  aoEscolherArquivo(evento: Event) {
    const input = evento.target as HTMLInputElement;
    this.arquivo.set(input.files?.[0] ?? null);
  }

  analisar() {
    const arquivo = this.arquivo();
    if (!arquivo) {
      return;
    }

    this.carregando.set(true);
    this.erro.set(null);
    this.resultado.set(null);

    this.servico.analisar(arquivo).subscribe({
      next: (r) => {
        this.resultado.set(r);
        this.carregando.set(false);
      },
      error: (e: HttpErrorResponse) => {
        this.erro.set(e.error?.mensagem ?? 'Não foi possível analisar o arquivo.');
        this.carregando.set(false);
      },
    });
  }
}
