# 🚀 Spring Security & JWT Base Template

¡Hola! Para aquel que tenga una API o quiera empezar a crear una, acá les dejo una base de **Spring Security con JWT** configurada y lista para usar.

Tambien viene con un `Enum` donde podes agregar los roles que quieras y después en la configuración de seguridad poder elegir qué usuario puede entrar a un endpoint. ⚙️🔒

Aclaración: use MySQL como base y en el .properties tengo las variables de entorno que use desde IntelliJ.
---

## 🛠️ Tecnologías utilizadas
* **Java** (con Spring Boot)
* **Spring Security**
* **JSON Web Token (JWT)** para autenticación stateless
* **Spring Data JPA / Hibernate**
* **MySQL** (Base de datos)

---

## 📂 Estructura del Proyecto
* **`config/`**: Inicializadores de datos y configuraciones iniciales.
* **`controller/`**: Controladores de autenticación (`AuthController`) y ejemplos de prueba (`ControllerExample`).
* **`dto/`**: Objetos de transferencia de datos para Login, Registro y respuestas del JWT.
* **`model/`**: Entidades principales (`User`, `Role`) y el Enum de roles (`ERole`).
* **`repository/`**: Interfaces de acceso a datos para usuarios y roles.
* **`security/`**: Filtros de autenticación (`AuthTokenFilter`), utilidades de JWT (`JwtUtils`) y la clase central `SecurityConfig`.
* **`service/`**: Lógica de negocio para la autenticación y gestión de usuarios.

---

## 🚀 Cómo empezar

1. **Cloná el repositorio:**
   ```bash
   git clone [https://github.com/Agustin771/Spring-Security-JWT-base-.git](https://github.com/Agustin771/Spring-Security-JWT-base-.git)
