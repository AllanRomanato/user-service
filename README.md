# User Service

Esse é o serviço responsável pelo controle de usuários, atualmente faz a criação e a autenticação. Não temos um cache por tras desse serviço pois as requisições 
para mesmos usuários não são muitas.

Um token JWT é criado quando um usuário é autenticado com sucesso no serviço e esse token por ter um viés matemático não necessitamos armazena-lo em banco.
Obs. Podemos armazena-lo em banco se quisermos ter um controle maior de poder bloquear usuários a qualquer momento, teriamos uma pequena perda de performance
pois os acessos ao banco ocorreriam para qualse requisição, tudo dependerá da demanda.

A secret seed to token esta armazenada no arquivo de properties para fins de apresentação deste teste, em uma situação real estaria armazenada nas variáveis 
de ambiente do ECS da AWS.

## Endpoints
```
POST /api/v1/user/login
Payload Request
{
    "email": "allan.romanato@gmail.com",
    "password": "1234"
}

Payload Response
{
    "success": true,
    "data": {
        "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaXNzIjoiR29rdVN0b3JlIiwibmFtZSI6IkFsbGFuIFJvbWFuYXRvIiwiZXhwIjoxNjMyMTU5MzA5LCJpYXQiOjE2MzIxNTg3MDksImVtYWlsIjoiYWxsYW4ucm9tYW5hdG9AZ21haWwuY29tIn0.tjrarErUswPkezfnbKPl-cDi6AtHH-26zrhO1SIbRHE"
    }
}
```
O código de retorno é ```200```

```
POST /api/v1/user/sign-up
Payload Request
{
    "userName": "ARomanato",
    "name": "Allan Romanato",
    "email": "allan.romanato@gmail.com",
    "password": "1234"
}
```
O código de retorno é ```201```
