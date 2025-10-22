# Ejemplos de Prueba - API de Festividades

## Ejemplos de Funcionamiento de los Tipos de Festivos

### Tipo 1: Fijo
**Descripción**: No se puede variar la fecha
**Ejemplos en Colombia**:
- Navidad: 25 de diciembre (siempre 25/12)
- Año Nuevo: 1 de enero (siempre 01/01)

**Pruebas**:
```bash
# Verificar que Navidad 2024 es festivo
GET /api/festivos/es-festivo?fecha=2024-12-25&paisId=1
# Respuesta: true

# Verificar que Año Nuevo 2025 es festivo  
GET /api/festivos/es-festivo?fecha=2025-01-01&paisId=1
# Respuesta: true
```

### Tipo 2: Ley de "Puente festivo"
**Descripción**: Se traslada al siguiente lunes si no cae en lunes
**Ejemplos en Colombia**:
- Santos Reyes: 6 de enero → Si cae martes, se celebra el lunes 8
- San José: 19 de marzo → Si cae viernes, se celebra el lunes 22

**Pruebas para 2024**:
```bash
# Santos Reyes 2024 (6 enero es sábado, se traslada al lunes 8)
GET /api/festivos/2/fecha-celebracion/2024
# Respuesta: "2024-01-08"

GET /api/festivos/es-festivo?fecha=2024-01-06&paisId=1
# Respuesta: false (fecha original)

GET /api/festivos/es-festivo?fecha=2024-01-08&paisId=1  
# Respuesta: true (fecha trasladada)
```

### Tipo 3: Basado en el domingo de pascua
**Descripción**: Se calcula desde la fecha de Pascua
**Ejemplos en Colombia**:
- Jueves Santo: Pascua - 3 días
- Viernes Santo: Pascua - 2 días
- Domingo de Pascua: Pascua + 0 días

**Pruebas para 2024 (Pascua: 31 marzo)**:
```bash
# Jueves Santo 2024 (31 marzo - 3 = 28 marzo)
GET /api/festivos/es-festivo?fecha=2024-03-28&paisId=1
# Respuesta: true

# Viernes Santo 2024 (31 marzo - 2 = 29 marzo)  
GET /api/festivos/es-festivo?fecha=2024-03-29&paisId=1
# Respuesta: true

# Domingo de Pascua 2024 (31 marzo)
GET /api/festivos/es-festivo?fecha=2024-03-31&paisId=1
# Respuesta: true
```

### Tipo 4: Basado en pascua + Ley de "Puente festivo"
**Descripción**: Se calcula desde Pascua Y se traslada al lunes
**Ejemplos en Colombia**:
- Ascensión del Señor: Pascua + 40 días → al siguiente lunes
- Corpus Christi: Pascua + 61 días → al siguiente lunes

**Pruebas para 2024**:
```bash
# Ascensión del Señor 2024 
# Pascua (31 mar) + 40 = 10 mayo (viernes) → se traslada al lunes 13 mayo
GET /api/festivos/16/fecha-celebracion/2024
# Respuesta: "2024-05-13"

# Corpus Christi 2024
# Pascua (31 mar) + 61 = 31 mayo (viernes) → se traslada al lunes 3 junio  
GET /api/festivos/17/fecha-celebracion/2024
# Respuesta: "2024-06-03"
```

## Casos de Prueba Específicos

### Verificar cálculo de Pascua
```bash
# Obtener festivos basados en Pascua de Colombia
GET /api/festivos/pais/1/pascua
# Retorna: Jueves Santo, Viernes Santo, Domingo de Pascua, Ascensión, Corpus Christi, Sagrado Corazón
```

### Verificar traslados por Ley de Puente
```bash
# Santos Reyes en diferentes años:
# 2024: 6 enero (sábado) → 8 enero (lunes)
# 2025: 6 enero (lunes) → 6 enero (no se traslada)
# 2026: 6 enero (martes) → 12 enero (lunes)

GET /api/festivos/2/fecha-celebracion/2024  # 2024-01-08
GET /api/festivos/2/fecha-celebracion/2025  # 2025-01-06  
GET /api/festivos/2/fecha-celebracion/2026  # 2026-01-12
```

