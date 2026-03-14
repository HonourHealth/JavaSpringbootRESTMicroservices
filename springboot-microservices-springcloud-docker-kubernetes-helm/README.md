# SpringBoot Microservices with Spring Cloud, Docker, Kubernetes, and Helm

This project demonstrates a comprehensive microservices architecture using Spring Boot, Spring Cloud, Docker, Kubernetes, and Helm. It includes services for banking operations such as Accounts, Cards, and Loans, along with infrastructure services like Config Server, Eureka Server, and API Gateway.

## Table of Contents
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Microservices](#microservices)
- [Messaging (Kafka + RabbitMQ Together)](#messaging-kafka--rabbitmq-together)
- [Resilience and Design Patterns](#resilience-and-design-patterns)
- [Failure Simulation Checklist](#failure-simulation-checklist)
- [Best Practices and Suggested Approaches](#best-practices-and-suggested-approaches)
- [Getting Started](#getting-started)
  - [Build Instructions](#build-instructions)
  - [Running with Docker Compose](#running-with-docker-compose)
  - [Running with Kubernetes](#running-with-kubernetes)
  - [Running with Helm](#running-with-helm)
- [Observability](#observability)
- [API Documentation](#api-documentation)
- [Keycloak Configuration](#keycloak-configuration)
- [Useful Commands](#useful-commands)

## Architecture

The project consists of the following microservices:
- **Config Server**: Centralized configuration for all services.
- **Eureka Server**: Service discovery and registration.
- **Accounts Service**: Manages customer bank accounts.
- **Cards Service**: Manages customer credit/debit cards.
- **Loans Service**: Manages customer loans.
- **Message Service**: Handles messaging and notifications.
- **Gateway Server**: API Gateway for routing and filtering.

Infrastructure components:
- **Kafka**: Event streaming broker.
- **RabbitMQ**: Message broker.
- **Keycloak**: Identity and Access Management.
- **Redis**: Caching (implied by docker-compose).
- **Observability Stack**: Grafana, Prometheus, Loki, Tempo, Alloy.

## Prerequisites

Ensure you have the following installed and running:
- **Java 21**
- **Maven**
- **Docker Desktop** (with Kubernetes enabled)
- **kubectl** (Kubernetes CLI)
- **Helm** (Package manager for Kubernetes)
- **Hookdeck CLI** (Optional, for webhook monitoring)

## Microservices

The services should be started in the following order to ensure dependencies are met:

1.  **ConfigserverApplication**
2.  **EurekaserverApplication**
3.  **AccountsApplication**
4.  **CardsApplication**
5.  **LoansApplication**
6.  **MessageApplication**
7.  **GatewayserverApplication**

If you run services from IDE (not via Docker Compose), start messaging infrastructure first:
- **Kafka** (`localhost:9092`)
- **RabbitMQ** (`localhost:5672`, management UI `localhost:15672`)

## Messaging (Kafka + RabbitMQ Together)

This project is configured to use **Kafka and RabbitMQ at the same time** (not a toggle).

Code and configuration references:
- `accounts/pom.xml`: includes `spring-cloud-stream`, `spring-cloud-stream-binder-kafka`, `spring-cloud-stream-binder-rabbit`
- `message/pom.xml`: includes `spring-cloud-stream`, `spring-cloud-stream-binder-kafka`, `spring-cloud-stream-binder-rabbit`
- `accounts/src/main/resources/application.yml`: function definitions `updateCommunication;updateCommunicationRabbit`
- `message/src/main/resources/application.yml`: function definitions `email|sms;emailRabbit|smsRabbit`
- `accounts/src/main/java/com/example/accounts/service/impl/AccountsServiceImpl.java`: sends messages via `StreamBridge`
- `accounts/src/main/java/com/example/accounts/functions/AccountsFunctions.java`: consumers for Kafka and RabbitMQ flows
- `message/src/main/java/com/example/message/functions/MessageFunctions.java`: processor functions for Kafka and RabbitMQ flows

### Channel mapping used in current code

| Flow | Accounts binding | Message binding | Destination |
|------|------------------|-----------------|-------------|
| Request via Kafka | `sendCommunication-out-0` | `emailsms-in-0` | `send-communication` |
| Response via Kafka | `updateCommunication-in-0` | `emailsms-out-0` | `communication-sent` |
| Request via RabbitMQ | `sendCommunicationRabbit-out-0` | `emailRabbitsmsRabbit-in-0` | `send-communication-rabbit` |
| Response via RabbitMQ | `updateCommunicationRabbit-in-0` | `emailRabbitsmsRabbit-out-0` | `communication-sent-rabbit` |

Notes:
- Both services set stream binders for `kafka` and `rabbit` in `application.yml`.
- `default-binder` is `kafka`, and RabbitMQ flows are still active because Rabbit bindings are explicitly set with `binder: rabbit`.

## Resilience and Design Patterns

The codebase uses several resilience and distributed-system patterns in combination:

| Pattern | How it is used in this project | Main references |
|---|---|---|
| Circuit Breaker (Gateway) | Gateway route for Accounts uses circuit breaker with fallback URI. | `gatewayserver/src/main/java/com/example/gatewayserver/GatewayserverApplication.java`, `gatewayserver/src/main/java/com/example/gatewayserver/controller/FallbackController.java` |
| Time Limiter | Resilience4j time limiter is configured in gateway circuit breaker customization. | `gatewayserver/src/main/java/com/example/gatewayserver/GatewayserverApplication.java` |
| Retry | Retry is applied at controller level (`getBuildInfo`) and on Loans gateway route. | `accounts/src/main/java/com/example/accounts/controller/AccountsController.java`, `gatewayserver/src/main/java/com/example/gatewayserver/GatewayserverApplication.java` |
| Rate Limiter | Rate limiter is applied at controller level (`getJavaVersion`) and gateway route level for Cards using Redis. | `accounts/src/main/java/com/example/accounts/controller/AccountsController.java`, `gatewayserver/src/main/java/com/example/gatewayserver/GatewayserverApplication.java`, `gatewayserver/src/main/resources/application.yml` |
| Fallback | Gateway fallback endpoint (`/contactSupport`) and OpenFeign fallback classes for Loans/Cards are present. | `gatewayserver/src/main/java/com/example/gatewayserver/controller/FallbackController.java`, `accounts/src/main/java/com/example/accounts/service/client/LoansFallback.java`, `accounts/src/main/java/com/example/accounts/service/client/CardsFallback.java` |
| OpenFeign + Client-side resilience | Accounts calls Loans/Cards through Feign clients with fallback classes, with Feign circuit breaker enabled via config. | `accounts/src/main/java/com/example/accounts/service/client/LoansFeignClient.java`, `accounts/src/main/java/com/example/accounts/service/client/CardsFeignClient.java`, `accounts/src/main/resources/application.yml` |
| Service Discovery | Services register to Eureka and Gateway routes with `lb://` logical service names. | `eurekaserver/src/main/java/com/example/eurekaserver/EurekaserverApplication.java`, `gatewayserver/src/main/java/com/example/gatewayserver/GatewayserverApplication.java` |
| Externalized Configuration | Config Server centralizes configuration and services import config via `spring.config.import`. | `configserver/src/main/java/com/example/configserver/ConfigserverApplication.java`, `accounts/src/main/resources/application.yml`, `gatewayserver/src/main/resources/application.yml` |
| Event-driven messaging | Accounts and Message services communicate asynchronously over Kafka and RabbitMQ with function bindings and `StreamBridge`. | `accounts/src/main/resources/application.yml`, `message/src/main/resources/application.yml`, `accounts/src/main/java/com/example/accounts/service/impl/AccountsServiceImpl.java` |

### Important implementation notes

- Feign fallbacks currently return `null` responses; these classes exist but can be improved with meaningful fallback payloads.
- `resilience4j` base configs are declared in `accounts/src/main/resources/application.yml` and `gatewayserver/src/main/resources/application.yml`.
- No explicit Bulkhead annotation/configuration was found in service code at the moment.

### Pattern to endpoint examples

Use these examples to validate runtime behavior through Postman or browser.

| Pattern | Example endpoint | What to observe |
|---|---|---|
| Gateway Circuit Breaker + Fallback | `GET http://localhost:8072/bank/accounts/api/fetchCustomerDetails?mobileNumber=4354437687` | If Accounts path fails repeatedly, gateway routes to fallback endpoint and returns support message from `/contactSupport`. |
| Gateway Retry (Loans route) | `GET http://localhost:8072/bank/loans/api/fetch?mobileNumber=4354437687` | For transient failures, gateway retries GET requests before returning an error. |
| Gateway Rate Limiter (Cards route) | `GET http://localhost:8072/bank/cards/api/contact-info` | Calls are throttled per `user` header and Redis rate limit config. |
| Service-level Retry | `GET http://localhost:8080/api/build-info` | Endpoint uses `@Retry` and fallback method (`getBuildInfoFallback`) if retries are exhausted. |
| Service-level Rate Limiter | `GET http://localhost:8080/api/java-version` | Endpoint uses `@RateLimiter` and returns fallback value when limit is exceeded. |
| Feign + Discovery | `GET http://localhost:8080/api/fetchCustomerDetails?mobileNumber=4354437687` | Accounts resolves Loans/Cards via Eureka service IDs (`loans`, `cards`) through Feign clients. |
| Kafka request/response messaging | `POST http://localhost:8080/api/create` | Accounts publishes to `send-communication`; Message consumes and publishes status to `communication-sent`; Accounts consumes update. |
| RabbitMQ request/response messaging | `POST http://localhost:8080/api/create` | Same business flow also runs over RabbitMQ topics `send-communication-rabbit` and `communication-sent-rabbit`. |

Quick checks before testing:
- Ensure `ConfigserverApplication`, `EurekaserverApplication`, all business services, and `GatewayserverApplication` are running in the documented order.
- Ensure Kafka (`localhost:9092`) and RabbitMQ (`localhost:5672`) are available when validating messaging flows.
- Ensure Redis (`localhost:6379`) is available when validating gateway rate limiting.

## Failure Simulation Checklist

Use this checklist to prove resilience behavior end-to-end.

| Scenario | What to stop/break | Test call | Expected behavior |
|---|---|---|---|
| Accounts route circuit breaker | Stop `AccountsApplication` (or block it via gateway route) | `GET http://localhost:8072/bank/accounts/api/fetchCustomerDetails?mobileNumber=4354437687` | Gateway fallback is returned from `/contactSupport`. |
| Loans route retry | Stop `LoansApplication` | `GET http://localhost:8072/bank/loans/api/fetch?mobileNumber=4354437687` | Gateway retries request before returning final failure. |
| Cards route rate limit | Keep Cards up, send repeated calls with same `user` header | `GET http://localhost:8072/bank/cards/api/contact-info` | Requests are throttled according to Redis limiter config. |
| Service retry fallback | Trigger repeated failures in build-info path | `GET http://localhost:8080/api/build-info` | `getBuildInfoFallback` value is returned after retries are exhausted. |
| Service rate-limit fallback | Burst calls to Java version endpoint | `GET http://localhost:8080/api/java-version` | Rate limiter fallback value is returned when limit is hit. |
| Kafka binder unavailable | Stop Kafka (`localhost:9092`) | `POST http://localhost:8080/api/create` | Kafka binding/provision logs fail; Rabbit flow can still work if Rabbit is up. |
| Rabbit binder unavailable | Stop RabbitMQ (`localhost:5672`) | `POST http://localhost:8080/api/create` | Rabbit listener reconnect/retry logs appear; Kafka flow can still work if Kafka is up. |
| Eureka unavailable | Stop `EurekaserverApplication` after services start | Gateway routed calls | Existing instances may serve briefly; new discovery/registration and load-balanced lookups degrade. |

Recommended verification endpoints:
- `http://localhost:8070` (Eureka dashboard)
- `http://localhost:8072/actuator/health`
- `http://localhost:8080/actuator/health`
- `http://localhost:9010/actuator/health` (if actuator enabled for Message service)

## Best Practices and Suggested Approaches

### 1) Configuration and environments
- Keep sensitive values out of source control; externalize secrets for DB, broker, and Keycloak credentials.
- Keep `default` profile developer-friendly, and use `prod` for stricter behavior and external dependencies.
- Continue using Config Server as the single source for shared runtime settings.

### 2) Messaging (Kafka + RabbitMQ together)
- Keep binder names explicit in each binding (`binder: kafka` / `binder: rabbit`) to avoid ambiguous defaults.
- Use consistent destination naming conventions across services (`send-*` for outbound requests, `*-sent` for status/ack).
- Add idempotency checks for consumer-side updates (for example, communication status updates) to tolerate re-delivery.

### 3) Resilience and fallback quality
- Replace `null` returns in Feign fallback implementations with meaningful safe responses and clear error metadata.
- Keep gateway-level resilience (circuit breaker/retry/rate-limiter) and service-level resilience (Retry/RateLimiter) complementary.
- Define explicit timeout budgets per hop so gateway, Feign, and downstream service timeouts are aligned.

### 4) Security and IAM
- For non-local environments, avoid default Keycloak admin credentials and rotate client secrets.
- Keep gateway as the primary OAuth2 resource-server enforcement point and validate role-based routes regularly.
- Maintain realm/client export backups for reproducible environment setup.

### 5) Observability and operations
- Keep a minimal runbook: expected ports, health endpoints, and common failure signatures (Kafka/Rabbit/Config/Eureka).
- Use Prometheus targets and Grafana dashboards together: targets up does not always mean application-level health is good.
- Standardize correlation IDs across gateway and downstream services for faster distributed troubleshooting.

### 6) Delivery workflow
- For Java code changes: `mvn clean install` first, then rebuild affected images (`jib:dockerBuild`), then redeploy.
- For Compose-only changes: `docker compose up -d` with recreate as needed is usually enough.
- For Helm changes: prefer `helm upgrade` and validate with `helm list`, `kubectl get pods`, and `kubectl get events`.

## Getting Started

### Build Instructions

To build the project and generate Docker images, use the following Maven commands:

```bash
# Generate a JAR file inside the target folder
mvn clean install -Dmaven.test.skip=true

# Start a Spring Boot project locally
mvn spring-boot:run

# Generate a Docker image using Buildpacks (No Dockerfile needed)
mvn spring-boot:build-image

# Generate a Docker image using Google Jib (No Dockerfile needed)
mvn compile jib:dockerBuild
```

### Running with Docker Compose

You can run the entire system using the provided Docker Compose files.

1.  Navigate to the desired environment folder (e.g., `default`, `prod`, `qa`):
    ```bash
    cd docker-compose/default
    ```
2.  Start the services:
    ```bash
    docker compose up
    ```
    This starts both brokers (`kafka` and `rabbit`) and the microservices wired to both binders.
3.  Stop the services:
    ```bash
    docker compose down
    ```

### Running with Kubernetes

Ensure your Kubernetes cluster is running (e.g., via Docker Desktop).

0.  **Messaging Prerequisite**:
    `kubernetes/*.yml` currently does not define Kafka or RabbitMQ resources.
    Provision both brokers separately (for example with charts under `helm/kafka` and a RabbitMQ chart) before deploying services that use messaging.

1.  **Keycloak Setup**:
    ```bash
    kubectl apply -f kubernetes/1_keycloak.yml
    ```
    *Note: You must create the necessary realms and profiles in Keycloak before proceeding.*

2.  **Apply Configurations and Services**:
    Run the following commands in order:
    ```bash
    kubectl apply -f kubernetes/2_configmaps.yaml
    kubectl apply -f kubernetes/3_configserver.yml
    kubectl apply -f kubernetes/4_eurekaserver.yml
    kubectl apply -f kubernetes/5_accounts.yml
    kubectl apply -f kubernetes/6_loans.yml
    kubectl apply -f kubernetes/7_cards.yml
    # Apply Message service if available
    kubectl apply -f kubernetes/8_gateway.yml
    ```

3.  **Port Forwarding (Dashboard)**:
    ```bash
    kubectl -n kubernetes-dashboard port-forward svc/kubernetes-dashboard-kong-proxy 8443:443
    ```

### Running with Helm

Navigate to the `helm` directory and use the following commands to manage releases.

```bash
# Check dependencies
helm dependencies build

# Install a chart
helm install [NAME] [CHART]

# Upgrade a release
helm upgrade [NAME] [CHART]

# Uninstall a release
helm uninstall [NAME]
```

## Observability

The project includes an observability stack in `docker-compose/observability`. This includes:
- **Grafana**: Dashboarding
- **Prometheus**: Metrics
- **Loki**: Logs
- **Tempo**: Tracing
- **Alloy**: OpenTelemetry Collector

## API Documentation

A **Postman Collection** is included in the root directory: `Microservices.postman_collection.json`. Import this into Postman to test the API endpoints.

## Keycloak Configuration

Keycloak is used for authentication and authorization.
- Ensure the Keycloak container is running.
- Access the Keycloak Admin Console (typically `http://localhost:8080` or similar).
- Create the required Realm, Client, and Users/Roles as expected by the microservices security configuration.

## Useful Commands

### Docker Commands
| Command | Description |
|---------|-------------|
| `docker build . -t yourdockername/accounts:s1` | Generate a docker image based on a Dockerfile |
| `docker run -p 8080:8080 yourdockername/accounts:s1` | Start a docker container based on a given image |
| `docker images` | List all docker images in the server |
| `docker image inspect image-id` | Display detailed image information |
| `docker image rm image-id` | Remove one or more images |
| `docker image push docker.io/yourdockername/accounts:s1` | Push an image to a registry |
| `docker image pull docker.io/yourdockername/accounts:s1` | Pull an image from a registry |
| `docker ps` | Show all running containers |
| `docker ps -a` | Show all containers (running and stopped) |
| `docker container start container-id` | Start stopped containers |
| `docker container pause container-id` | Pause processes within containers |
| `docker container unpause container-id` | Unpause processes within containers |
| `docker container stop container-id` | Stop running containers |
| `docker container kill container-id` | Kill running containers instantly |
| `docker container restart container-id` | Restart containers |
| `docker container inspect container-id` | Inspect details for a container |
| `docker container logs container-id` | Fetch logs of a container |
| `docker container logs -f container-id` | Follow log output of a container |
| `docker container rm container-id` | Remove containers |
| `docker container prune` | Remove all stopped containers |
| `docker compose up` | Create and start containers from Compose file |
| `docker compose down` | Stop and remove resources created by up |
| `docker compose start` | Start existing containers |
| `docker compose stop` | Stop running containers |

### Service Containers
```bash
# MySQL
docker run -p 3306:3306 --name accountsdb -e MYSQL_ROOT_PASSWORD=root -e MYSQL_DATABASE=accountsdb -d mysql

# Redis
docker run -p 6379:6379 --name redis -d redis

# Keycloak
docker run -p 8080:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin quay.io/keycloak/keycloak:22.0.3 start-dev

# RabbitMQ
docker run -it --rm --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:4-management

# Kafka
docker run --name kafka -p 9092:9092 apache/kafka:4.1.0
```

### Kubernetes Commands
| Command | Description |
|---------|-------------|
| `kubectl apply -f filename` | Create deployment/service/configmap from YAML |
| `kubectl get all` | Get all components inside the cluster |
| `kubectl get pods` | Get all pod details |
| `kubectl get pod pod-id` | Get details of a specific pod |
| `kubectl describe pod pod-id` | Get more details of a specific pod |
| `kubectl delete pod pod-id` | Delete a pod from grid |
| `kubectl get services` | Get all services details |
| `kubectl get service service-id` | Get details of a specific service |
| `kubectl describe service service-id` | Get more details of a specific service |
| `kubectl get nodes` | Get all node details |
| `kubectl get node node-id` | Get details of a specific node |
| `kubectl get replicasets` | Get all replicaset details |
| `kubectl get deployments` | Get all deployment details |
| `kubectl get configmaps` | Get all configmap details |
| `kubectl get events --sort-by=.metadata.creationTimestamp` | Get all events sorted by time |
| `kubectl scale deployment accounts-deployment --replicas=1` | Scale replicas for a deployment |
| `kubectl set image deployment gatewayserver-deployment gatewayserver=example/gatewayserver:s1 --record` | Update image for a deployment |
| `kubectl rollout history deployment gatewayserver-deployment` | View rollout history |
| `kubectl rollout undo deployment gatewayserver-deployment --to-revision=1` | Rollback to a specific revision |
| `kubectl get pvc` | List PVCs in the cluster |
| `kubectl delete pvc data-happy-panda-mariadb-0` | Delete a PVC |

### Helm Commands
| Command | Description |
|---------|-------------|
| `helm create [NAME]` | Create a default chart |
| `helm dependencies build` | Recompile the chart dependencies |
| `helm install [NAME] [CHART]` | Install a chart into K8s |
| `helm upgrade [NAME] [CHART]` | Upgrade a release |
| `helm history [NAME]` | Display release history |
| `helm rollback [NAME] [REVISION]` | Rollback a release |
| `helm uninstall [NAME]` | Uninstall a release |
| `helm template [NAME] [CHART]` | Render templates locally |
| `helm list` | List helm releases |

### Other Tools
**Hookdeck**:
```bash
hookdeck listen 8071 Source --cli-path /monitor
```

Monitor webhooks at:
- `https://github.com/HonourHealth/config-server-repo/settings/hooks/579867926?tab=deliveries`
- `https://console.hookdeck.com/`

**Apache Benchmark**:
```bash
# Perform load testing (10 requests, concurrency 2)
ab -n 10 -c 2 -v 3 http://localhost:8072/example/cards/api/contact-info
```



