# Hambre Cero - Backend API

Este proyecto implementa la API REST para el sistema **Hambre Cero**, orientada a la gestión de recetas sostenibles e ingredientes. Desarrollado en Java con Spring Boot, proporciona operaciones CRUD completas y gestión de imágenes.

## 🛠️ Tecnologías

*   **Lenguaje:** Java 2
*   **Framework:** Spring Boot 3.5.10
*   **Base de Datos:** MySQL / MariaDB (Entorno de desarrollo con Docker) 
*   **Persistencia:** Spring Data JPA & Hibernate 
*   **Mapeo:** ModelMapper & Lombok 
*   **Documentación:** OpenAPI 3 (`recipeapp.yaml`) 

## 🏗️ Arquitectura

El proyecto sigue una arquitectura por capas :
*   `controller`: Endpoints REST.
*   `service`: Lógica de negocio.
*   `repository`: Acceso a datos.
*   `domain/dto`: Entidades y objetos de transferencia.
*   `exception`: Gestión global de errores.

## 🗄️ Modelo de Datos

El sistema gestiona dos entidades principales con una relación **Muchos a Muchos (N:M)** :
*   **Recipes:** Recetas (dificultad, coste, imagen, etc.).
*   **Ingredients:** Ingredientes (calorías, temporada, huella de carbono).

## 🚀 Instalación y Ejecución

1.  **Base de Datos:** Levantar el contenedor de base de datos:
    ```bash
    docker-compose up -d
    ```
2.  **Configuración:** Revisar `application.properties` para credenciales y URL de BD.
3.  **Ejecución:**
    ```bash
    ./mvnw spring-boot:run
    ```
4.  **Verificación:** Los logs se generan en `logs/recipe-app.log` .

## 🔌 Endpoints Principales

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| POST | `/recipes` | Crear receta  |
| GET | `/recipes` | Listar recetas |
| GET | `/recipes/{id}` | Detalle de receta  |
| POST | `/ingredients` | Crear ingrediente |
| POST | `/images` | Subir imagen |

Se permite acceso CORS desde `http://localhost:5173` (Frontend).
