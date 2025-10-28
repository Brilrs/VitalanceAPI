# Aplicación Integral de Prevención y Control para Personas Diabéticas - Backend

## Descripción
API backend para monitoreo, alertas y gestión de datos de salud de pacientes con diabetes, desarrollada en **Java con Spring Boot**. Permite a pacientes y profesionales de la salud registrar mediciones, recibir alertas personalizadas y acceder a datos históricos de manera segura y escalable.

## Tecnologías
- **Lenguaje:** Java 17+  
- **Framework:** Spring Boot 3+  
- **Base de datos:** H2 (para pruebas) / MySQL (producción)  
- **ORM:** Spring Data JPA / Hibernate  
- **Seguridad:** Spring Security (opcional para autenticación y roles)  
- **Gestión de dependencias:** Maven o Gradle  
- **Documentación API:** Swagger (opcional)

## Características principales
- Gestión de **usuarios** (pacientes y profesionales de la salud)  
- Registro y seguimiento de **mediciones de salud** (glucosa, presión, etc.)  
- **Alertas personalizadas** basadas en los datos registrados  
- Integración con **dispositivos o aplicaciones externas** (Apple HealthKit, Google Fit)  
- API **RESTful** con endpoints claros y documentación sencilla  
- Compatible con **bases de datos SQL** y pruebas locales en memoria  

## Instalación y ejecución

1. Clonar el repositorio:  
git clone https://github.com/tu-usuario/nombre-del-proyecto.git

2. Entrar al proyecto:
cd nombre-del-proyecto

3.Configurar la base de datos en src/main/resources/application.properties o application.yml. Por ejemplo, para MySQL:
spring.datasource.url=jdbc:mysql://localhost:3306/diabetesdb
spring.datasource.username=root
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update

4. Compilar y ejecutar con Maven:
mvn clean install
mvn spring-boot:run


5.Acceder a la API (por defecto en http://localhost:8080)


Endpoints principales


POST /usuarios – Registrar un nuevo paciente o profesional
GET /usuarios/{id} – Obtener información de un usuario
POST /mediciones – Registrar medición de salud
GET /alertas – Consultar alertas activas
GET /mediciones/{usuarioId} – Listar historial de mediciones de un usuario
(Se recomienda usar herramientas como Postman o Swagger para probar la API)


Estructura del proyecto
src/main/java
│
├── controller     # Controladores REST
├── model          # Clases de entidad y DTOs
├── repository     # Interfaces para acceso a datos
├── service        # Lógica de negocio
└── config         # Configuración (seguridad, base de datos, etc.)


