# Decisões


**Placa em minúsculas ou com espaços.**
Normalizo com `trim()` e maiúsculas, para `abc1d23` e `ABC1D23`
serem o mesmo veículo.

**Limite zero ou negativo.**
Considero a linha inválida, porque o cálculo do excesso divide
pelo limite.

**Placa vazia.**
Linha inválida.

**Linha com número de campos diferente de 4.**
Linha inválida, com o motivo informando quantos campos foram encontrados.

**Numeração das linhas inválidas.**
O cabeçalho é a linha 1, então a primeira leitura é a linha 2.
Assim o número bate com o que o gestor vê no editor ou no Excel.

**Arredondamento antes de classificar a gravidade.**
O excesso de cada leitura é arredondado para 1 casa decimal, e a
gravidade usa o valor já arredondado. Ex.: 20,04% vira 20,0% e é LEVE.

**Episódio com uma única leitura.**
Conta como episódio, com duração de 0 segundos.

**Intervalo sem sinal.**
O intervalo de 5 minutos é medido entre leituras consecutivas do
veículo, estejam elas em excesso ou não.

**Velocidade máxima do desempate no ranking.**
Uso a maior `velocidadeMaxima` entre os episódios do veículo, e não a
velocidade de leituras fora de episódio.