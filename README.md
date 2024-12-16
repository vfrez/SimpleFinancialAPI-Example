Aplicação simples para gerenciamento de transações financeiras, incluindo crédito, débito e consulta de saldo.

## Executar a aplicação

### Pode-se iniciar toda a aplicação pelo docker compose

Gerar pacotes <br>
`mvn clean package -DskipTests`

Iniciar o docker <br>
`docker compose up`

### Também pode-se iniciar a aplicação pelo maven local

Iniciar banco de dados <br> 
`docker compose up db -d`

Iniciar aplicação <br>
`mvn spring-boot:run`

## Executar teste de stress
entrar na pasta `./locust`

Iniciar o container docker que realizará os testes <br>
`docker compose up`

E para adicionar mais workers <br>
`docker compose up --scale worker=4`

> [!NOTE]  
> O teste de carga vai usar uma conta previamente criada para fazer as transacoes <br>
> A conta e agencia sao previamente criadas no start da aplicação <br>
> agencia 1010 e conta 1010 <br>

> [!NOTE]  
> O teste para a API de criaçao de contas foi desabilitado, pois a intensidade do teste gera duplicate key na agencia e conta gerados. <br>
> Quando o teste de stress para criaçao de API estiver ativo, as contas serao criadas para a agencia 2020



## Definiçoes gerais
- A porta padrão é `8080`
- Ao executar pelo docker, seá disponibilizada a porta `9010` para estatiscas da jvm(Usando VisualVm ou JMC)
- Locust tem um frontend que pode ser acessado em http://localhost:8089

## estrutura de pacotes
```
com.example.credideb
├── api             # Código gerado automaticamente pelo OpenAPI Generator
│   ├── delegate    # Implementação das interfaces geradas (ApiDelegate)
│   ├── exception   # Classes personalizadas para tratamento de erros (opcional)
│   ├── request     # DTOs de entrada gerados (opcional, se separados pelo gerador)
│   ├── response    # DTOs de saída gerados (opcional, se separados pelo gerador)
├── config          # Configurações gerais da aplicação (CORS, Cache, etc.)
├── model           # Modelos de domínio (entidades do banco de dados)
├── repository      # Repositórios para interação com o banco de dados
├── service         # Implementações de regras de negócio
├── util            # Classes utilitárias para conversões e validações
└── CredidebApplication.java # Classe principal da aplicação
```




