# [demo] — Solução Monorepo Fullstack

Este repositório é uma **solução fullstack monorepo** utilizando pnpm.

---

## Tecnologias Utilizadas

- **Frontend**
  - [TypeScript](https://www.typescriptlang.org/)
  - [Next.js](https://nextjs.org/)
  - [Nx](https://nx.dev/) — Ferramenta monorepo para organização e escalabilidade
  - [pnpm](https://pnpm.io/) — Gerenciador de pacotes rápido e eficiente

- **Backend**
  - [Java](https://dev.java)
  - [Spring Boot](https://spring.io/projects/spring-boot)
  - [PostgreSQL](https://www.postgresql.org/) — Banco de dados relacional

---

## Estrutura do Projeto

```sh

├── apis/ # Microservices (Java + Spring Boot)
│ └── ...
│
├── apps/ # Aplicações Frontend (Ex: Next.js)
│ ├── next-app/ # Exemplo de app
│ └── ...
│
├── packages/ # Pacotes compartilhados
│ ├── libs/ # Biblioteca de componentes da interface
│
├── pnpm-workspace.yaml
└── ...
```
