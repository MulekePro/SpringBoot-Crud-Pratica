
Não é necessário instalar banco de dados: os dados ficam em memória e são perdidos quando a aplicação é reiniciada.

## Como executar

1. Clone o repositório e entre na pasta do projeto:

   ```bash
   git clone <URL-DO-REPOSITORIO>
   cd <NOME-DA-PASTA>
   ```

2. Execute a aplicação:

   ```bash
   mvn spring-boot:run
   ```

   Se o projeto tiver o wrapper do Maven, use `./mvnw spring-boot:run` (Linux/macOS) ou `mvnw.cmd spring-boot:run` (Windows).

   Também é possível abrir o projeto no IntelliJ IDEA e executar a classe `Demo1Application`.

3. A API ficará disponível em `http://localhost:8080`.

Para gerar o `.jar` e executá-lo:

```bash
mvn clean package
java -jar target/*.jar
```

## Endpoints

| Método | URI | Descrição | Sucesso |
|---|---|---|---|
| `POST` | `/notificacao` | Cadastra uma notificação | `201 Created` |
| `GET` | `/notificacao` | Lista as notificações, com filtros opcionais | `200 OK` |
| `GET` | `/notificacao/{id}` | Busca uma notificação pelo ID | `200 OK` |
| `PUT` | `/notificacao/{id}` | Atualiza uma notificação | `200 OK` |
| `DELETE` | `/notificacao/{id}` | Remove uma notificação | `204 No Content` |

### Filtros do `GET /notificacao`

Todos são opcionais e podem ser combinados.

| Parâmetro | Descrição |
|---|---|
| `agravo` | Texto contido no nome do agravo/doença |
| `nomePaciente` | Texto contido no nome do paciente |
| `dataNotificacaoDe` | Data da notificação a partir de (formato `yyyy-MM-dd`) |
| `dataNotificacaoAte` | Data da notificação até (formato `yyyy-MM-dd`) |
| `duplicadas` | Com `true`, lista somente as notificações possivelmente duplicadas (RN01) |

Exemplo:

```http
GET /notificacao?agravo=dengue&duplicadas=true
```

## Exemplo de uso

Cadastro de uma notificação com os campos obrigatórios:

```bash
curl -X POST http://localhost:8080/notificacao \
  -H "Content-Type: application/json" \
  -d '{
    "dadosGerais": {
      "numeroNotificacao": "1001",
      "tipoNotificacao": 2,
      "agravo": "Dengue",
      "dataNotificacao": "2026-03-12",
      "ufNotificacao": "PB",
      "municipioNotificacao": "Cajazeiras",
      "unidadeSaude": "UBS Centro",
      "dataPrimeirosSintomas": "2026-03-10"
    },
    "notificacaoIndividual": {
      "nomePaciente": "Maria da Silva",
      "dataNascimento": "1990-05-10",
      "sexo": "F",
      "gestante": "5",
      "nomeMae": "Ana da Silva"
    },
    "dadosDeResidencia": {
      "uf": "PB",
      "municipio": "Cajazeiras"
    },
    "conclusao": {
      "dataInvestigacao": "2026-03-13"
    }
  }'
```



| Situação | Status |
|---|---|
| Campos inválidos ou regra de negócio violada (RN02, RN03) | `400 Bad Request` |
| Notificação não encontrada | `404 Not Found` |

Exemplo de erro de validação:

```json
{
  "title": "Dados inválidos",
  "status": 400,
  "detail": "Um ou mais campos são inválidos.",
  "erros": {
    "dadosGerais.agravo": "O agravo é obrigatório"
  }
}
```
