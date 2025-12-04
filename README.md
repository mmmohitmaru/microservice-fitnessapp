# Microservice Fitness App

A comprehensive fitness application built using a Microservices architecture. This platform integrates Google Gemini AI to provide personalized workout recommendations, diet analysis, and security suggestions. It features secure authentication via Keycloak and robust data management using PostgreSQL and MongoDB.

## 🚀 Key Features

* **Secure Authentication**: Centralized identity management and login functionality using **Keycloak**.
* **AI-Powered Insights**: Integrated **Google Gemini AI** to provide:
    * Personalized workout and diet recommendations.
    * Health data analysis.
    * Intelligent suggestions for user progress.
* **Microservices Architecture**: Scalable and independent services for distinct functionalities.
* **Real-time Communication**: Asynchronous messaging and event streaming using **Apache Kafka**.
* **Hybrid Database System**: Utilizes both **PostgreSQL** (Relational) and **MongoDB** (NoSQL) for optimized data storage.
* **Centralized Configuration**: Managed via a Config Server.
* **Service Discovery**: Automated service registration using Netflix Eureka.

## 🛠️ Technology Stack

* **Backend Framework**: Spring Boot, Hibernate
* **Frontend**: JavaScript, HTML, CSS
* **Architecture**: Microservices
* **Message Broker**: Apache Kafka
* **Security & Identity**: Keycloak
* **Databases**: 
    * PostgreSQL (User data, relational data)
    * MongoDB (Activity logs, unstructured data)
* **AI Integration**: Google Gemini AI
* **Service Discovery**: Netflix Eureka
* **API Gateway**: Spring Cloud Gateway

## 📂 System Architecture

The project is organized into the following microservices:

* **`userservice`**: Manages user registration, profiles, and authentication details.
* **`activityservice`**: Tracks user workouts, daily activities, and logs.
* **`aiservice`**: Connects with Google Gemini AI to process data and generate recommendations.
* **`gateway`**: The API Gateway acting as the single entry point for client requests.
* **`eureka`**: Service Registry for service discovery and load balancing.
* **`configserver`**: Centralized configuration management for all services.
* **`fitness-frontend`**: The user interface for the application.

## ⚙️ Prerequisites

Before running the application, ensure you have the following installed:

* **Java 17+** (JDK)
* **Node.js & npm** (for Frontend)
* **Docker** (optional, for containerization)
* **PostgreSQL**
* **MongoDB**
* **Apache Kafka** & **Zookeeper**
* **Keycloak Server**

## 🏃‍♂️ Getting Started

### 1. Database Setup
* Create a PostgreSQL database (e.g., `fitness_db`).
* Create a MongoDB database (e.g., `fitness_logs`).

### 2. Infrastructure Services
Start the supporting services (Kafka, Zookeeper, and Keycloak). ensure Keycloak is running on the configured port and a Realm is set up.

### 3. Start Microservices
Run the Spring Boot services in the following order:
1.  **Config Server**
2.  **Eureka Server**
3.  **API Gateway**
4.  **User Service**
5.  **Activity Service**
6.  **AI Service**

🔐 Keycloak Configuration
Access the Keycloak Admin Console.

Create a new Realm (e.g., fitness-realm).

Create a Client for the frontend app.

Configure Roles and Users as required.

Update the application.properties in userservice and gateway with the Keycloak Client Secret and URL.

🤖 Google Gemini AI Setup
Obtain an API Key from Google AI Studio.

Add the API key to the aiservice configuration file.

Properties

gemini.api.key=YOUR_API_KEY

🤝 Contribution
Contributions are welcome! Please fork the repository and submit a pull request.

Fork the Project

Create your Feature Branch (git checkout -b feature/AmazingFeature)

Commit your Changes (git commit -m 'Add some AmazingFeature')

Push to the Branch (git push origin feature/AmazingFeature)

Open a Pull Request

📄 License
Distributed under the MIT License. See LICENSE for more information.
