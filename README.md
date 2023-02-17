# Votes-ms
Projeto realizado com Webflux e MongoDB.

## ☕ Pré-requisitos
 * Docker

## 💻 Executar aplicação
Observação: Entrar na pasta raíz da aplicação
 
Build docker image:
```bash
docker build -t vote-ms:vote-ms .
```
Run docker image:
```bash
docker run -p 8080:8080 vote-ms:vote-ms
```

## 🚀Acessar API

A aplicação irá iniciar na porta 8080

Para acessar o swagger:

* http://localhost:8080/webjars/swagger-ui/index.html#/

Na raíz do projeto se encontra a collection do postman, caso queira importar para testar.
