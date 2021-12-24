# crawler-k

// Encontre todas as notícias nos sites "https://g1.globo.com/" e
	// "https://www.infomoney.com.br/mercados/"

	// O programa precisa navegar em pelo menos 2 seções do site, além do link
	// passado (ex.: https://g1.globo.com/economia/);

	// O site infomoney tem um botão "Carregar mais", onde é possível encontrar mais
	// matérias. O programa precisa acessar matérias de pelo menos 2 páginas
	// adicionais a partir desse botão (Essas páginas não são consideradas como
	// sessões extras para o item anterior);

	// Para cada notícia encontrada, extrair:
		// A URL da notícia;
		// O título da notícia;
		// O subtítulo da notícia;
		// O Autor da notícia;
		// A data de publicação da notícia (escolha um formato padrão);
		// O conteúdo da notícia, sem tags html e sem quebras de linha.
	// As notícias devem ser armazenadas no AWS DynamoDB. Utilize a versão local de
	// desenvolvimento;

	// O programa deve ser empacotado em um Docker. Deixe a Dockerfile na raiz do
	// projeto com instruções para o build;

	// Os sites devem ser processados em paralelo;
	
 
https://g1.globo.com/sitemap/g1/
https://www.infomoney.com.br/mercados/

## Requisitos
Docker
Chrome versão 96
ChromeDriver versão 96 (https://sites.google.com/chromium.org/driver/)

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


# Pendências
Adicionar identificadores de formatos de páginas para parser