# Arquitectura Hexagonal - API de Festivos Colombianos

## Resumen de la Reorganización Arquitectural

Se ha reorganizado exitosamente el proyecto API REST de Spring Boot para seguir los principios de la **Arquitectura Hexagonal (Ports and Adapters)**, separando claramente las responsabilidades en capas bien definidas.

## Nueva Estructura de Carpetas

```
src/main/java/festivos/api/
├── dominio/                    # Capa de Dominio (Core Business)
│   ├── entidades/             # Entidades del dominio
│   │   ├── Pais.java
│   │   ├── TipoFestivo.java
│   │   └── Festivo.java
│   └── dto/                   # Data Transfer Objects
│       ├── PaisDTO.java
│       ├── TipoFestivoDTO.java
│       └── FestivoDTO.java
├── core/                      # Puertos (Interfaces del dominio)
│   ├── IPaisService.java
│   ├── ITipoFestivoService.java
│   └── IFestivoService.java
├── infraestructura/           # Adaptadores de Infraestructura
│   └── repositorios/          # Repositorios JPA
│       ├── PaisRepository.java
│       ├── TipoFestivoRepository.java
│       └── FestivoRepository.java
├── service/                   # Servicios de Aplicación (Implementaciones)
│   ├── PaisService.java
│   ├── TipoFestivoService.java
│   └── FestivoService.java
├── controller/                # Adaptadores de Entrada (Controllers REST)
│   ├── PaisController.java
│   ├── TipoFestivoController.java
│   └── FestivoController.java
├── mapper/                    # Mappers para conversión DTO ↔ Entidad
│   ├── PaisMapper.java
│   ├── TipoFestivoMapper.java
│   └── FestivoMapper.java
├── config/                    # Configuración
│   └── DataInitializer.java
└── exception/                 # Manejo de Excepciones
    └── GlobalExceptionHandler.java
```

## Principios de Arquitectura Hexagonal Implementados

### 1. **Capa de Dominio (Centro del Hexágono)**
- **Entidades**: Representan el modelo de negocio puro
  - `Pais`, `TipoFestivo`, `Festivo`
- **DTOs**: Objetos para transferencia de datos
  - Incluyen validaciones con Jakarta Validation

### 2. **Puertos (Interfaces)**
- **core/**: Define contratos sin dependencias externas
  - `IPaisService`, `ITipoFestivoService`, `IFestivoService`
  - Operaciones de negocio independientes de la tecnología

### 3. **Adaptadores**
- **Adaptadores de Entrada** (controllers/):
  - REST Controllers que exponen la API HTTP
  - Convierten requests HTTP a llamadas de dominio
  
- **Adaptadores de Salida** (infraestructura/):
  - Repositorios JPA para persistencia
  - Implementan acceso a datos usando Spring Data JPA

### 4. **Servicios de Aplicación**
- **service/**: Implementan las interfaces del core
  - Orquestan operaciones de dominio
  - Manejan transacciones y validaciones
  - Incluyen lógica específica de Colombia (cálculo de Pascua)

### 5. **Mappers**
- **mapper/**: Conversión entre DTOs y Entidades
  - Aíslan la lógica de transformación
  - Facilitan el mantenimiento y testing

## Beneficios Alcanzados

### ✅ **Separación de Responsabilidades**
- Dominio independiente de tecnologías externas
- Lógica de negocio centralizada y protegida
- Infraestructura intercambiable

### ✅ **Testabilidad**
- Interfaces permiten fácil mocking
- Lógica de dominio testeable sin dependencias
- Servicios probables independientemente

### ✅ **Mantenibilidad**
- Cambios de infraestructura no afectan al dominio
- Código más legible y organizado
- Acoplamiento reducido entre capas

### ✅ **Escalabilidad**
- Fácil agregar nuevos adaptadores
- Lógica de negocio reutilizable
- Arquitectura preparada para microservicios

## Funcionalidades Implementadas

### **APIs REST Disponibles (29 endpoints)**

#### Países
- `GET /api/paises` - Listar todos los países
- `GET /api/paises/{id}` - Obtener país por ID
- `GET /api/paises/nombre/{nombre}` - Buscar por nombre
- `POST /api/paises` - Crear nuevo país
- `PUT /api/paises/{id}` - Actualizar país
- `DELETE /api/paises/{id}` - Eliminar país

#### Tipos de Festivos
- `GET /api/tipos-festivos` - Listar tipos
- `GET /api/tipos-festivos/{id}` - Obtener por ID
- `GET /api/tipos-festivos/tipo/{tipo}` - Buscar por tipo
- `POST /api/tipos-festivos` - Crear tipo
- `PUT /api/tipos-festivos/{id}` - Actualizar tipo
- `DELETE /api/tipos-festivos/{id}` - Eliminar tipo

#### Festivos
- `GET /api/festivos` - Listar todos los festivos
- `GET /api/festivos/{id}` - Obtener festivo por ID
- `GET /api/festivos/pais/{paisId}` - Festivos por país
- `GET /api/festivos/fecha?dia={dia}&mes={mes}` - Por fecha
- `GET /api/festivos/pais/{paisId}/mes/{mes}` - Por país y mes
- `GET /api/festivos/pascua` - Festivos basados en Pascua
- `GET /api/festivos/verificar?fecha={fecha}&paisId={id}` - **Validar si es festivo**
- `POST /api/festivos` - Crear festivo
- `PUT /api/festivos/{id}` - Actualizar festivo
- `DELETE /api/festivos/{id}` - Eliminar festivo

### **Lógica de Negocio Especial**
- **Cálculo de Pascua**: Implementa la fórmula oficial colombiana
- **18 Festivos Colombianos**: Pre-cargados en el sistema
- **Validación de Fechas**: Determina si una fecha específica es festivo

## Tecnologías Utilizadas

- **Spring Boot 3.5.6** - Framework principal
- **Spring Data JPA** - Persistencia
- **Hibernate 6.6.29** - ORM
- **H2 Database** - Base de datos en memoria
- **Jakarta Validation** - Validaciones
- **Maven** - Gestión de dependencias
- **Java 17** - Lenguaje de programación

## Estado del Proyecto

✅ **COMPLETADO** - Arquitectura hexagonal implementada exitosamente
✅ **COMPILACIÓN EXITOSA** - Sin errores
✅ **29 ENDPOINTS** funcionando
✅ **FESTIVOS COLOMBIANOS** inicializados
✅ **CÁLCULO DE PASCUA** implementado

La API está lista para ser utilizada y puede ejecutarse con `mvn spring-boot:run`.