### Obtener todos los festivos de un mes
```bash
# Festivos de enero en Colombia
GET /api/festivos/pais/1/mes/1
# Incluye: Año Nuevo (1 ene) y Santos Reyes (trasladado)

# Festivos de diciembre en Colombia  
GET /api/festivos/pais/1/mes/12
# Incluye: Inmaculada Concepción (8 dic) y Navidad (25 dic)
```

## Pruebas de Validación

### Fechas inválidas
```bash
# Fecha que no es festivo
GET /api/festivos/es-festivo?fecha=2024-02-15&paisId=1
# Respuesta: false

# País que no existe
GET /api/festivos/es-festivo?fecha=2024-12-25&paisId=999
# Respuesta: 400 Bad Request
```

### Obtener información de tipos
```bash
# Ver todos los tipos de festivos
GET /api/tipos-festivos
# Retorna los 4 tipos con sus descripciones correctas

# Buscar tipo específico
GET /api/tipos-festivos/tipo/Fijo
# Retorna el tipo 1
```

## Validación de Lógica de Negocio

### Fórmula Colombiana para Pascua
La API utiliza la **fórmula oficial colombiana** para calcular Pascua:

**Cálculo del Domingo de Ramos:**
```
días = d + (2b+4c+6d+5) MOD 7
```
Donde:
- a = Año MOD 19
- b = Año MOD 4  
- c = Año MOD 7
- d = (19a+24) MOD 30

**Ejemplo verificado para 1999:**
- a = 1999 MOD 19 = 4
- b = 1999 MOD 4 = 3
- c = 1999 MOD 7 = 4
- d = (19×4 + 24) MOD 30 = 10
- días = 10 + (2×3 + 4×4 + 6×10 + 5) MOD 7 = 13

**Resultado:**
- Domingo de Ramos: 15 marzo + 13 días = **28 marzo 1999**
- Domingo de Pascua: 28 marzo + 7 días = **4 abril 1999**

### Pruebas de la Nueva Fórmula
```bash
# Verificar Domingo de Ramos para 1999
GET /api/festivos/domingo-ramos/1999
# Respuesta esperada: "1999-03-28"

# Verificar Domingo de Pascua 1999
GET /api/festivos/es-festivo?fecha=1999-04-04&paisId=1
# Respuesta: true

# Verificar Jueves Santo 1999 (Pascua - 3)
GET /api/festivos/es-festivo?fecha=1999-04-01&paisId=1
# Respuesta: true

# Verificar Viernes Santo 1999 (Pascua - 2)  
GET /api/festivos/es-festivo?fecha=1999-04-02&paisId=1
# Respuesta: true
```

### Reglas de Traslado al Lunes
**Días de la semana** (ISO 8601):
- 1 = Lunes (no se traslada)
- 2 = Martes → +6 días al lunes
- 3 = Miércoles → +5 días al lunes  
- 4 = Jueves → +4 días al lunes
- 5 = Viernes → +3 días al lunes
- 6 = Sábado → +2 días al lunes
- 7 = Domingo → +1 día al lunes

## Comandos de Prueba Completos

```bash
# Obtener Domingo de Ramos para un año específico
curl -X GET http://localhost:8080/api/festivos/domingo-ramos/1999

# Obtener todos los países
curl -X GET http://localhost:8080/api/paises

# Obtener todos los tipos de festivos  
curl -X GET http://localhost:8080/api/tipos-festivos

# Obtener todos los festivos de Colombia
curl -X GET http://localhost:8080/api/festivos/pais/1

# Verificar si una fecha específica es festivo
curl -X GET "http://localhost:8080/api/festivos/es-festivo?fecha=2024-12-25&paisId=1"

# Obtener fecha de celebración de un festivo específico
curl -X GET http://localhost:8080/api/festivos/2/fecha-celebracion/2024

# Obtener festivos de Colombia basados en Pascua
curl -X GET http://localhost:8080/api/festivos/pais/1/pascua
```