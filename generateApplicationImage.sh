#!/bin/bash

# Título e início do script
echo "============================"
echo "  Generate Docker Image"
echo "============================"

# Passo 1: Empacotar a aplicação
echo "Step 1: Packing application..."
echo "Running: mvn clean package -DskipTests"

if mvn clean package -DskipTests; then
  echo "Application packed successfully."
else
  echo "Error: Failed to package application." >&2
  exit 1
fi

echo "============================"

# Passo 2: Construir e gerar imagem Docker
DOCKER_IMAGE_NAME="credideb"
DOCKER_IMAGE_VERSION="0.0.1"
FULL_IMAGE_NAME="${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_VERSION}"

echo "Step 2: Build and generate Docker image..."
echo "Running: docker build -t ${FULL_IMAGE_NAME} ."

if docker build -t "${FULL_IMAGE_NAME}" .; then
  echo "Docker image '${FULL_IMAGE_NAME}' generated successfully."
else
  echo "Error: Failed to build Docker image." >&2
  exit 1
fi

echo "============================"

# Passo 3: Listar imagens relacionadas
echo "Step 3: Listing generated Docker image..."
docker images | grep "${DOCKER_IMAGE_NAME}"

# Conclusão
echo "============================"
echo "Docker image generation completed."
echo "============================"

exit 0
