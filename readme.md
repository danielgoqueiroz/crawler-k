# crawler-k

Navega e encontra as notícias nos sites https://g1.globo.com/ e https://www.infomoney.com.br/mercados/, salvando em banco de dados DynamoDB.

Para cada notícia encontrada, extrai:
- A URL da notícia;
- O título da notícia;
- O subtítulo da notícia;
- O Autor da notícia;
- A data de publicação da notícia (escolha um formato padrão);
- O conteúdo da notícia, sem tags html e sem quebras de linha.


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