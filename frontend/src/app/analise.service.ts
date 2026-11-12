import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ResultadoAnalise } from './modelos';

@Injectable({ providedIn: 'root' })
export class AnaliseService {
  private http = inject(HttpClient);

  analisar(arquivo: File): Observable<ResultadoAnalise> {
    const form = new FormData();
    form.append('arquivo', arquivo);
    return this.http.post<ResultadoAnalise>('http://localhost:8080/api/analises', form);
  }
}
