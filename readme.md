# crawler-k
 
https://g1.globo.com/sitemap/g1/
https://www.infomoney.com.br/mercados/

## Requisitos
Docker
Chrome versão 96
ChromeDriver versão 96 (https://sites.google.com/chromium.org/driver/)

## DynamoDB

Doc
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/creating-clients.html

Tutorial
https://stackoverflow.com/questions/34137043/amazon-dynamodb-local-unknown-error-exception-or-failure

- Rodar docker
docker run -p 8000:8000 amazon/dynamodb-local

aws dynamodb create-table --table-name news --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000

Ver tabelas
aws dynamodb list-tables --endpoint-url http://localhost:8000

Ver Itens
aws dynamodb scan --table-name news --endpoint-url http://localhost:8000

Criar tabelas
aws dynamodb create-table --attribute-definitions AttributeName=id,AttributeType=S --table-name news --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --region ap-southeast-2 --output json --profile myaws-admin


# Pendências
Adicionar identificadores de formatos de páginas para parser