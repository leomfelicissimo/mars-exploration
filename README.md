# Mars Exploration API

API REST criada para simulação de exploração espacial através do envio de sondas. Lance uma sonda dentro de um plano cartesiano e movimente-a através do envio de comando textual.

## Começando 

Instruções para obter uma cópia do projeto sendo executada em sua máquina local para desenvolvimento ou testes. Notas sobre deploy serão adicionadas apenas após aprovação. ;) hehehe

### Prerequisitos

Coisas que você precisa garantir que estejam instaladas localmente antes de executar a API.

- [Java 8](https://www.oracle.com/technetwork/pt/java/javase/downloads/jre8-downloads-2133155.html): JRE do Java para execução de aplicações que executam na JVM.
- [SBT](https://www.scala-sbt.org/download.html): Ferramenta de build para compilar e executar o código Scala.

Para garantir que o java esteja instalado corretamente execute o seguinte comando:
```
$ java -version
java version "1.8.0_152"
Java(TM) SE Runtime Environment (build 1.8.0_152-b16)
Java HotSpot(TM) 64-Bit Server VM (build 25.152-b16, mixed mode)
```

Para garantir que o sbt esteja instalado corretamente execute o seguinte comando:
```
$ sbt sbtVersion
[info] Loading settings for project global-plugins from metals.sbt ...
[info] Loading global plugins from /Users/leonardo/.sbt/1.0/plugins
[info] Loading project definition from /Users/leonardo/project
[info] Set current project to leonardo (in build file:/Users/leonardo/)
[info] 1.2.6
```

### Executando

Uma vez que você tenha todos os pré-requisitos instalados e obtido o código fonte corretamente, vá até o diretório da aplicação e execute:

```
(mars-exploration) $ sbt run
```
Você verá o seguinte output:
```
[info] Loading settings for project global-plugins from metals.sbt ...
[info] Loading global plugins from /Users/leonardo/.sbt/1.0/plugins
[info] Loading settings for project mars-exploration-build from assembly.sbt ...
[info] Loading project definition from /Users/leonardo/Projects/mars-exploration/project
[info] Loading settings for project root from build.sbt ...
[info] Set current project to MarsExploration (in build file:/Users/leonardo/Projects/mars-exploration/)
[info] Running com.felicissimo.api.MarsExploration
Server online at http://localhost:8080/
```

Isto significa que a API está pronta para receber requisições

## Requisições Http para a API

A API atende as seguintes requisições:
- Enviar uma sonda
- Obter as sondas enviadas e suas ultimas posições
- Movimentar uma sonda

### Envio de nova sonda

Para o envio de nova sonda, faça uma requisição POST para o endpoint: http://localhost:8080/probes. O ContentType deve ser _application/json_. O response será a sonda incluída.

#### Informações sobre o Payload

| Campo     | Descrição |
| --------- | --------- |
| name      | nome da sonda a ser enviada |
| x         | posição da sonda no eixo x  |
| y         | posição da sonda no eixo y  |
| direction | direção da sonda (North, South, East, West)  |

Exemplo de payload de uma sonda enviada na coordenada 1, 3 apontando para o norte
```
{
  "name": "Nome da sonda",
  "x": 1,
  "y": 3,
  "direction": "North"
}
```

### Obter as sondas enviadas e suas ultimas posições

Para obter as sondas enviadas faça uma requisição _GET_ para o endpoint: http://localhost:8080/probes. O response será uma array de sondas.

#### Informações sobre o Response

| Campo     | Descrição |
| --------- | --------- |
| name      | nome da sonda a ser enviada |
| x         | posição da sonda no eixo x  |
| y         | posição da sonda no eixo y  |
| direction | direção da sonda (North, South, East, West)  |

Exemplo de response de uma sonda enviada na coordenada 1, 3 apontando para o norte:
```
[{
  "name": "Nome da sonda",
  "x": 1,
  "y": 3,
  "direction": "North"
}]
```

### Movimentar uma sonda

Para movimentar uma sondas enviada faça uma requisição _PUT_ para o endpoint: http://localhost:8080/probes. O response será a sonda e sua nova coordenada.

#### Informações sobre o Response

| Campo     | Descrição |
| --------- | --------- |
| sequence  | sequência de comandos para envio da sonda |
| probeName | nome da sonda a ser movimentada  |

Os comandos permitidos para movimentação são:
- L: Direciona a sonda para a esquerda
- R: Direciona a sonda para a direita
- M: Move a sonda na direção determinada

Exemplo de payload para movimentação de uma sonda enviada na coordenada 3, 3 apontando para o leste:
```
[{
  "sequence": "MMRMMRMRRM",
  "name": "Nome da sonda"
}]
```

O response será a nova coordenada da sonda, apontando para 5, 1, leste.
```
[{
  "name": "Nome da sonda",
  "x": 5,
  "y": 1,
  "direction": "East"
}]
```

## Tecnologias

* [JVM](https://www.java.com/en/) - Runtime para execução de aplicações Java. 
* [Scala](http://www.dropwizard.io/1.0.2/docs/) - Linguagem utilizada para criar aplicações que executam na JVM utilizando abordagem funcional.
* [Akka Http](https://github.com/akka/akka-http) - Biblioteca criada a partir do Akka para implementação de aplicações que se comunicam via Http seguindo o modelo de Atores.
* [SBT](https://www.scala-sbt.org/download.html): Ferramenta de build para compilar e executar o código Scala.

## Authors

* **Leonardo Felicissimo** - *Criador* - [Github](https://github.com/leomfelicissimo), [LinkedIn](https://www.linkedin.com/in/leomfelicissimo/)

## Backlog (TODO)

* Separação de responsabilidade entre definição de rotas e chamadas do controller (criação de Routers)
* Serialização direta de retornos da controller (Sem precisar criar ProbeRequest e ProbeResponse)
* Validações de payloads enviados no POST e PUT
* Pesquisa de Sondas pelo nome usando QueryString em /probe
* Persistencia in-memory e não no código
* Tratamento de StatusCodes para casos de exceção
* Testes unitários
