# Análise de excesso de velocidade
 
Aplicação que recebe um arquivo CSV com leituras de velocidade de uma frota e devolve:
 
- **episódios de excesso** (várias leituras seguidas acima do limite viram uma ocorrência só);
- **ranking de veículos** por pontos de gravidade;
- **linhas inválidas**, com o número da linha no arquivo e o motivo;
- um **resumo** (linhas lidas, linhas válidas, total de episódios).
Não usa banco de dados: o arquivo entra, o resultado sai.
 
## Tecnologias
 
- **Backend:** Java 21, Spring Boot 3+, Maven, JUnit 5
- **Frontend:** Angular (standalone components), TypeScript
## Estrutura do repositório
 
```
desafio-vaga/
  backend/
    backend/     # projeto Spring Boot (onde está o pom.xml)
  frontend/      # projeto Angular
  README.md
  DECISOES.md
```
 
## Pré-requisitos
 
- JDK 17 ou superior (testado com 21)
- Maven 3.9+
- Node.js LTS (testado com 22) e npm
## Como rodar
 
Abra **dois terminais**: um para o backend e outro para o frontend.
 
### 1. Backend (porta 8080)
 
```bash
cd backend/backend
mvn spring-boot:run
```
 
### 2. Frontend (porta 4200)
 
```bash
cd frontend
npm install --legacy-peer-deps
npm start
```
 
> A flag `--legacy-peer-deps` foi necessária no ambiente de desenvolvimento
> para contornar um erro do npm (`Cannot read properties of null (reading 'edgesOut')`).
> Se o `npm install` simples funcionar para você, pode usá-lo.
 
Abra <http://localhost:4200>, escolha o arquivo `leituras.csv` e clique em **Analisar**.
 
> O frontend chama `http://localhost:8080`. O backend precisa estar rodando antes de enviar o arquivo.
 
## Como rodar os testes
 
```bash
cd backend/backend
mvn test
```
 
Os testes cobrem as regras de negócio: leitura e validação de linhas, limite exato de
5 km/h, intervalo exato de 5 minutos, arquivo fora de ordem, excesso máximo,
gravidade, ranking com desempates e arquivo vazio ou com cabeçalho errado.
 
## API
 
### `POST /api/analises`
 
Recebe `multipart/form-data` com o campo `arquivo` (o CSV).
 
```bash
curl -X POST -F "arquivo=@leituras.csv" http://localhost:8080/api/analises
```
 
No PowerShell, use `curl.exe` no lugar de `curl`.
 
**Formato do arquivo** (separado por `;`, com cabeçalho):
 
```
placa;dataHora;velocidadeKmh;limiteKmh
ABC1D23;2026-10-05T08:00:00;72;60
```
 
**Resposta 200:**
 
```json
{
  "resumo": { "linhasLidas": 43, "linhasValidas": 34, "totalEpisodios": 11 },
  "episodios": [
    {
      "placa": "ABC1D23",
      "inicio": "2026-10-05T08:00:00",
      "fim": "2026-10-05T08:02:00",
      "duracaoSegundos": 120,
      "velocidadeMaxima": 80,
      "excessoMaximoPercentual": 55.0,
      "gravidade": "GRAVISSIMA"
    }
  ],
  "ranking": [
    { "placa": "GHI3J45", "totalPontos": 6, "velocidadeMaxima": 100.0, "totalEpisodios": 2 }
  ],
  "linhasInvalidas": [
    { "numeroLinha": 4, "motivo": "Limite deve ser maior que zero: 0" }
  ]
}
```
 
**Resposta 400:** arquivo vazio ou cabeçalho diferente do esperado.
 
```json
{ "mensagem": "Cabeçalho inválido. Esperado: placa;dataHora;velocidadeKmh;limiteKmh" }
```
 
## Regras de negócio (resumo)
 
- **Leitura em excesso:** `velocidade > limite + 5 km/h` (exatamente 5 km/h acima não conta).
- **Episódio:** leituras em excesso consecutivas do mesmo veículo. Termina com uma leitura
  sem excesso ou quando o intervalo até a próxima leitura é **maior que 5 minutos**
  (exatamente 5 minutos não encerra).
- **Excesso (%):** `(velocidade - limite) / limite * 100`, com 1 casa decimal. O excesso máximo
  do episódio é o maior percentual entre suas leituras.
- **Gravidade:**
  | Excesso máximo | Gravidade | Pontos |
  |---|---|---|
  | até 20% | LEVE | 1 |
  | acima de 20% até 50% | GRAVE | 3 |
  | acima de 50% | GRAVISSIMA | 5 |
- **Ranking:** total de pontos (maior primeiro); empate pela maior velocidade em episódio;
  depois ordem alfabética da placa.
As decisões sobre situações que o enunciado não cobria estão em [DECISOES.md](DECISOES.md).
 
## Frontend
 
- Envio do arquivo e tratamento de erro quando o backend recusa o arquivo.
- Resumo, ranking e tabela de episódios.
- Filtro de episódios por gravidade, feito no próprio navegador (sem nova chamada ao backend).
- Lista de linhas inválidas.
