# Sistema de Agendamento de Transferências Financeiras

Este projeto implementa um sistema completo de agendamento de transferências financeiras com backend em Spring Boot e frontend em Vue.js.

## Objetivo

O sistema permite:
- agendar uma transferência financeira
- informar conta de origem, conta de destino, valor e data de transferência
- calcular automaticamente a taxa com base no prazo entre a data de agendamento e a data de transferência
- consultar o extrato de agendamentos por conta de origem
- persistir em banco de dados em memória H2

## Tecnologias

- Java 11
- Spring Boot 2.7.x
- Spring Data JPA
- H2 Database (in-memory)
- Vue.js 3 + Vite
- Axios
- Maven Wrapper (`mvnw`)

## Regras de taxa

A taxa é calculada sobre o valor transferido com base no número de dias entre a data do agendamento (`appointmentDate`) e a data de transferência (`transferDate`):

- 0 dias: 2,5% do valor + R$ 3,00
- 1 a 10 dias: 0,0% + R$ 12,00
- 11 a 20 dias: 8,2%
- 21 a 30 dias: 6,9%
- 31 a 40 dias: 4,7%
- 41 a 50 dias: 1,7%
- fora desse intervalo: taxa não aplicável e a transferência não é permitida

## Backend

### Executar

No diretório raiz do projeto:

```bash
./mvnw spring-boot:run
```

### Build e testes

```bash
./mvnw clean test
./mvnw package
```

### API

Base URL: `http://localhost:8080`

Endpoints:

- `POST /transfers`
  - Cria uma transferência agendada
  - Corpo JSON:
    ```json
    {
      "originAccount": "1234567890",
      "destinationAccount": "0987654321",
      "amount": 100.00,
      "transferDate": "2026-06-26"
    }
    ```
- `GET /transfers/{account}`
  - Retorna todas as transferências agendadas para a conta de origem informada

### Banco de dados

O backend usa H2 em memória com as seguintes configurações:

- URL: `jdbc:h2:mem:testdb`
- usuário: `sa`
- senha: vazia
- console disponível em: `http://localhost:8080/h2-console`

## Frontend

O frontend está em `front_teste_tecnico_analista_pleno` e usa Vue.js 3 + Vite.

### Instalar dependências

```bash
cd front_teste_tecnico_analista_pleno
npm install
```

### Executar frontend

```bash
npm run dev
```

Por padrão, a aplicação Vue consome a API em `http://localhost:8080`.

## Estrutura do projeto

- `src/main/java` - backend Spring Boot
- `src/main/resources` - configurações e H2
- `src/test/java` - testes unitários do backend
- `front_teste_tecnico_analista_pleno` - frontend Vue.js

## Observações

- As contas devem ter exatamente 10 caracteres.
- Caso não exista taxa aplicável para a data informada, a transferência é rejeitada com erro.
- A data de agendamento é definida automaticamente pelo backend como a data atual.

## Como rodar o projeto

1. Execute o backend com `./mvnw spring-boot:run`.
2. Abra o frontend com `npm run dev` em `front_teste_tecnico_analista_pleno`.
3. Agende transferências e consulte o extrato por conta de origem.

---
