import os
import json
from locust import HttpUser, task, between

class CredidebUser(HttpUser):

    ###Preparando###
    conta_data = None
    credito_data = None
    debito_data = None

    def on_start(self):
        self.client.headers = {"Content-Type": "application/json"}
        self.initialize()

    def initialize(self):
        # Obter o diretório onde o locustfile.py está
        base_dir = os.path.dirname(os.path.abspath(__file__))

        # Construir o caminho completo para o arquivo JSON
        conta_file_path = os.path.join(base_dir, 'jsonRequest/criar_conta.json')
        credito_file_path = os.path.join(base_dir, 'jsonRequest/transacao_credito.json')
        debito_file_path = os.path.join(base_dir, 'jsonRequest/transacao_debito.json')

        # Carregar JSON de um arquivo
        with open(conta_file_path) as f:
            self.conta_data = json.load(f)

        with open(credito_file_path) as f:
            self.credito_data = json.load(f)

        with open(debito_file_path) as f:
            self.debito_data = json.load(f)

    wait_time = between(0.1, 0.5)  # Tempo entre as requisições (em segundos)

    ## Teste desligado, pois ocorrem varios erros de duplicate entry, devido ao uso intenso do locust.
    # @task(1)
    # def create_conta(self):
    #     response = self.client.post("/api/v1/conta", name="create_account", json=self.conta_data)
    #     assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"

    @task(1)
    def get_saldo(self):
        response = self.client.get("/api/v1/conta/1010/1010/saldo", name="get_saldo")
        assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"

    @task(2)
    def post_credito(self):
        response = self.client.post("/api/v1/transacoes", name="post_credito", json=self.credito_data)
        assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"

    @task(2)
    def post_debito(self):
        response = self.client.post("/api/v1/transacoes", name="post_debito", json=self.debito_data)
        assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"
