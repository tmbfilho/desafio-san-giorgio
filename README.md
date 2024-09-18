# Observações

### Base de Dados
Escolhi a base de dados MySQL para rodar a aplicação.  
Assumi que o vendedor não estava na "amarrado" ao pagamento nas tabelas e por isso criei as tabelas a seguir com o mínimo de colunas para atender ao Caso de Uso:
* TB_SELLER com dos dados do vendedor
* TB_PAYMENT_REGISTER com os dados de pagamento

Para subir a base de dados no ambiente local via docker, utilizar o comando a seguir: 
* docker run --name my_sql_desafio_san_giorgio -p3306:3306 -p33060:33060 -v my_sql_vol:/var/lib/mysql -e MYSQL_ROOT_PASSWORD=senha -e MYSQL_DATABASE=sangiorgio_db -d mysql:8.0.39

### Amazon SQS
Escolhi o utilizar o localstack para simular o ambiente AWS na minha estação de trabalho.

Para subir o localstack no ambiente local via docker utilizar os comandos a seguir:
* docker pull localstack/localstack
* docker run -d -p 4566:4566 -p 4571:4571 --name aws_local localstack/localstack

Para criar as filas no localstack utilizar os comando a seguir:
* aws sqs create-queue --queue-name payment-total-queue --endpoint-url=http://localhost:4566
* aws sqs create-queue --queue-name payment-partial-queue --endpoint-url=http://localhost:4566
* aws sqs create-queue --queue-name payment-excess-queue --endpoint-url=http://localhost:4566

### Configurações
Escolhi manter no application.properties as configurações locais(máquina do desenvolvedor) assumindo que nos demais ambientes existam as corretas versões externas ao .jar e/ou variáveis de ambiente adequadas.  
Diante disso, ele está da forma mais simples possível, inclusive gerando a base de dados via hibernate.

