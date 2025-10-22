# API REST de Festividades

## Descripción
API RESTful desarrollada con Spring Boot para gestionar festividades de diferentes países. Incluye soporte para festividades de fecha fija y festividades calculadas en base a la fecha de Pascua.

## Tecnologías Utilizadas
- **Spring Boot 3.5.6**
- **Spring Data JPA**
- **Spring Web**
- **Spring Validation**
- **Base de datos H2** (en memoria para desarrollo)
- **Java 17**

## Modelo de Datos

### Entidades
1. **País** - Representa los países
2. **TipoFestivo** - Tipos de festividades (fecha fija, transferible, basado en Pascua, etc.)
3. **Festivo** - Las festividades con sus fechas y relaciones

### Relaciones
- Un País puede tener muchos Festivos (1:N)
- Un TipoFestivo puede tener muchos Festivos (1:N)
- Un Festivo pertenece a un País y un TipoFestivo (N:1)

## Inicialización de Datos
La aplicación se inicializa automáticamente con:
- **País**: Colombia
- **4 Tipos de festivos** según la especificación oficial:
  1. **Fijo** - No se puede variar (ej: Navidad, Año Nuevo)
  2. **Ley de "Puente festivo"** - Se traslada al siguiente lunes
  3. **Basado en el domingo de pascua** - Se calcula desde Pascua
  4. **Basado en el domingo de pascua y Ley de "Puente festivo"** - Se calcula desde Pascua Y se traslada al lunes
- **18 Festivos de Colombia** según la legislación colombiana

## Lógica de Cálculo de Festividades

### Tipos de Festivos y sus Reglas

1. **Tipo 1 - Fijo**: 
   - La fecha no se puede variar
   - Ejemplos: Navidad (25 dic), Año Nuevo (1 ene)

2. **Tipo 2 - Ley de "Puente festivo"**: 
   - Si no cae en lunes, se traslada al siguiente lunes
   - Ejemplos: Santos Reyes, San José, Día de la Raza

3. **Tipo 3 - Basado en el domingo de pascua**: 
   - Se calcula sumando/restando días a la fecha de Pascua
   - Ejemplos: Jueves Santo (-3), Viernes Santo (-2)

4. **Tipo 4 - Basado en pascua + Puente festivo**: 
   - Se calcula desde Pascua Y se traslada al lunes si no cae en lunes
   - Ejemplos: Ascensión del Señor (+40), Corpus Christi (+61)

## Endpoints de la API

### Países (`/api/paises`)

#### Obtener todos los países
```http
GET /api/paises
```

#### Obtener país por ID
```http
GET /api/paises/{id}
```

#### Obtener país por nombre
```http
GET /api/paises/nombre/{nombre}
```
**Ejemplo**: `GET /api/paises/nombre/Colombia`

#### Crear nuevo país
```http
POST /api/paises
Content-Type: application/json

{
  "nombre": "Argentina"
}
```

#### Actualizar país
```http
PUT /api/paises/{id}
Content-Type: application/json

{
  "nombre": "República de Colombia"
}
```

#### Eliminar país
```http
DELETE /api/paises/{id}
```

#### Verificar si existe un país
```http
GET /api/paises/{id}/existe
```

### Tipos de Festivos (`/api/tipos-festivos`)

#### Obtener todos los tipos de festivos
```http
GET /api/tipos-festivos
```

#### Obtener tipo de festivo por ID
```http
GET /api/tipos-festivos/{id}
```

#### Obtener tipo de festivo por tipo
```http
GET /api/tipos-festivos/tipo/{tipo}
```
**Ejemplo**: `GET /api/tipos-festivos/tipo/Festivo de fecha fija`

#### Crear nuevo tipo de festivo
```http
POST /api/tipos-festivos
Content-Type: application/json

{
  "tipo": "Festivo religioso"
}
```

#### Actualizar tipo de festivo
```http
PUT /api/tipos-festivos/{id}
Content-Type: application/json

{
  "tipo": "Festivo cívico"
}
```

#### Eliminar tipo de festivo
```http
DELETE /api/tipos-festivos/{id}
```

#### Verificar si existe un tipo de festivo
```http
GET /api/tipos-festivos/{id}/existe
```

### Festivos (`/api/festivos`)

#### Obtener todos los festivos
```http
GET /api/festivos
```

#### Obtener festivo por ID
```http
GET /api/festivos/{id}
```

#### Obtener festivos de un país
```http
GET /api/festivos/pais/{paisId}
```
**Ejemplo**: `GET /api/festivos/pais/1` (Festivos de Colombia)

#### Obtener festivos por fecha específica
```http
GET /api/festivos/fecha?dia=1&mes=1
```
**Ejemplo**: Festivos del 1 de enero

#### Obtener festivos de un país en un mes específico
```http
GET /api/festivos/pais/{paisId}/mes/{mes}
```
**Ejemplo**: `GET /api/festivos/pais/1/mes/12` (Festivos de Colombia en diciembre)

#### Obtener festivos basados en Pascua
```http
GET /api/festivos/pascua
```

#### Obtener festivos basados en Pascua de un país
```http
GET /api/festivos/pais/{paisId}/pascua
```

#### Obtener el Domingo de Ramos para un año específico
```http
GET /api/festivos/domingo-ramos/{año}
```
**Ejemplo**: `GET /api/festivos/domingo-ramos/1999`
**Respuesta**: `"1999-03-28"`
**Descripción**: Calcula el Domingo de Ramos (inicio de Semana Santa) usando la fórmula colombiana oficial.

