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
Ter instalado o Docker

## DynamoDB

- Rodar docker
docker run -p 8000:8000 amazon/dynamodb-local

aws dynamodb create-table --table-name news --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000

Ver tabelas
aws dynamodb list-tables --endpoint-url http://localhost:8000

Ver Itens
aws dynamodb scan --table-name news --endpoint-url http://localhost:8000

Criar tabelas
aws dynamodb create-table --attribute-definitions AttributeName=id,AttributeType=S --table-name news --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --region ap-southeast-2 --output json --profile myaws-admin