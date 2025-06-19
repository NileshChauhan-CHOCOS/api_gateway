# 🚀 API Gateway Service
## Introduction
A reactive, non-blocking, database supported dynamic routing API Gateway built using **Spring Cloud Gateway**.  
This service acts as a single entry point for routing requests to downstream microservices with support for dynamic routing, request filtering, response rewriting, authentication, and circuit-breaking.
---

## 📖 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Available Routes](#available-routes)
- [Custom Predicates & Filters](#custom-predicates--filters)
- [Running Locally](#running-locally)
- [Build & Run with Docker](#build--run-with-docker)
- [License](#license)

---

---

## ✨ Features

- Reactive, asynchronous request routing
- Custom `AsyncPredicate` support for dynamic route matching
- Request filtering and header manipulation
- Response body rewriting (e.g., error sanitization)
- JWT-based authentication
- Dynamic route definition from configuration or external service

## 🛠️ Tech Stack

- Java 21
- Spring Boot 3.x
- Spring Cloud Gateway
- Reactor (Project Reactor)
- Spring WebFlux
- JWT (JSON Web Token)
- Docker (optional)
- MongoDB
- Maven

---

## 🚀 Getting Started

### 📦 Clone the repository
```bash
git clone https://github.com/your-org/your-spring-cloud-gateway.git
cd your-spring-cloud-gateway...

### 🛠️ Build the project