#### Obtener la fecha real de celebración de un festivo
```http
GET /api/festivos/{festivoId}/fecha-celebracion/{año}
```
**Ejemplo**: `GET /api/festivos/2/fecha-celebracion/2024`
**Descripción**: Calcula la fecha exacta en que se celebra un festivo considerando las reglas de puente festivo y cálculos de Pascua.

#### Verificar si una fecha es festivo
```http
GET /api/festivos/es-festivo?fecha=2024-04-14&paisId=1
```
**Parámetros**:
- `fecha`: Formato yyyy-MM-dd
- `paisId`: ID del país

#### Crear nuevo festivo (fecha fija)
```http
POST /api/festivos
Content-Type: application/json

{
  "pais": {"id": 1},
  "nombre": "Día del Programador",
  "dia": 13,
  "mes": 9,
  "diasPascua": null,
  "tipoFestivo": {"id": 1}
}
```

#### Crear nuevo festivo (basado en Pascua)
```http
POST /api/festivos
Content-Type: application/json

{
  "pais": {"id": 1},
  "nombre": "Lunes de Pascua",
  "dia": null,
  "mes": null,
  "diasPascua": 1,
  "tipoFestivo": {"id": 3}
}
```

#### Actualizar festivo
```http
PUT /api/festivos/{id}
Content-Type: application/json

{
  "pais": {"id": 1},
  "nombre": "Año Nuevo",
  "dia": 1,
  "mes": 1,
  "diasPascua": null,
  "tipoFestivo": {"id": 1}
}
```

#### Eliminar festivo
```http
DELETE /api/festivos/{id}
```

## Respuestas de Error

La API maneja errores de forma consistente con el siguiente formato:

```json
{
  "status": 400,
  "error": "Error de validación",
  "message": "Ya existe un país con el nombre: Colombia",
  "timestamp": "2024-10-21T10:30:00"
}
```

Para errores de validación de campos:
```json
{
  "status": 400,
  "error": "Error de validación de campos",
  "message": "Los datos enviados no son válidos",
  "timestamp": "2024-10-21T10:30:00",
  "validationErrors": {
    "nombre": "El nombre del país es obligatorio",
    "dia": "El día debe estar entre 1 y 31"
  }
}
```

## Códigos de Estado HTTP

- **200 OK**: Operación exitosa
- **201 Created**: Recurso creado exitosamente
- **204 No Content**: Eliminación exitosa
- **400 Bad Request**: Error en los datos enviados
- **404 Not Found**: Recurso no encontrado
- **500 Internal Server Error**: Error interno del servidor

## Configuración

### Base de Datos H2
- **URL**: `jdbc:h2:mem:festivosdb`
- **Usuario**: `sa`
- **Contraseña**: `password`
- **Consola H2**: `http://localhost:8080/h2-console`

### Puerto del Servidor
- **Puerto**: `8080`
- **URL Base**: `http://localhost:8080`

## Validaciones

### País
- Nombre obligatorio y máximo 100 caracteres
- Nombre único (no sensible a mayúsculas/minúsculas)

### Tipo de Festivo
- Tipo obligatorio y máximo 100 caracteres
- Tipo único (no sensible a mayúsculas/minúsculas)

### Festivo
- País y tipo de festivo obligatorios y deben existir
- Nombre obligatorio y máximo 100 caracteres
- Debe tener fecha fija (día/mes) O días de Pascua, pero no ambos
- Día entre 1 y 31, mes entre 1 y 12
- Fecha debe ser válida (ej: no 30 de febrero)

## Lógica de Negocio Especial

### Cálculo de Pascua
La API incluye la **fórmula colombiana oficial** para calcular la fecha de Pascua:

**Fórmula para Domingo de Ramos:**
```
días = d + (2b+4c+6d+5) MOD 7
```
Donde:
- a = Año MOD 19
- b = Año MOD 4  
- c = Año MOD 7
- d = (19a+24) MOD 30

**Proceso de cálculo:**
1. Calcular días después del 15 de marzo para el Domingo de Ramos
2. Domingo de Pascua = Domingo de Ramos + 7 días
3. Aplicar los días de diferencia para cada festivo basado en Pascua

**Festivos basados en Pascua:**
- Jueves Santo (Pascua - 3 días)
- Viernes Santo (Pascua - 2 días)
- Domingo de Pascua (Pascua + 0 días)
- Ascensión del Señor (Pascua + 40 días)
- Corpus Christi (Pascua + 61 días)
- Sagrado Corazón de Jesús (Pascua + 68 días)

### Verificación de Festivos
El endpoint `/api/festivos/es-festivo` verifica automáticamente:
1. Festivos de fecha fija que coincidan con la fecha consultada
2. Festivos basados en Pascua calculando la fecha de Pascua del año y sumando/restando los días correspondientes

## Ejemplos de Uso

### Verificar si el 25 de diciembre de 2024 es festivo en Colombia
```http
GET /api/festivos/es-festivo?fecha=2024-12-25&paisId=1
```
**Respuesta**: `true`

### Obtener todos los festivos de marzo en Colombia
```http
GET /api/festivos/pais/1/mes/3
```

### Obtener festivos basados en Pascua de Colombia
```http
GET /api/festivos/pais/1/pascua
```

Este endpoint retornará Jueves Santo, Viernes Santo, Domingo de Pascua, Ascensión del Señor, Corpus Christi, y Sagrado Corazón de Jesús.