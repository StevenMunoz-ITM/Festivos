# 🎉 API de Gestión de Festivos

Aplicación Spring Boot para la gestión y consulta de días festivos por país, con cálculo automático de fechas móviles basadas en Pascua.

## 🚀 Inicio Rápido

**Base URL:** `http://localhost:8080`

### ⭐ Endpoints Principales

#### **Validar si una fecha es festivo**
```http
GET /api/festivos/esfestivo/{idPais}/{dia}/{mes}/{anio}
```
**Ejemplo:** `GET /api/festivos/esfestivo/1/17/4/2025`  
**Respuesta:** `"Es festivo"`, `"No es festivo"` o `"Fecha no válida"`

#### **Listar festivos de un año**
```http
GET /api/festivos/listarporanio/{idPais}/{anio}
```
**Ejemplo:** `GET /api/festivos/listarporanio/1/2025`

#### **Validar con información detallada**
```http
GET /api/festivos/validar/{idPais}/{dia}/{mes}/{anio}
```
**Ejemplo:** `GET /api/festivos/validar/1/17/4/2025`

### 📋 Endpoints Adicionales

#### **CRUD Festivos**
- `GET /api/festivos/listar` - Listar todos los festivos
- `GET /api/festivos/obtener/{id}` - Obtener festivo por ID
- `POST /api/festivos/agregar` - Crear nuevo festivo
- `PUT /api/festivos/modificar` - Actualizar festivo
- `DELETE /api/festivos/eliminar/{id}` - Eliminar festivo

#### **CRUD Países**
- `GET /api/paises/listar` - Listar países
- `POST /api/paises/agregar` - Crear país
- `PUT /api/paises/modificar` - Actualizar país
- `DELETE /api/paises/eliminar/{id}` - Eliminar país

#### **CRUD Tipos de Festivos**
- `GET /api/tipofestivos/listar` - Listar tipos
- `POST /api/tipofestivos/agregar` - Crear tipo
- `PUT /api/tipofestivos/modificar` - Actualizar tipo
- `DELETE /api/tipofestivos/eliminar/{id}` - Eliminar tipo

## 💡 Tipos de Festivos Soportados

1. **Fijo** - Fecha fija cada año (ej: Año Nuevo)
2. **Ley Puente** - Se traslada al lunes siguiente si cae en fin de semana
3. **Basado en Pascua** - Calculado desde el Domingo de Pascua
4. **Pascua con Puente** - Basado en Pascua + ley puente
5. **Puente Viernes** - Se traslada al viernes (usado en Ecuador)

## 🇨🇴 Ejemplo de Uso - Colombia (ID: 1)

```bash
# Verificar si el Jueves Santo 2025 es festivo
curl http://localhost:8080/api/festivos/esfestivo/1/17/4/2025

# Obtener todos los festivos de Colombia en 2025  
curl http://localhost:8080/api/festivos/listarporanio/1/2025
```