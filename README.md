estrutura de pacotes

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

locust 
entrar na pasta locust
rodar `docker compose -f docker-compose-locust.yml up`
E para adicionar varios containeres de executor `docker compose -f docker-compose-locust.yml up --scale worker=4`