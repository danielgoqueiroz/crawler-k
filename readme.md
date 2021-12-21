# crawler-k
 
https://g1.globo.com/sitemap/g1/

## Queue
https://www.youtube.com/watch?v=UOr9kMCCa5g

## DynamoDB

Doc
https://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/creating-clients.html

Tutorial
https://stackoverflow.com/questions/34137043/amazon-dynamodb-local-unknown-error-exception-or-failure

- Rodar docker
docker run -p 8000:8000 amazon/dynamodb-local

aws dynamodb create-table --table-name news --attribute-definitions AttributeName=id,AttributeType=S --key-schema AttributeName=id,KeyType=HASH --billing-mode PAY_PER_REQUEST --endpoint-url http://localhost:8000

Ver tabelas

Criar tabelas
aws dynamodb create-table --attribute-definitions AttributeName=id,AttributeType=S --table-name news --key-schema AttributeName=id,KeyType=HASH --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 --region ap-southeast-2 --output json --profile myaws-admin


Ver Itens
aws dynamodb scan --table-name NEWS --endpoint-url http://127.0.0.1:8000