# Estoque Facil - Projeto de Software

## Execução Local (Passos) do Backend
### Spring Boot
```
mvn spring-boot:run
```

### Funcionalidades
Para acessar as funcionalidades do sistema acesse
```
http://localhost:8080/swagger-ui.html
```

### Features Implementadas

● Eu, como administrador, gostaria de ter o sistema armazenando todos os seus dados de forma persistente em um banco de dados.

● Eu, como cliente, gostaria de consultar a disponibilidade e o preço de cada produto do supermercado (não precisa estar logado).

● Eu, como administrador, gostaria de logar no sistema, para ter acesso às funcionalidades destinadas ao administrador.

● Eu, como administrador, gostaria que um produto fosse marcado como indisponível quando todos os lotes do produto ultrapassarem a data de validade. Dessa forma, o seu preço não pode ser mais exibido para os clientes e ele deve ser adicionado na lista de produtos vencidos.

● Eu, como administrador, gostaria de acessar informações sobre quais itens de um produto estão em baixa (abaixo de 15 unidades).

● Eu, como administrador, gostaria de acessar informações sobre quais os lotes estão próximos do vencimento (um mês de antecedência).

● Eu, como administrador, gostaria de acessar o sistema através de um link na web, preferencialmente usando o Heroku (outras opções de deploy podem ser usadas).

● Eu, como cliente, gostaria de logar no sistema, para poder ter acesso às funcionalidades destinadas ao cliente.

● Eu, como administrador, gostaria de ordenar os produtos e os registros de vendas da loja de acordo com informações importantes deles (por exemplo, ordenar os produtos por nome, preço, data, categoria).

● Eu, como administrador, gostaria de atribuir descontos para cada categoria de produto, dessa forma, o cliente recebe um abatimento no valor final da compra. Tipos de desconto: sem desconto (0%), bom desconto (10%), ótimo desconto (25%) e super desconto (50%).

● Eu, como administrador, gostaria que um produto fosse marcado como indisponível quando a quantidade de itens dele for igual a zero. Dessa forma, o seu preço não pode ser mais exibido para os clientes e ele deve ser adicionado na lista de produtos em falta.
