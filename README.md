# 🚀 Spring Security & JWT Base Template

¿Estás creando una API REST con Spring Boot y querés empezar con una base de seguridad ya configurada?

Este proyecto proporciona una plantilla reutilizable con **Spring Security, autenticación mediante JWT y autorización basada en roles**, para que puedas concentrarte en desarrollar la lógica de tu aplicación sin tener que arrancar toda la configuración de seguridad desde cero.

La idea es simple: cloná el proyecto, configurá tu entorno y empezá a construir tu propia API sobre esta base. 🧉

## ✨ Características

- 🔐 Registro e inicio de sesión de usuarios.
- 🎟️ Generación y validación de tokens JWT.
- 🔑 Contraseñas protegidas mediante BCrypt.
- 👥 Sistema de roles configurable mediante un `Enum`.
- 🛡️ Protección de endpoints según autenticación y roles.
- 🗄️ Persistencia de usuarios y roles con MySQL y Spring Data JPA.
- ⚠️ Manejo centralizado de excepciones.
- 📝 Registro de eventos mediante SLF4J y Logback.
- 🚫 Configuración de sesiones sin estado (`STATELESS`).

Los usuarios se registran inicialmente con el rol `ROLE_USER`. El acceso de administrador se reserva para usuarios a los que se les haya asignado ese rol mediante un procedimiento autorizado.

**Importante:** esta es una plantilla de partida. Podés extenderla, adaptar las reglas de acceso y agregar las validaciones que necesite tu aplicación.

---

## 🛠️ Tecnologías utilizadas

- Java
- Spring Boot
- Spring Security
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- MySQL
- Maven
- BCrypt
- SLF4J / Logback

---

## 📂 Estructura del proyecto

La aplicación está organizada por responsabilidades:

- `config/`: configuraciones e inicialización de datos.
- `controller/`: endpoints de autenticación y controladores de ejemplo.
- `dto/`: objetos utilizados para recibir y devolver información de la API.
- `model/`: entidades `User`, `Role` y el enum `ERole`.
- `repository/`: interfaces de acceso a datos.
- `security/`: configuración de Spring Security, filtro JWT y utilidades para gestionar los tokens.
- `service/`: lógica de autenticación y gestión de usuarios.

Esta estructura permite agregar nuevas funcionalidades sin concentrar toda la lógica en los controladores.

---

## ⚙️ Requisitos previos

Antes de ejecutar el proyecto, necesitás tener instalado:

- Java, en una versión compatible con la configurada en `pom.xml`.
- MySQL Server.
- Git.
- IntelliJ IDEA u otro IDE compatible con Java (opcional).

Maven se puede utilizar mediante el wrapper incluido en el repositorio.

---

## 🚀 Cómo empezar

### 1. Clonar el repositorio

```bash
git clone https://github.com/Agustin771/Spring-Security-JWT-base-.git
```

Entrá a la carpeta del proyecto:

```bash
cd Spring-Security-JWT-base-
```

### 2. Crear la base de datos

En MySQL, creá una base de datos para la aplicación. Por ejemplo:

```sql
CREATE DATABASE spring_security_jwt;
```

Podés elegir otro nombre, siempre que coincida con la configuración de la aplicación.

### 3. Configurar las variables de entorno

La aplicación necesita los datos de conexión a MySQL y la configuración del JWT.

Revisá `src/main/resources/application.properties` para identificar las variables que utiliza el proyecto y configurá los valores correspondientes en tu entorno.

Como mínimo, vas a necesitar definir:

- URL de conexión a MySQL.
- Usuario y contraseña de MySQL.
- Clave secreta para firmar los JWT.
- Tiempo de expiración de los tokens.

La clave JWT debe tener el formato esperado por la implementación, una longitud adecuada para el algoritmo utilizado y permanecer fuera del repositorio. Si la aplicación espera la clave codificada en Base64, generala con ese formato.

**No publiques credenciales reales ni claves JWT en GitHub.** Utilizá tus propias variables de entorno y no reutilices claves de ejemplo en producción.

### 4. Ejecutar la aplicación

Desde IntelliJ IDEA, podés ejecutar la clase principal de Spring Boot.

También podés iniciarla desde una terminal en Windows:

```bash
mvnw.cmd spring-boot:run
```

