# 🎉 API de Gestión de Festivos

✅ **Proyecto funcionando correctamente**

Una API REST desarrollada con Spring Boot para gestionar días festivos por país utilizando arquitectura limpia.

## 🏗️ Arquitectura del Proyecto

El proyecto está organizado en módulos siguiendo el patrón de arquitectura limpia:

```
� api-festivos
├── 📁 dominio/          # Entidades del dominio y DTOs
└── 📁 infraestructura/  # Repositorios y acceso a datos
```

### Módulos Actuales:

- **Dominio**: Contiene las entidades principales (`Festivo`, `Pais`, `TipoFestivo`) y sus DTOs correspondientes
- **Infraestructura**: Contiene los repositorios JPA para acceso a datos

## �🚀 Características principales:

- ✅ Arquitectura limpia y modular
- ✅ Entidades JPA con validaciones
- ✅ DTOs para transferencia de datos
- ✅ Repositorios JPA personalizados
- ✅ Configuración para PostgreSQL
- ✅ Soporte para cálculo de fechas basadas en Pascua
- ✅ Soporte para leyes puente

## 🛠️ Tecnologías utilizadas:

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Jackson** (para serialización JSON)

## 📋 Requisitos:

- Java 17 o superior
- Maven 3.6+
- PostgreSQL (para producción)

## � Cómo ejecutar:

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/StevenMunoz-ITM/Festivos.git
   cd Festivos/api
   ```

2. **Compilar el proyecto:**
   ```bash
   mvn clean compile
   ```

3. **Instalar dependencias:**
   ```bash
   mvn clean install
   ```

## 📂 Estructura del proyecto:

```
api/
├── dominio/
│   └── src/main/java/festivos/api/dominio/
│       ├── entidades/     # Entidades JPA
│       └── dtos/          # Data Transfer Objects
├── infraestructura/
│   └── src/main/java/festivos/api/infraestructura/
│       └── repositorios/  # Repositorios JPA
├── pom.xml               # Configuración Maven principal
└── README.md            # Este archivo
```

## 🔄 Estado del proyecto:

- ✅ **Dominio**: Entidades y DTOs implementados
- ✅ **Infraestructura**: Repositorios implementados
- 🚧 **En desarrollo**: Módulos de aplicación y presentación

---

*Proyecto desarrollado con Spring Boot y arquitectura limpia*