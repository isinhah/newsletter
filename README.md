<h1 align="center">
  Newsletter
</h1>

## 📄 Sobre
A API envia diariamente um e-mail com as 10 notícias mais importantes do momento para cada assinante, sem se limitar a uma única categoria. A aplicação consome a API da [GNews](https://docs.gnews.io/).

---

## ⚙️ Como funciona?
O usuário se cadastra informando nome e e-mail e, em seguida, recebe um e-mail de verificação contendo um token. Ao confirmar o cadastro, seu status passa a ser ACTIVE (ativo). A API de notícias então consome as notícias mais recentes e envia-as automaticamente para os assinantes ativos no horário programado.

A aplicação é composta por dois microsserviços: newsletter-api, responsável pelo gerenciamento de usuários e notícias, e notification-api, responsável pelo envio de notificações por e-mail.

---

## 🛠️ Tecnologias
- Java
- Spring Boot
- Maven
- PostgreSQL
- RabbitMQ
- Spring Cache
- Mapstruct
- Lombok
- Thymeleaf

---

## 📝 Endpoints

`POST /api/subscribers`

Cria um novo assinante no sistema.

```json
{
    "name": "exemplo",
    "email": "seuemail@exemplo.com"
}
```

`GET /api/subscribers/verify?token={verificationToken}`

Verifica o token recebido por e-mail e ativa o assinante correspondente.

```json
{
    "message": "Subscriber verified successfully"
}
```

`POST /api/news/fetch-and-send`

Endpoint para **testes manuais**:
Busca as 10 últimas notícias da API do Gnews, salva no banco de dados e envia para os assinantes ativos.

```json
{
    "message": "News fetched and sent successfully"
}
```

`GET /api/subscribers?status={status}`

Lista assinantes filtrando pelo status (pending, active, unsubscribed).

```json
[
    {
        "name": "exemplo",
        "email": "seuemail@exemplo.com",
        "status": "PENDING"
    },
    {
        "name": "exemplo 2",
        "email": "email2@exemplo.com",
        "status": "PENDING"
    }
]
```
`GET /api/subscribers/unsubscribe?token={unsubscribeToken}`

Desinscreve o assinante correspondente ao token, alterando seu status para unsubscribed.

```json
{
    "message": "Subscriber unsubscribed successfully"
}
```

---

## ⚙️ Configuração e Execução

**Pré-requisitos**:

- Java 17
- Maven
- PostgreSQL
- RabbitMQ (CloudAMQP)
- GNews (API Key)
- SMTP Gmail

**Passos para Configuração**:

1. Clone o repositório
2. Acesse o diretório do projeto
3. Configure o banco de dados no arquivo `application.yml` (URL, usuário, senha) em **newsletter-api**
3. Adicione a API Key da sua conta do GNews no `application.yml` em **newsletter-api**
4. Configure o RabbitMQ (CloudAMQP) no arquivo `application.yml` nas **duas APIs**
5. Configure o SMTP Gmail no arquivo `application.yml` em **notification-service**

```bash
# Execute a aplicação
mvn spring-boot:run

# Pressione (CTRL + C) para encerrar a aplicação
```

---

## 🙋‍♀️ Autor

👩‍💻 Projeto desenvolvido por [Isabel Henrique](https://www.linkedin.com/in/isabel-henrique/)

🤝 Fique à vontade para contribuir!

