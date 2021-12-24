# crawler-k

// Encontre todas as not�cias nos sites "https://g1.globo.com/" e
	// "https://www.infomoney.com.br/mercados/"

	// O programa precisa navegar em pelo menos 2 se��es do site, al�m do link
	// passado (ex.: https://g1.globo.com/economia/);

	// O site infomoney tem um bot�o "Carregar mais", onde � poss�vel encontrar mais
	// mat�rias. O programa precisa acessar mat�rias de pelo menos 2 p�ginas
	// adicionais a partir desse bot�o (Essas p�ginas n�o s�o consideradas como
	// sess�es extras para o item anterior);

	// Para cada not�cia encontrada, extrair:
		// A URL da not�cia;
		// O t�tulo da not�cia;
		// O subt�tulo da not�cia;
		// O Autor da not�cia;
		// A data de publica��o da not�cia (escolha um formato padr�o);
		// O conte�do da not�cia, sem tags html e sem quebras de linha.
	// As not�cias devem ser armazenadas no AWS DynamoDB. Utilize a vers�o local de
	// desenvolvimento;

	// O programa deve ser empacotado em um Docker. Deixe a Dockerfile na raiz do
	// projeto com instru��es para o build;

	// Os sites devem ser processados em paralelo;
	
 
https://g1.globo.com/sitemap/g1/
https://www.infomoney.com.br/mercados/

## Requisitos
Docker
Chrome vers�o 96
ChromeDriver vers�o 96 (https://sites.google.com/chromium.org/driver/)

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


# Pend�ncias
Adicionar identificadores de formatos de p�ginas para parser