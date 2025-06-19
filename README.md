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
cd your-spring-cloud-gateway
```

## ⚙️ Application Configuration
### Update values of mongodb URI
### Update value of your secret key
```bash
spring:
   data:
      mongodb:
         uri: Update with your mongoDB URI
         database: sample_route # update database name
rsa:
   key:
      secret: WVZgXCnLoulqw1G97eFHV32P28x3uYbw9jcwTwuiM+ZI75od4y2Ln7bviuuxedZBKXF4VJe/Vp4VvT8/R6XEBQ== # Update RSA Key 
```
## 📚 Database Configuration
- ### Type: NoSQL
- ### Provider MongoDB
- ### database name : db_route <sub>(can be customized)</sub>
- ### collection name : routes <sub>(can be customized)</sub>
### 📥 Insert Sample Document into MongoDB
### 📝 Insert Command (Mongo Shell)
Replace collection with the name of the collection
```javascript
use db_route;
db.routes.insertOne(
{
  "route_id": 2,
  "service_id": 1,
  "group_id": 1,
  "up_route": "/nova/ejakerarkanva/",
  "down_route": "/api/public/",
  "enabled": true,
  "uri": "http://localhost:8081",
  "p_def": [
    {
      "name": "Cookie",
      "args": {
        "x_cookie": "test endpoint"
      }
    },
    {
      "name": "Method",
      "args": {
        "GET": "GET is used to request data from a specified resource",
        "POST": "The POST HTTP method sends data to the server. The type of the body of the request is indicated by the Content-Type header.Post is not Idempotent meaning: calling multiple time result creates a new resource at the server"
      }
    }
  ]
}
)
```
## 📦 Install Dependencies

<p style="color: green;">Use the following command to install all dependencies:</p>

```bash
./mvnw clean install
```
### 🛠️ Build the project
```bash
./mvnw clean package
```
🎛️ Run the application
```bash
./mvnw spring-boot:run
```
## ⚠️ **Important:**  
Ensure another REST application is running on port `8081` with the endpoint `/api/public` to properly test the routing functionality

