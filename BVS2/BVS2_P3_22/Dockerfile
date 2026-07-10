# Stage 1: Pull ollama model during build
FROM ollama/ollama AS ollama-pull   #we built the Homework with docker-compose we tried to make a single dockerfile for it while at the ULP but didnt quite manage in the given time

RUN ollama serve & \
    sleep 5 && \
    ollama pull phi && \
    pkill ollama

# Stage 2: Build client
FROM python:3.10-slim AS client-build

WORKDIR /app

COPY requirements .

RUN apt-get update && apt-get install -y python3-pip
RUN pip3 install --no-cache-dir -r requirements

COPY ex_08_client.py .

# Stage 3: Build server
FROM python:3.10-slim AS server-build

WORKDIR /app

COPY requirements .

RUN apt-get update && apt-get install -y python3-pip
RUN pip3 install --no-cache-dir -r requirements

COPY ex_08_api_server.py .

# Final stage: run everything with ollama base
FROM ollama/ollama

WORKDIR /app

RUN apt-get update && apt-get install -y bash

# Install python and pip for running flask apps
RUN apt-get update && apt-get install -y python3 python3-pip

# Copy Python dependencies and install
COPY requirements .
RUN pip3 install --no-cache-dir -r requirements

# Copy client and server scripts
COPY --from=client-build /app/ex_08_client.py .
COPY --from=server-build /app/ex_08_api_server.py .

# Copy templates folder if needed
COPY templates ./templates

EXPOSE 5001 5002 11434

ENTRYPOINT []

CMD ["bash", "-c", "ollama serve & sleep 10 && python3 ex_08_api_server.py & python3 ex_08_client.py"]
