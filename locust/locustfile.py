import os
import json
from locust import HttpUser, task, between

class CredidebUser(HttpUser):

    #Preparando

    # Obter o diretório onde o locustfile.py está
    base_dir = os.path.dirname(os.path.abspath(__file__))

    # Construir o caminho completo para o arquivo JSON
    credito_file_path = os.path.join(base_dir, 'transacao_credito.json')
    debito_file_path = os.path.join(base_dir, 'transacao_debito.json')

    # Carregar JSON de um arquivo
    with open(credito_file_path) as f:
        credito_data = json.load(f)

    # Carregar JSON de um arquivo
    with open(debito_file_path) as f:
        debito_data = json.load(f)

    wait_time = between(0.1, 0.5)  # Tempo entre as requisições (em segundos)

    @task(1)
    def get_saldo(self):
        self.client.get("/api/v1/conta/1010/1010/saldo", name="get_saldo")

    @task(2)
    def post_credito(self):
        response = self.client.post("/api/v1/transacoes", name="post_credito", json=self.credito_data)
        
        # Valida o status code da resposta
        assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"

    @task(2)
    def post_debito(self):
        response = self.client.post("/api/v1/transacoes", name="post_debito", json=self.debito_data)

        # Valida o status code da resposta
        assert response.status_code == 200, f"Expected status code 200, but got {response.status_code}"