O, si tenés Maven instalado:

```bash
mvn spring-boot:run
```

Verificá en la consola que la aplicación haya iniciado correctamente y que la conexión con MySQL funcione.

---

## 🔐 ¿Cómo funciona la seguridad?

El flujo de autenticación es el siguiente:

1. El usuario envía sus credenciales al endpoint de login.
2. Spring Security verifica las credenciales.
3. Si son correctas, la aplicación genera un JWT firmado.
4. El cliente utiliza ese token para realizar solicitudes autenticadas.
5. El filtro JWT valida el token y establece el contexto de seguridad.
6. Spring Security determina si el usuario tiene permisos para acceder al endpoint solicitado.

Para las solicitudes autenticadas, el cliente debe enviar el token en el encabezado HTTP:

```http
Authorization: Bearer TU_TOKEN
```

La aplicación utiliza sesiones sin estado (`STATELESS`), por lo que la autenticación de las solicitudes protegidas se basa en el token y no en una sesión HTTP tradicional.

---

## 👥 Gestión de roles

El proyecto incluye un enum `ERole` que permite definir los roles de la aplicación.

Por ejemplo:

```java
public enum ERole {
    ROLE_USER,
    ROLE_ADMIN
}
```

Podés agregar otros roles según las necesidades de tu sistema, pero recordá que también debés configurar sus permisos y asignaciones.

En `SecurityConfig` se definen las reglas de acceso a los endpoints. Por ejemplo:

```java
.requestMatchers("/prueba/**").hasRole("ADMIN")
.anyRequest().authenticated()
```

En este ejemplo, las rutas que coincidan con `/prueba/**` requieren el rol `ROLE_ADMIN`, mientras que las demás solicitudes deben estar autenticadas, salvo que otra regla específica indique lo contrario.

El proyecto incluye un controlador de ejemplo para probar la protección de rutas exclusivas para administradores.

**Importante:** el registro público asigna el rol de usuario común. No habilites que cualquier persona pueda registrarse directamente como administrador. La asignación de privilegios elevados debe realizarse mediante un procedimiento controlado.

---

## 🧩 Adaptá la plantilla a tu API

Una vez que la aplicación esté funcionando, podés empezar a desarrollar tu propio sistema:

1. Creá tus entidades, DTOs y repositorios.
2. Implementá los servicios con la lógica de negocio.
3. Creá tus controladores y endpoints.
4. Definí qué rutas requieren autenticación y cuáles necesitan roles específicos.
5. Agregá validaciones, pruebas y manejo de errores según las necesidades de tu proyecto.

Por ejemplo, podés construir una API de productos, turnos, reservas o servicios para un negocio, reutilizando esta base de autenticación y autorización.

No necesitás mantener los controladores de ejemplo si ya no los necesitás: podés modificarlos o eliminarlos.

---

## ⚠️ Consideraciones de seguridad

Esta plantilla es un punto de partida y no reemplaza una revisión de seguridad para producción.

Antes de utilizarla en un entorno real, revisá, entre otros aspectos:

- Validación de los datos de entrada y tratamiento de errores.
- Gestión segura de claves, credenciales y configuración.
- Control de intentos de inicio de sesión.
- Expiración y estrategia de revocación de tokens.
- Uso de HTTPS en los entornos desplegados.
- Autorización de cada operación y recurso, no solamente de cada endpoint.
- Pruebas automatizadas de autenticación y permisos.
- Configuración de logs para evitar la exposición de contraseñas, tokens y otros datos sensibles.

---

## 🤝 Contribuciones

Si encontrás errores, tenés sugerencias o querés proponer mejoras, podés abrir un issue o enviar un pull request.

La idea es que esta base pueda servirle a otras personas que estén aprendiendo o quieran comenzar una API con una estructura de seguridad inicial.

## 👨‍💻 Autor

**Agustin771**

Repositorio: [Spring Security JWT Base Template](https://github.com/Agustin771/Spring-Security-JWT-base-)

Si te resulta útil, una estrella ⭐ en GitHub ayuda a que otras personas puedan descubrir el proyecto.

---

*Proyecto orientado al aprendizaje y a servir como base reutilizable para nuevas APIs. Adaptá y verificá la configuración antes de utilizarlo en producción.*
