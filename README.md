# personalFinancesBackEnd

API REST de controle de finanças pessoais: cadastro de transações, listagem, exclusão e exportação do extrato em Excel.

> Projeto de estudo (julho/2024). O front-end que consome esta API está em [personalFinancesFrontEnd](https://github.com/lucasmaraia/personalFinancesFrontEnd).

## Stack

- Java 17
- Spring Boot 3.3.1 (Web, Data JPA)
- H2 em arquivo (persistência local, sem precisar de servidor de banco)
- Apache POI 5.3.0 (geração do `.xlsx`)
- Lombok

## Modelo

`Transaction`

| Campo         | Tipo         |
|---------------|--------------|
| `id`          | `Long`       |
| `description` | `String`     |
| `amount`      | `BigDecimal` |
| `date`        | `Date`       |

## Endpoints

| Método   | Rota                 | Descrição                           |
|----------|----------------------|-------------------------------------|
| `POST`   | `/transaction`       | Cria uma transação                  |
| `GET`    | `/transaction`       | Lista todas as transações           |
| `DELETE` | `/transaction/{id}`  | Remove uma transação                |
| `GET`    | `/transaction/excel` | Baixa o extrato completo em `.xlsx` |

```bash
curl -X POST http://localhost:8080/transaction \
  -H "Content-Type: application/json" \
  -d '{"description":"Mercado","amount":250.90,"date":"2024-07-28"}'

curl http://localhost:8080/transaction

curl -OJ http://localhost:8080/transaction/excel
```

## Como rodar

```bash
./mvnw spring-boot:run
```

Não há banco para instalar: o H2 grava em `./personalFinances.mv.db` e o schema é criado pelo Hibernate (`ddl-auto=update`). O console web do H2 fica em <http://localhost:8080/h2-console> (JDBC URL `jdbc:h2:file:./personalFinances`, usuário `sa`).

O CORS está liberado em `config/CorsConfig.java` para que o front-end rode de outra origem.

## Limitações

Sem autenticação, sem multiusuário e sem categorias de transação: é um extrato único e aberto. O arquivo `.mv.db` do H2 está versionado no repositório (junto com `target/` e `out/`), o que não é uma boa prática; um `.gitignore` resolveria.
