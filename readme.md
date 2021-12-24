# crawler-k

Navega e encontra as not�cias nos sites https://g1.globo.com/ e https://www.infomoney.com.br/mercados/, salvando em banco de dados DynamoDB.

Para cada not�cia encontrada, extrai:
- A URL da not�cia;
- O t�tulo da not�cia;
- O subt�tulo da not�cia;
- O Autor da not�cia;
- A data de publica��o da not�cia (escolha um formato padr�o);
- O conte�do da not�cia, sem tags html e sem quebras de linha.


## Requisitos

* Java 11 (https://www.oracle.com/br/java/technologies/javase/jdk11-archive-downloads.html)
* Docker (https://www.docker.com/)
* Maven (https://maven.apache.org/)
* Git (https://cli.github.com/)

## Como rodar

Clonar projeto
$ git clone git@github.com:danielgoqueiroz/crawler-k.git

Gerar jar 
$ mvn install -DskipTests 

Executar 
$ java -jar target/crawler-k-0.0.1-SNAPSHOT-jar-with-dependencies.jar